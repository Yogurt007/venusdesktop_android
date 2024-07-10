package com.huajia.mac.framework.application;

import android.graphics.drawable.Drawable;

import com.huajia.mac.framework.window.WindowsRouter;

/**
 * Description: 应用bean
 * Author: HuaJ1a
 * Date: 2024/6/30
 */
public class ApplicationBean {

    /**
     * 类型
     */
    private String type;

    /**
     * 图标
     */
    private Drawable icon;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 路由,用于连接application和window
     */
    private WindowsRouter router;

    public ApplicationBean(String type, Drawable icon, WindowsRouter router) {
        this.type = type;
        this.icon = icon;
        this.router = router;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public WindowsRouter getRouter() {
        return router;
    }

    public void setRouter(WindowsRouter router) {
        this.router = router;
    }
}
