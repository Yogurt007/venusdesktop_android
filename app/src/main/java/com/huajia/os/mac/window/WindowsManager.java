package com.huajia.os.mac.window;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.huajia.os.mac.application.BaseApplication;
import com.huajia.os.mac.application.camera.CameraApplication;
import com.huajia.os.mac.window.bean.WindowsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: huajia
 * @date: 2023/9/13 22:50
 */

public class WindowsManager {

    private Context context;

    //后台
    private final HashMap<String, WindowsBean> backgroundApplication = new HashMap<>();

    private static volatile WindowsManager instance;

    private WindowsManager(){}

    public static WindowsManager getInstance(){
        if (instance == null){
            synchronized (WindowsManager.class){
                if (instance == null){
                    instance = new WindowsManager();
                }
            }
        }
        return instance;
    }

    public void initWindow(Context context,String type){
        Window window;
        BaseApplication application;
        application = getApplication(context,type);
        //应用存活，不需要打开
        if (application == null){
            return;
        }
        window = application.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;  //设置参考系
        //起始坐标
        layoutParams.x = 10;
        layoutParams.y = 300;
        window.setAttributes(layoutParams);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.getDecorView().setOnTouchListener(new WindowsOnTouchListener(window,application,layoutParams));
        application.show();
        if (backgroundApplication.containsKey(type)){
            backgroundApplication.remove(type);
        }
        backgroundApplication.put(type,new WindowsBean(window,application));
    }

    private BaseApplication getApplication(Context context, String type) {
        if (checkBackground(type)){
            return null;
        }
        BaseApplication application = null;
        switch (type){
            case WindowsConstant.CameraApplication:
                application = new CameraApplication(context);
                break;
        }
        return application;
    }

    /**
     * 检查后台是否存活
     * @return
     */
    @SuppressLint("WrongConstant")
    private boolean checkBackground(String type){
        if (!backgroundApplication.containsKey(type)){
            return false;
        }
        WindowsBean windowsBean = backgroundApplication.get(type);
        BaseApplication application = windowsBean.getApplication();
        if (windowsBean == null || application == null){
            return false;
        }
        //应用存活
        if (application.isShowing()){
            Window window = application.getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
//            layoutParams.flags = 8388610;
            layoutParams.flags = 0x800002;
            window.setAttributes(layoutParams);
            application.show();
            return true;
        }
        return false;
    }

}











