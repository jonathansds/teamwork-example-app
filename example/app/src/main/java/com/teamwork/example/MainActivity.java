package com.teamwork.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.teamwork.example.activity.HomeActivity;

//Here I usually decide if I redirect the user to login screen or skip login or force app update...
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
