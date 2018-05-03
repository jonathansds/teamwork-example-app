package com.teamwork.example.service.project.endpoint;

import com.teamwork.example.pojo.response.ExampleResponsePojo;
import com.teamwork.example.pojo.response.ProjectsResponsePojo;
import com.teamwork.example.pojo.response.TasksResponsePojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectEndPointR {

    @GET("projects.json")
    Call<ProjectsResponsePojo> listProjects(@Query("page") int pageCursor);

    @PUT("projects/{id}/star.json")
    Call<ExampleResponsePojo> favouriteProject(@Path("id") String projectId);

    @PUT("projects/{id}/unstar.json")
    Call<ExampleResponsePojo> cancelProjectFavourite(@Path("id") String projectId);

    @GET("projects/{projectId}/tasks.json")
    Call<TasksResponsePojo> listMyProjectTasks(@Path("projectId") String projectId, @Query("responsible-party-ids") String ownerId);
}
