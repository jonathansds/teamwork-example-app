package com.teamwork.example.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.teamwork.example.fragment.ProjectDescriptionFragment;
import com.teamwork.example.fragment.ProjectTasksFragment;
import com.teamwork.example.model.TWProject;

import java.util.List;

public class ProjectPageAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<Integer> pages;
    private TWProject project;
    private ProjectTasksFragment tasksFragment;

    public ProjectPageAdapter(Context context, FragmentManager fragmentManager, List<Integer> pages, TWProject project) {
        super(fragmentManager);

        this.context = context;
        this.pages = pages;
        this.project = project;
    }

    public ProjectTasksFragment getTasksFragment(){
        return tasksFragment;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(pages.get(position));
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return ProjectDescriptionFragment.newInstance(project);
        }

        //This is not bullet proof but in this case we know there will be always 2 options only
        return ProjectTasksFragment.newInstance();
    }

    //Hack to have the proper instance of this fragment. (Research for better solution).
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if(object != null && object instanceof ProjectTasksFragment){
            tasksFragment = (ProjectTasksFragment) object;
            tasksFragment.refreshIfNeeded();
        }

        super.setPrimaryItem(container, position, object);
    }
}
