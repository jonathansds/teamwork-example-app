package com.teamwork.example;

import android.app.Application;
import android.content.Context;

//This class is not necessary for this project but I created as a way to show how I would do in a bigger project
public class ExampleApp extends Application {
    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();

        ExampleApp.instance = this;
    }

    /**
     * @return the aplication instance
     */
    public static Context getInstance(){
        return instance;
    }
}
