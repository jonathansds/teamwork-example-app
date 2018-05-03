package com.teamwork.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamwork.example.R;
import com.teamwork.example.activity.ProjectActivity;
import com.teamwork.example.adapter.ProjectRecycleAdapter;
import com.teamwork.example.components.EndlessRecyclerViewScrollListener;
import com.teamwork.example.model.TWProject;
import com.teamwork.example.pojo.response.ProjectsResponsePojo;
import com.teamwork.example.util.EnumUtil;
import com.teamwork.example.util.FunctionUtil;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ProjectRecycleAdapter.ProjectAdapterListener{
    //HOST
    private HomeCallback mActivity;

    //VIEWS
    private Toolbar homeToolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;

    //ADAPTERS
    private ProjectRecycleAdapter projectsAdapter;

    //PAGINATION
    private boolean hasMore;
    private int pageCursor;

    //OTHERS
    private List<TWProject> projectList;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeToolbar = view.findViewById(R.id.fragment_home_toolbar);
        swipeRefreshLayout = view.findViewById(R.id.fragment_home_swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.fragment_home_projects_recycler_view);

        projectList = new ArrayList<>();
        hasMore = false;
        pageCursor = 0;

        projectsAdapter = new ProjectRecycleAdapter(getContext(), projectList, this);
        projectsAdapter.setHasMore(hasMore);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 500;
            }
        };

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMoreProjects();
            }
        };

        swipeRefreshLayout.setOnRefreshListener(() -> {
            //Even though Companies API doesn't support page = 0, it looks like page starts from 0 on Projects API.
            pageCursor = 0;
            loadMoreProjects();
        });

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(projectsAdapter);

        swipeRefreshLayout.setRefreshing(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.HomeCallback) {
            mActivity = (HomeFragment.HomeCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeFragment.HomeCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(!hidden && mActivity != null){
            ((AppCompatActivity)getActivity()).setSupportActionBar(homeToolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void indicateListLoadingError(int message){
        if(getView() != null && message != -1){
            FunctionUtil.showSnakeMessage(getView().findViewById(R.id.main_layout), message);
        }

        scrollListener.loadedWithError();
        recyclerView.post(()-> projectsAdapter.setLoadedWithError(true));

        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    //PUBLIC METHODS

    public void onProjectsRetrieved(ProjectsResponsePojo response){
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

        if(response.getCode().equals(EnumUtil.RESPONSE_CODE.SUCCESS)){
            if (response.getProjects() != null && !response.getProjects().isEmpty()){
                if(pageCursor == 0){
                    projectList.clear();
                    projectsAdapter.notifyDataSetChanged();
                    scrollListener.resetState();
                }

                int start = projectList.size();
                projectList.addAll(response.getProjects());
                projectsAdapter.notifyItemRangeInserted(start, response.getProjects().size());

            }else{
                if(pageCursor == 0){
                    projectList.clear();
                    projectsAdapter.notifyDataSetChanged();
                    scrollListener.resetState();

                }else {
                    projectsAdapter.notifyItemChanged(projectList.size() - 1);
                }
            }

            this.hasMore = response.getHasMore();
            this.pageCursor = response.getNextPage();
            projectsAdapter.setHasMore(hasMore);
            projectsAdapter.setLoadedWithError(false);

        }else{
            indicateListLoadingError(-1);
        }
    }



    //PROJECT ADAPTER LISTENER

    @Override
    public void loadMoreProjects() {
        if(hasMore || pageCursor == 0){
            if(FunctionUtil.isInternetAvailable()){
                recyclerView.post(()-> projectsAdapter.setLoadedWithError(false));
                mActivity.onRequestLoadProjects(pageCursor);

            }else{
                indicateListLoadingError(R.string.message_no_internet);
            }
        }
    }

    @Override
    public void onRequestOpenProject(TWProject project) {
        Intent projectDetail = new Intent(getContext(), ProjectActivity.class);

        projectDetail.putExtra(EnumUtil.EXTRAS.PROJECT.getId(), Parcels.wrap(TWProject.class, project));
        startActivity(projectDetail);

        getActivity().overridePendingTransition(R.anim.example_transition_slide_in_bottom, R.anim.example_transition_slide_out_top);
    }

    @Override
    public void onRequestFavouriteProject(TWProject project) {
        mActivity.onRequestFavouriteProject(project.getId(), project.isFavourite());
    }



    //FRAGMENT INTERFACE CALLBACK

    public interface HomeCallback{
        void onRequestLoadProjects(int pageCursor);
        void onRequestFavouriteProject(String projectId, boolean favourite);
    }
}
