package com.teamwork.example.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;

import com.teamwork.example.ExampleApp;

//This class is not necessary for this project but I created as a way to show how I would do in a bigger project
public class FunctionUtil {

    //This class is not necessary for this project but I created as a way to show how I would do in a bigger project
    public static class Preferences{
        private static final String PREFERENCES_TEST = "PREFERENCES_TEST";

        private static SharedPreferences getPreferences(){
            return ExampleApp.getInstance().getSharedPreferences("ExampleApp", Context.MODE_PRIVATE);
        }

        public static void setTest(String test){
            getPreferences().edit().putString(PREFERENCES_TEST, test).apply();
        }

        public static String getTest(){
            return getPreferences().getString(PREFERENCES_TEST, null);
        }
    }

    public static boolean isInternetAvailable(){
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) ExampleApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

            if(connectivityManager != null){
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
        }catch (Exception error){
            //TODO CRASHLYTICS ?
        }

        return false;
    }

    public static RoundedBitmapDrawable roundBitmap(Bitmap bitmap){
        Resources resources = ExampleApp.getInstance().getResources();
        RoundedBitmapDrawable roundedBitmap = RoundedBitmapDrawableFactory.create(resources, bitmap);
        roundedBitmap.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);

        return roundedBitmap;
    }

    public static void showSnakeMessage(View view, int messageID){
        Snackbar.make(view, ExampleApp.getInstance().getString(messageID), Snackbar.LENGTH_SHORT).show();
    }
}
