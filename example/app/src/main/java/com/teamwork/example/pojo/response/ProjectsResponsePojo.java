package com.teamwork.example.pojo.response;

import com.google.gson.annotations.SerializedName;
import com.teamwork.example.model.TWProject;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class ProjectsResponsePojo extends ExampleResponsePojo{

    @SerializedName("projects")
    List<TWProject> projects;

    public List<TWProject> getProjects() {
        return projects;
    }

    public void setProjects(List<TWProject> projects) {
        this.projects = projects;
    }
}
