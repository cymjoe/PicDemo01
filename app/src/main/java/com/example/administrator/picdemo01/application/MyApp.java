package com.example.administrator.picdemo01.application;

import android.app.Application;

/**
 * Created by Administrator on 2018/5/8.
 */

public class MyApp extends Application {
    private static MyApp instance = null;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
}
