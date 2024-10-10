package com.huajia.venusdesktop.framework.app;

import android.app.Application;

import com.huajia.venusdesktop.framework.application.ApplicationManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;

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
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.addLogAdapter(new DiskLogAdapter());
    }

    @Override
    public void onTerminate() {
        ActivityLifecycleManager.getInstance().release(this);
        super.onTerminate();
    }
}
