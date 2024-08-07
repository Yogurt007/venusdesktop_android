package com.huajia.venusdesktop.framework.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/10
 * @Description:
 */
public class SharedPreferencesManager {
    private static final String SHARED_PREFERENCES_NAME = "mac_sp";

    private static SharedPreferencesManager instance;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private SharedPreferencesManager(){
    }

    public static SharedPreferencesManager getInstance() {
        if (instance == null) {
            synchronized (SharedPreferencesManager.class) {
                if (instance == null) {
                    instance = new SharedPreferencesManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBoolean(String key, boolean defaultValue){
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }


}
