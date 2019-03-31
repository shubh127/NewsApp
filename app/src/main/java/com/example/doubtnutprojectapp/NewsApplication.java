package com.example.doubtnutprojectapp;

import android.app.Application;

public class NewsApplication extends Application {
    private static NewsApplication myTimesApplicationInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        myTimesApplicationInstance = this;
    }
    public static NewsApplication getMyTimesApplicationInstance(){
        return myTimesApplicationInstance;
    }
}