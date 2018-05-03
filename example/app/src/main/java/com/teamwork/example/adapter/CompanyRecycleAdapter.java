package com.teamwork.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.teamwork.example.R;
import com.teamwork.example.model.TWCompany;
import com.teamwork.example.util.FunctionUtil;

import java.util.List;

public class CompanyRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_COMPANY = 1;
    private static final int VIEW_TYPE_LOADING = 2;

    private List<TWCompany> listItems;
    private Context context;
    private CompanyAdapterListener listener;
    private boolean hasMore, loadedWithError;

    public CompanyRecycleAdapter(Context context, List<TWCompany> listItems, CompanyAdapterListener listener){
        this.context = context;
        this.listItems = listItems;
        this.listener = listener;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void setLoadedWithError(boolean loadedWithError){
        this.loadedWithError = loadedWithError;
        notifyItemChanged(listItems.size());
    }

    @Override
    public int getItemCount() {
        return listItems == null ? 0 : listItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == listItems.size()){
            return VIEW_TYPE_LOADING;

        }else{
            return VIEW_TYPE_COMPANY;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_loading, parent, false);
            return new CompanyRecycleAdapter.LoadingViewHolder(view);

        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_company, parent, false);
            return new CompanyRecycleAdapter.CompanyItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CompanyRecycleAdapter.LoadingViewHolder){
            CompanyRecycleAdapter.LoadingViewHolder loadingViewHolder = (CompanyRecycleAdapter.LoadingViewHolder)holder;

            if(hasMore){
                loadingViewHolder.itemView.setVisibility(View.VISIBLE);

                if(loadedWithError){
                    loadingViewHolder.progressBar.setVisibility(View.GONE);
                    loadingViewHolder.textLoading.setVisibility(View.GONE);

                    loadingViewHolder.iconRetry.setVisibility(View.VISIBLE);
                    loadingViewHolder.textRetry.setVisibility(View.VISIBLE);

                }else {
                    loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                    loadingViewHolder.textLoading.setVisibility(View.VISIBLE);
                    loadingViewHolder.progressBar.setIndeterminate(true);

                    loadingViewHolder.iconRetry.setVisibility(View.GONE);
                    loadingViewHolder.textRetry.setVisibility(View.GONE);
                }
            }else{
                loadingViewHolder.progressBar.setVisibility(View.GONE);
                loadingViewHolder.textLoading.setVisibility(View.GONE);
                loadingViewHolder.iconRetry.setVisibility(View.GONE);
                loadingViewHolder.textRetry.setVisibility(View.GONE);
            }
        }else{
            TWCompany item = listItems.get(position);
            CompanyRecycleAdapter.CompanyItemViewHolder companyHolder = (CompanyRecycleAdapter.CompanyItemViewHolder)holder;

            companyHolder.layout.setTag(item);

            companyHolder.companyName.setText(item.getName());

            companyHolder.companyImage.setImageResource(R.drawable.example_ic_circle_company_image_placeholder);

            if(item.getLogoURL() != null && !item.getLogoURL().isEmpty()){
                Picasso.with(context).load(item.getLogoURL())
                        .resizeDimen(R.dimen.example_circle_picture_medium, R.dimen.example_circle_picture_medium)
                        .centerCrop()
                        .into(companyHolder.target);
            }
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        TextView textLoading, textRetry;
        AppCompatImageView iconRetry;

        LoadingViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.item_list_loading_progress_bar);
            textLoading = itemView.findViewById(R.id.item_list_loading_text);
            iconRetry = itemView.findViewById(R.id.item_list_loading_retry_icon);
            textRetry = itemView.findViewById(R.id.item_list_loading_retry_text);

            iconRetry.setOnClickListener(view -> listener.loadMoreCompanies());

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                progressBar.getIndeterminateDrawable().setColorFilter(context.getColor(R.color.listLoadingProgressColor), android.graphics.PorterDuff.Mode.MULTIPLY);

            }else{
                progressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.listLoadingProgressColor), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }
    }

    public class CompanyItemViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView companyImage;
        TextView companyName;
        RelativeLayout layout;
        CompanyClickManager clickManager;
        Target target;

        CompanyItemViewHolder(View itemView) {
            super(itemView);

            clickManager = new CompanyClickManager();

            companyImage = itemView.findViewById(R.id.item_company_company_image);
            companyName = itemView.findViewById(R.id.item_company_label_name);
            layout = itemView.findViewById(R.id.item_company_main_layout);

            layout.setOnClickListener(clickManager);

            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    companyImage.setImageDrawable(FunctionUtil.roundBitmap(bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
        }

        //Not necessary but I decided to implement like this to show how I would implement in a scenario with many click actions
        private class CompanyClickManager implements View.OnClickListener{
            @Override
            public void onClick(View view) {
                TWCompany company = (TWCompany) view.getTag();

                switch (view.getId()){
                    case R.id.item_company_main_layout:
                        listener.onRequestOpenCompany(company);

                        break;
                }
            }
        }
    }

    public interface CompanyAdapterListener {
        void loadMoreCompanies();
        void onRequestOpenCompany(TWCompany company);
    }
}
