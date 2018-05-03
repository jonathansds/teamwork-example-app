package com.teamwork.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamwork.example.R;
import com.teamwork.example.model.TWProject;
import com.teamwork.example.util.EnumUtil;

import org.parceler.Parcels;

public class ProjectDescriptionFragment extends Fragment{

    //OTHERS
    private TWProject project;

    public ProjectDescriptionFragment() {
        // Required empty public constructor
    }

    public static ProjectDescriptionFragment newInstance(TWProject project) {
        ProjectDescriptionFragment instance = new ProjectDescriptionFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EnumUtil.EXTRAS.PROJECT.getId(), Parcels.wrap(TWProject.class, project));
        instance.setArguments(bundle);

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        project = Parcels.unwrap(getArguments().getParcelable(EnumUtil.EXTRAS.PROJECT.getId()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_description, container, false);

        TextView projectDescription = view.findViewById(R.id.fragment_project_description_description);

        projectDescription.setText(project.getDescription());

        return view;
    }
}
