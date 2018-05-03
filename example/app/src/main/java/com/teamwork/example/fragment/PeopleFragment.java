package com.teamwork.example.fragment;

import android.content.Context;
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
import com.teamwork.example.adapter.PersonRecycleAdapter;
import com.teamwork.example.components.EndlessRecyclerViewScrollListener;
import com.teamwork.example.model.TWPerson;
import com.teamwork.example.pojo.response.PeopleResponsePojo;
import com.teamwork.example.util.EnumUtil;
import com.teamwork.example.util.FunctionUtil;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment implements PersonRecycleAdapter.PersonAdapterListener{
    //HOST
    private PeopleCallback mActivity;

    //VIEWS
    private Toolbar peopleToolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;

    //ADAPTERS
    private PersonRecycleAdapter peopleAdapter;

    //PAGINATION
    private boolean hasMore;
    private int pageCursor;

    //OTHERS
    private List<TWPerson> peopleList;

    public PeopleFragment() {
        // Required empty public constructor
    }

    public static PeopleFragment newInstance() {
        return new PeopleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);

        peopleToolbar = view.findViewById(R.id.fragment_people_toolbar);
        swipeRefreshLayout = view.findViewById(R.id.fragment_people_swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.fragment_people_recycler_view);

        peopleList = new ArrayList<>();
        hasMore = false;
        pageCursor = 1;

        peopleAdapter = new PersonRecycleAdapter(getContext(), peopleList, this);
        peopleAdapter.setHasMore(hasMore);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 500;
            }
        };

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMorePeople();
            }
        };

        swipeRefreshLayout.setOnRefreshListener(() -> {
            pageCursor = 1;
            loadMorePeople();
        });

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(peopleAdapter);

        swipeRefreshLayout.setRefreshing(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PeopleFragment.PeopleCallback) {
            mActivity = (PeopleFragment.PeopleCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PeopleFragment.PeopleCallback");
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
            ((AppCompatActivity)getActivity()).setSupportActionBar(peopleToolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void indicateListLoadingError(int message){
        if(getView() != null && message != -1){
            FunctionUtil.showSnakeMessage(getView().findViewById(R.id.main_layout), message);
        }

        scrollListener.loadedWithError();
        recyclerView.post(()-> peopleAdapter.setLoadedWithError(true));

        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    //PUBLIC METHODS

    public void onPeopleRetrieved(PeopleResponsePojo response){
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

        if(response.getCode().equals(EnumUtil.RESPONSE_CODE.SUCCESS)){
            if (response.getPeople() != null && !response.getPeople().isEmpty()){
                if(pageCursor == 1){
                    peopleList.clear();
                    peopleAdapter.notifyDataSetChanged();
                    scrollListener.resetState();
                }

                int start = peopleList.size();
                peopleList.addAll(response.getPeople());
                peopleAdapter.notifyItemRangeInserted(start, response.getPeople().size());

            }else{
                if(pageCursor == 1){
                    peopleList.clear();
                    peopleAdapter.notifyDataSetChanged();
                    scrollListener.resetState();

                }else {
                    peopleAdapter.notifyItemChanged(peopleList.size() - 1);
                }
            }

            this.hasMore = response.getHasMore();
            this.pageCursor = response.getNextPage();
            peopleAdapter.setHasMore(hasMore);
            peopleAdapter.setLoadedWithError(false);

        }else{
            indicateListLoadingError(-1);
        }
    }



    //PROJECT ADAPTER LISTENER

    @Override
    public void loadMorePeople() {
        if(hasMore || pageCursor == 1){
            if(FunctionUtil.isInternetAvailable()){
                recyclerView.post(()-> peopleAdapter.setLoadedWithError(false));
                mActivity.onRequestLoadPeople(pageCursor);

            }else{
                indicateListLoadingError(R.string.message_no_internet);
            }
        }
    }

    @Override
    public void onRequestOpenPerson(TWPerson person) {
        //TODO : Need extra time to implement this feature
    }



    //FRAGMENT INTERFACE CALLBACK

    public interface PeopleCallback{
        void onRequestLoadPeople(int pageCursor);
    }
}
