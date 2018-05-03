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
import com.teamwork.example.adapter.CompanyRecycleAdapter;
import com.teamwork.example.components.EndlessRecyclerViewScrollListener;
import com.teamwork.example.model.TWCompany;
import com.teamwork.example.pojo.response.CompaniesResponsePojo;
import com.teamwork.example.util.EnumUtil;
import com.teamwork.example.util.FunctionUtil;

import java.util.ArrayList;
import java.util.List;

public class CompaniesFragment extends Fragment implements CompanyRecycleAdapter.CompanyAdapterListener{
    //HOST
    private CompaniesCallback mActivity;

    //VIEWS
    private Toolbar companiesToolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;

    //ADAPTERS
    private CompanyRecycleAdapter companiesAdapter;

    //PAGINATION
    private boolean hasMore;
    private int pageCursor;

    //OTHERS
    private List<TWCompany> companyList;

    public CompaniesFragment() {
        // Required empty public constructor
    }

    public static CompaniesFragment newInstance() {
        return new CompaniesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_companies, container, false);

        companiesToolbar = view.findViewById(R.id.fragment_companies_toolbar);
        swipeRefreshLayout = view.findViewById(R.id.fragment_companies_swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.fragment_companies_recycler_view);

        companyList = new ArrayList<>();
        hasMore = false;
        pageCursor = 1;

        companiesAdapter = new CompanyRecycleAdapter(getContext(), companyList, this);
        companiesAdapter.setHasMore(hasMore);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 500;
            }
        };

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMoreCompanies();
            }
        };

        swipeRefreshLayout.setOnRefreshListener(() -> {
            //Even though Projects API supports page = 0, Companies API just supports from 1.
            pageCursor = 1;
            loadMoreCompanies();
        });

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(companiesAdapter);

        swipeRefreshLayout.setRefreshing(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CompaniesFragment.CompaniesCallback) {
            mActivity = (CompaniesFragment.CompaniesCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CompaniesFragment.CompaniesCallback");
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
            ((AppCompatActivity)getActivity()).setSupportActionBar(companiesToolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void indicateListLoadingError(int message){
        if(getView() != null && message != -1){
            FunctionUtil.showSnakeMessage(getView().findViewById(R.id.main_layout), message);
        }

        scrollListener.loadedWithError();
        recyclerView.post(()-> companiesAdapter.setLoadedWithError(true));

        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    //PUBLIC METHODS

    public void onCompaniesRetrieved(CompaniesResponsePojo response){
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

        if(response.getCode().equals(EnumUtil.RESPONSE_CODE.SUCCESS)){
            if (response.getCompanies() != null && !response.getCompanies().isEmpty()){
                if(pageCursor == 1){
                    companyList.clear();
                    companiesAdapter.notifyDataSetChanged();
                    scrollListener.resetState();
                }

                int start = companyList.size();
                companyList.addAll(response.getCompanies());
                companiesAdapter.notifyItemRangeInserted(start, response.getCompanies().size());

            }else{
                if(pageCursor == 1){
                    companyList.clear();
                    companiesAdapter.notifyDataSetChanged();
                    scrollListener.resetState();

                }else {
                    companiesAdapter.notifyItemChanged(companyList.size() - 1);
                }
            }

            this.hasMore = response.getHasMore();
            this.pageCursor = response.getNextPage();
            companiesAdapter.setHasMore(hasMore);
            companiesAdapter.setLoadedWithError(false);

        }else{
            indicateListLoadingError(-1);
        }
    }



    //PROJECT ADAPTER LISTENER

    @Override
    public void loadMoreCompanies() {
        if(hasMore || pageCursor == 1){
            if(FunctionUtil.isInternetAvailable()){
                recyclerView.post(()-> companiesAdapter.setLoadedWithError(false));
                mActivity.onRequestLoadCompanies(pageCursor);

            }else{
                indicateListLoadingError(R.string.message_no_internet);
            }
        }
    }

    @Override
    public void onRequestOpenCompany(TWCompany company) {
        //TODO : Need extra time to implement this feature
    }



    //FRAGMENT INTERFACE CALLBACK

    public interface CompaniesCallback{
        void onRequestLoadCompanies(int pageCursor);
    }
}
