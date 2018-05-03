package com.teamwork.example.service.project.callback;

import com.teamwork.example.pojo.response.ExampleResponsePojo;
import com.teamwork.example.pojo.response.ProjectsResponsePojo;
import com.teamwork.example.pojo.response.TasksResponsePojo;
import com.teamwork.example.util.EnumUtil;

public interface ProjectServiceCallback {

    void onProjectRequestError(EnumUtil.RESPONSE_CODE error);

    void onListProjectsResponse(ProjectsResponsePojo response);

    void onRequestFavouriteProjectResponse(ExampleResponsePojo response);

    void onListProjectTasksResponse(TasksResponsePojo response);
}
