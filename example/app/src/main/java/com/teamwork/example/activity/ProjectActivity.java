package com.teamwork.example.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.teamwork.example.ExampleActivity;
import com.teamwork.example.R;
import com.teamwork.example.adapter.ProjectPageAdapter;
import com.teamwork.example.fragment.ProjectTasksFragment;
import com.teamwork.example.model.TWProject;
import com.teamwork.example.pojo.response.TasksResponsePojo;
import com.teamwork.example.util.EnumUtil;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;

public class ProjectActivity extends ExampleActivity implements View.OnClickListener, ProjectTasksFragment.ProjectTasksCallback {
    //VIEWS
    private ProjectPageAdapter pageAdapter;
    private Target projectImageTarget; //Instance needs to be keep for as long as possible. Picasso issue.

    //OTHERS
    private TWProject selectedProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        if(getIntent().hasExtra(EnumUtil.EXTRAS.PROJECT.getId())){
            selectedProject = Parcels.unwrap(getIntent().getParcelableExtra(EnumUtil.EXTRAS.PROJECT.getId()));

            initComponents();

        }else{
            finish();
        }
    }

    @Override
    public void initComponents() {
        myContext = this;

        Toolbar projectToolbar = findViewById(R.id.project_toolbar);
        AppCompatImageView projectImage = findViewById(R.id.project_image_project);
        ViewPager viewPager = findViewById(R.id.project_view_pager);
        TabLayout tabLayout = findViewById(R.id.project_tab_layout);
        AppCompatImageView iconClose = findViewById(R.id.project_icon_close);

        iconClose.setOnClickListener(this);

        projectImageTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if(bitmap != null && !bitmap.isRecycled()){
                    Palette palette = Palette.from(bitmap).generate();
                    Palette.Swatch mostPopulous = null;
                    for (Palette.Swatch swatch : palette.getSwatches()) {
                        if (mostPopulous == null || swatch.getPopulation() > mostPopulous.getPopulation()) {
                            mostPopulous = swatch;
                        }
                    }

                    if(mostPopulous != null){
                        projectToolbar.setBackgroundColor(mostPopulous.getRgb());
                    }

                    projectImage.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        if(selectedProject.getLogoURL() != null && !selectedProject.getLogoURL().isEmpty()){
            Picasso.with(myContext).load(selectedProject.getLogoURL()).into(projectImageTarget);
        }

        List<Integer> tabLabels = Arrays.asList(R.string.project_tab_project_description, R.string.project_tab_project_summary);

        pageAdapter = new ProjectPageAdapter(myContext, getSupportFragmentManager(), tabLabels, selectedProject);
        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.example_transition_slide_in_top, R.anim.example_transition_slide_out_bottom);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.project_icon_close:
                finish();

        }
    }





    // COMPANIES FRAGMENT CALLBACK

    @Override
    public void onRequestListMyTasks() {
        if(projectService != null){
            projectService.listMyProjectTasks(selectedProject.getId());
        }
    }






    //---------------- PROJECT SERVICE CALLBACK ------------------//

    @Override
    public void onListProjectTasksResponse(TasksResponsePojo response) {
        super.onListProjectTasksResponse(response);

        if(pageAdapter.getTasksFragment() != null){
            pageAdapter.getTasksFragment().onProjectTasksRetrieved(response);
        }
    }
}
