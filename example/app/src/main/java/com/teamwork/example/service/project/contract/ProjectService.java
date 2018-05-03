package com.teamwork.example.service.project.contract;


public interface ProjectService {

    //This method is present in all service interfaces.
    // In future, should create an interface to put this method in and make all service interfaces extends of it
    void releaseCallback();

    void listProjects(int pageCursor);

    void requestFavouriteProject(String projectId, boolean favourite);

    void listMyProjectTasks(String projectId);
}