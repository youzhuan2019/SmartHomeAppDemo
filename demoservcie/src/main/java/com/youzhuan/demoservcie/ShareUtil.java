package com.youzhuan.demoservcie;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author 樱花满地集于我心
 * Create By 2020-06-08
 */
public class ShareUtil {

    public static final String IS_LOGIN = "isLogin";
    public static final String USER = "user";
    public static final String PWD = "pwd";

    private static ShareUtil saveUtil;
    private final int mode = Context.MODE_PRIVATE;
    private SharedPreferences data = null;
    private SharedPreferences.Editor editor = null;
    private static final String name = "hjj";
    
    private ShareUtil(Context context) {
        if (null == data) {
            data = context.getSharedPreferences(name, mode);
        }
        if (null == editor) {
            editor = data.edit();
        }
    }

    public static ShareUtil getInstance(Context context) {
        if (null == saveUtil) {
            saveUtil = new ShareUtil(context.getApplicationContext());
        }
        return saveUtil;
    }

    public Boolean getBoolean1(String key) {
        return data.getBoolean(key, true);
    }

    public void release() {
        saveUtil = null;
    }

    public void put(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }
    public void put(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public void put(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void put(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return data.getInt(key, 0);
    }
    public long getLong(String key) {
        return data.getLong(key, 0L);
    }

    public int getInt(String key, int defaultValue) {
        return data.getInt(key, defaultValue);
    }

    public String getString(String key) {
        return data.getString(key, "");
    }

    public String getString(String key, String defaultName) {
        return data.getString(key, defaultName);
    }
    public Boolean getBoolean(String key) {
        return data.getBoolean(key, false);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return data.getBoolean(key, defaultValue);
    }


}
