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
import com.teamwork.example.model.TWProject;
import com.teamwork.example.util.FunctionUtil;

import java.util.List;

public class ProjectRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_PROJECT = 1;
    private static final int VIEW_TYPE_LOADING = 2;

    private List<TWProject> listItems;
    private Context context;
    private ProjectAdapterListener listener;
    private boolean hasMore, loadedWithError;

    public ProjectRecycleAdapter(Context context, List<TWProject> listItems, ProjectAdapterListener listener){
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
            return VIEW_TYPE_PROJECT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_loading, parent, false);
            return new ProjectRecycleAdapter.LoadingViewHolder(view);

        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false);
            return new ProjectRecycleAdapter.ProjectItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ProjectRecycleAdapter.LoadingViewHolder){
            ProjectRecycleAdapter.LoadingViewHolder loadingViewHolder = (ProjectRecycleAdapter.LoadingViewHolder)holder;

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
            TWProject item = listItems.get(position);
            ProjectRecycleAdapter.ProjectItemViewHolder projectHolder = (ProjectRecycleAdapter.ProjectItemViewHolder)holder;

            projectHolder.layout.setTag(item);
            projectHolder.iconFavourite.setTag(item);

            projectHolder.projectName.setText(item.getName());
            projectHolder.projectImage.setImageResource(R.drawable.example_ic_circle_project_image_placeholder);

            if(item.getLogoURL() != null && !item.getLogoURL().isEmpty()){
                Picasso.with(context).load(item.getLogoURL())
                        .resizeDimen(R.dimen.example_circle_picture_medium, R.dimen.example_circle_picture_medium)
                        .centerCrop()
                        .into(projectHolder.target);
            }

            if(item.isFavourite()){
                projectHolder.iconFavourite.setImageResource(R.drawable.example_ic_favourite);

            }else {
                projectHolder.iconFavourite.setImageResource(R.drawable.example_ic_favourite_o);
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

            iconRetry.setOnClickListener(view -> listener.loadMoreProjects());

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                progressBar.getIndeterminateDrawable().setColorFilter(context.getColor(R.color.listLoadingProgressColor), android.graphics.PorterDuff.Mode.MULTIPLY);

            }else{
                progressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.listLoadingProgressColor), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }
    }

    public class ProjectItemViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView iconFavourite, projectImage;
        TextView projectName;
        RelativeLayout layout;
        ProjectClickManager clickManager;
        Target target;

        ProjectItemViewHolder(View itemView) {
            super(itemView);

            clickManager = new ProjectClickManager();

            iconFavourite = itemView.findViewById(R.id.item_project_icon_favourite);
            projectImage = itemView.findViewById(R.id.item_project_project_image);
            projectName = itemView.findViewById(R.id.item_project_label_name);
            layout = itemView.findViewById(R.id.item_project_main_layout);

            layout.setOnClickListener(clickManager);
            iconFavourite.setOnClickListener(clickManager);

            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    projectImage.setImageDrawable(FunctionUtil.roundBitmap(bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
        }

        private class ProjectClickManager implements View.OnClickListener{
            @Override
            public void onClick(View view) {
                TWProject project = (TWProject) view.getTag();

                switch (view.getId()){
                    case R.id.item_project_main_layout:
                        listener.onRequestOpenProject(project);

                        break;
                    case R.id.item_project_icon_favourite:
                        if(project.isFavourite()){
                            iconFavourite.setImageResource(R.drawable.example_ic_favourite_o);
                            project.setFavourite(false);

                        }else {
                            iconFavourite.setImageResource(R.drawable.example_ic_favourite);
                            project.setFavourite(true);
                        }

                        listener.onRequestFavouriteProject(project);

                        break;
                }
            }
        }
    }

    public interface ProjectAdapterListener {
        void loadMoreProjects();
        void onRequestOpenProject(TWProject project);
        void onRequestFavouriteProject(TWProject project);
    }
}
