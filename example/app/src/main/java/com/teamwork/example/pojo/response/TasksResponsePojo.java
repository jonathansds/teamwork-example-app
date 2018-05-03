package com.teamwork.example.pojo.response;

import com.google.gson.annotations.SerializedName;
import com.teamwork.example.model.TWTask;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class TasksResponsePojo extends ExampleResponsePojo{

    @SerializedName("todo-items")
    List<TWTask> tasks;

    public List<TWTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<TWTask> tasks) {
        this.tasks = tasks;
    }
}
