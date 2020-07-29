package com.youzhuan.demoservcie;

import android.app.Application;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.youzhuan.iot.constant.YzDevType;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author 樱花满地集于我心
 * Create By 2020-07-22
 */
public class App extends Application {
    private static App application;
    private JSONObject configJson;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        readTypeConfig();
    }

    private void readTypeConfig() {
        try {
            InputStream inputStream = getAssets().open("type_config.json");
            JSONReader jsonReader = new JSONReader(new InputStreamReader(inputStream));
            configJson = jsonReader.readObject(JSONObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static App getInstance(){
        return application;
    }

    public String getType(String code){
        if (configJson == null){
            return null;
        }
        if(configJson.containsKey(code)){
            return configJson.getString(code);
        }
        return null;
    }
}
