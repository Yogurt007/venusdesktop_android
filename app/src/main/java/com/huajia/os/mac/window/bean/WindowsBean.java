package com.huajia.os.mac.window.bean;

import android.view.Window;

import com.huajia.os.mac.application.BaseApplication;

/**
 * @author: huajia
 * @date: 2023/9/13 23:12
 */

public class WindowsBean {
    private Window window;

    private BaseApplication application;

    public WindowsBean(Window window,BaseApplication application){
        this.window = window;
        this.application = application;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public BaseApplication getApplication() {
        return application;
    }

    public void setApplication(BaseApplication application) {
        this.application = application;
    }
}
