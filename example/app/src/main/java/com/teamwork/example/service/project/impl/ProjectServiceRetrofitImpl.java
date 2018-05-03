package com.teamwork.example.service.project.impl;

import android.support.annotation.NonNull;

import com.teamwork.example.pojo.response.ExampleResponsePojo;
import com.teamwork.example.pojo.response.ProjectsResponsePojo;
import com.teamwork.example.pojo.response.TasksResponsePojo;
import com.teamwork.example.service.ExampleServiceR;
import com.teamwork.example.service.project.callback.ProjectServiceCallback;
import com.teamwork.example.service.project.contract.ProjectService;
import com.teamwork.example.service.project.endpoint.ProjectEndPointR;
import com.teamwork.example.util.EnumUtil;
import com.teamwork.example.util.FunctionUtil;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectServiceRetrofitImpl extends ExampleServiceR implements ProjectService{
    private ProjectServiceCallback callback;

    public ProjectServiceRetrofitImpl(ProjectServiceCallback callback){
        this.callback = callback;
    }

    @Override
    public void releaseCallback(){
        this.callback = null;
    }

    @Override
    protected void validateRequest() throws Exception{
        if(!FunctionUtil.isInternetAvailable()){
            if(callback != null)
                callback.onProjectRequestError(EnumUtil.RESPONSE_CODE.CONNECTION_ERROR);

            throw new UnknownHostException();
        }
    }

    //There's too much repeated code here. I need to have a look at how to improve this process
    @Override
    public void listProjects(int pageCursor) {
        try {
            validateRequest();

            Call<ProjectsResponsePojo> call  = getRetrofit().create(ProjectEndPointR.class).listProjects(pageCursor);

            call.enqueue(new Callback<ProjectsResponsePojo>() {
                @Override
                public void onResponse(@NonNull Call<ProjectsResponsePojo> call, @NonNull Response<ProjectsResponsePojo> response) {
                    ProjectsResponsePojo responseProjects;

                    //It's not safe to assume that a success http request is equivalent to success on the host
                    if(response.body() != null && response.code() == 200){
                        responseProjects = setPagination(response);
                        responseProjects.setCode(EnumUtil.RESPONSE_CODE.SUCCESS);

                    }else {
                        responseProjects = new ProjectsResponsePojo();
                        responseProjects.setCode(EnumUtil.RESPONSE_CODE.fromIntCode(response.code()));
                    }

                    if(callback != null){
                        callback.onListProjectsResponse(responseProjects);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProjectsResponsePojo> call, @NonNull Throwable throwable) {
                    ProjectsResponsePojo responseProjects = new ProjectsResponsePojo();
                    responseProjects.setCode(EnumUtil.RESPONSE_CODE.UNKNOWN_ERROR);

                    if(callback != null){
                        callback.onListProjectsResponse(responseProjects);
                    }
                }
            });
        } catch (Exception error) {
            ProjectsResponsePojo responseProjects = new ProjectsResponsePojo();
            responseProjects.setCode(EnumUtil.RESPONSE_CODE.UNKNOWN_ERROR);

            if(error instanceof UnknownHostException){
                responseProjects.setCode(EnumUtil.RESPONSE_CODE.CONNECTION_ERROR);
            }

            if(callback != null){
                callback.onListProjectsResponse(responseProjects);
            }
        }
    }

    //There's too much repeated code here. I need to have a look at how to improve this process
    @Override
    public void requestFavouriteProject(String projectId, boolean favourite) {
        try {
            validateRequest();

            Call<ExampleResponsePojo> call;

            if(favourite){
                call = getRetrofit().create(ProjectEndPointR.class).favouriteProject(projectId);

            }else{
                call = getRetrofit().create(ProjectEndPointR.class).cancelProjectFavourite(projectId);
            }

            call.enqueue(new Callback<ExampleResponsePojo>() {
                @Override
                public void onResponse(@NonNull Call<ExampleResponsePojo> call, @NonNull Response<ExampleResponsePojo> response) {
                    ExampleResponsePojo responseFavourite;

                    //It's not safe to assume that a success http request is equivalent to success on the host
                    if(response.body() != null && response.code() == 200){
                        responseFavourite = new ExampleResponsePojo();
                        responseFavourite.setCode(EnumUtil.RESPONSE_CODE.SUCCESS);

                    }else {
                        responseFavourite = new ExampleResponsePojo();
                        responseFavourite.setCode(EnumUtil.RESPONSE_CODE.fromIntCode(response.code()));
                    }

                    if(callback != null){
                        callback.onRequestFavouriteProjectResponse(responseFavourite);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ExampleResponsePojo> call, @NonNull Throwable throwable) {
                    ExampleResponsePojo responseFavourite = new ExampleResponsePojo();
                    responseFavourite.setCode(EnumUtil.RESPONSE_CODE.UNKNOWN_ERROR);

                    if(callback != null){
                        callback.onRequestFavouriteProjectResponse(responseFavourite);
                    }
                }
            });
        } catch (Exception error) {
            ExampleResponsePojo responseFavourite = new ExampleResponsePojo();
            responseFavourite.setCode(EnumUtil.RESPONSE_CODE.UNKNOWN_ERROR);

            if(error instanceof UnknownHostException){
                responseFavourite.setCode(EnumUtil.RESPONSE_CODE.CONNECTION_ERROR);
            }

            if(callback != null){
                callback.onRequestFavouriteProjectResponse(responseFavourite);
            }
        }
    }

    //There's too much repeated code here. I need to have a look at how to improve this process
    @Override
    public void listMyProjectTasks(String projectId) {
        try {
            validateRequest();

            //Logged user id should be passed here. Hard coded for demo purpose.
            Call<TasksResponsePojo> call  = getRetrofit().create(ProjectEndPointR.class).listMyProjectTasks(projectId, "230907");

            call.enqueue(new Callback<TasksResponsePojo>() {
                @Override
                public void onResponse(@NonNull Call<TasksResponsePojo> call, @NonNull Response<TasksResponsePojo> response) {
                    TasksResponsePojo responseTasks;

                    //It's not safe to assume that a success http request is equivalent to success on the host
                    if(response.body() != null && response.code() == 200){
                        responseTasks = setPagination(response);
                        responseTasks.setCode(EnumUtil.RESPONSE_CODE.SUCCESS);

                    }else {
                        responseTasks = new TasksResponsePojo();
                        responseTasks.setCode(EnumUtil.RESPONSE_CODE.fromIntCode(response.code()));
                    }

                    if(callback != null){
                        callback.onListProjectTasksResponse(responseTasks);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TasksResponsePojo> call, @NonNull Throwable throwable) {
                    TasksResponsePojo responseTasks = new TasksResponsePojo();
                    responseTasks.setCode(EnumUtil.RESPONSE_CODE.UNKNOWN_ERROR);

                    if(callback != null){
                        callback.onListProjectTasksResponse(responseTasks);
                    }
                }
            });
        } catch (Exception error) {
            TasksResponsePojo responseTasks = new TasksResponsePojo();
            responseTasks.setCode(EnumUtil.RESPONSE_CODE.UNKNOWN_ERROR);

            if(error instanceof UnknownHostException){
                responseTasks.setCode(EnumUtil.RESPONSE_CODE.CONNECTION_ERROR);
            }

            if(callback != null){
                callback.onListProjectTasksResponse(responseTasks);
            }
        }
    }
}
