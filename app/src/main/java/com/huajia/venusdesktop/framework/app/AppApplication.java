package com.huajia.venusdesktop.framework.app;

import android.app.Application;

import com.huajia.venusdesktop.framework.application.ApplicationManager;

/**
 * @Author: HuaJ1a
 * @Email: 821759439@qq.com
 * @Date: 2024/5/12
 * @Description:
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifecycleManager.getInstance().init(this);
        ApplicationManager.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        ActivityLifecycleManager.getInstance().release(this);
        super.onTerminate();
    }
}
