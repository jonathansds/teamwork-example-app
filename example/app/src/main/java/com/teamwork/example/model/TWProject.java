package com.teamwork.example.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class TWProject {

    @SerializedName("id")
    String id;

    @SerializedName("company")
    TWCompany company;

    @SerializedName("category")
    TWCategory category;

    @SerializedName("starred")
    boolean favourite;

    @SerializedName("name")
    String name;

    @SerializedName("show-announcement")
    boolean showAnnouncement;

    @SerializedName("announcement")
    String announcement;

    @SerializedName("description")
    String description;

    @SerializedName("status")
    String status;

    @SerializedName("isProjectAdmin")
    boolean projectAdmin;

    @SerializedName("created-on")
    String dateCreated;

    @SerializedName("last-changed-on")
    String dateChanged;

    @SerializedName("startDate")
    String dateStart;

    @SerializedName("endDate")
    String dateEnd;

    @SerializedName("start-page")
    String pageStart;

    @SerializedName("logo")
    String logoURL;

    @SerializedName("notifyeveryone")
    boolean notifyAll;

    @SerializedName("harvest-timers-enabled")
    String harvestTimersEnabled;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TWCompany getCompany() {
        return company;
    }

    public void setCompany(TWCompany company) {
        this.company = company;
    }

    public TWCategory getCategory() {
        return category;
    }

    public void setCategory(TWCategory category) {
        this.category = category;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowAnnouncement() {
        return showAnnouncement;
    }

    public void setShowAnnouncement(boolean showAnnouncement) {
        this.showAnnouncement = showAnnouncement;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isProjectAdmin() {
        return projectAdmin;
    }

    public void setProjectAdmin(boolean projectAdmin) {
        this.projectAdmin = projectAdmin;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(String dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getPageStart() {
        return pageStart;
    }

    public void setPageStart(String pageStart) {
        this.pageStart = pageStart;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public boolean isNotifyAll() {
        return notifyAll;
    }

    public void setNotifyAll(boolean notifyAll) {
        this.notifyAll = notifyAll;
    }

    public String isHarvestTimersEnabled() {
        return harvestTimersEnabled;
    }

    public void setHarvestTimersEnabled(String harvestTimersEnabled) {
        this.harvestTimersEnabled = harvestTimersEnabled;
    }
}
