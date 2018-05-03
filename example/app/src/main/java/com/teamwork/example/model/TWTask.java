package com.teamwork.example.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class TWTask {

    @SerializedName("id")
    String id;

    @SerializedName("description")
    String description;

    @SerializedName("project-id")
    String projectId;

    @SerializedName("project-name")
    String projectName;

    @SerializedName("status")
    String status;

    @SerializedName("completed")
    boolean completed;

    @SerializedName("start-date")
    String dateStart;

    @SerializedName("due-date")
    String dateDue;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }
}
