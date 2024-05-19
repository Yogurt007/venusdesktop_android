package com.huajia.mac.framework.app;

import android.app.Application;
import android.util.Log;

/**
 * @Author: HuaJ1a
 * @Email: 821759439@qq.com
 * @Date: 2024/5/12
 * @Description:
 */
public class ActivityLifecycleManager {
    private static final String TAG = "ActivityLifecycleManager";

    private ActivityLifecycleManager() {}

    private static ActivityLifecycleManager instance;

    private ActivityLifecycleImp activityLifecycleImp;

    public static ActivityLifecycleManager getInstance() {
        if (instance == null) {
            synchronized (ActivityLifecycleManager.class) {
                if (instance == null) {
                    instance = new ActivityLifecycleManager();
                }
            }
        }
        return instance;
    }

    public void init(Application application) {
        if (application == null) {
            Log.i(TAG, "application is null");
            return;
        }
        if (activityLifecycleImp == null) {
            activityLifecycleImp = new ActivityLifecycleImp();
        }
        application.registerActivityLifecycleCallbacks(activityLifecycleImp);
    }

    public void release(Application application) {
        if (application == null) {
            Log.i(TAG, "application is null");
            return;
        }
        application.unregisterActivityLifecycleCallbacks(activityLifecycleImp);
    }

}
