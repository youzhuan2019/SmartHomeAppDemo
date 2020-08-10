package com.youzhuan.demoservcie;

import android.app.Application;

/**
 * @author 樱花满地集于我心
 * Create By 2020-07-22
 */
public class App extends Application {
    private static App application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

}
