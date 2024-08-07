package com.huajia.venusdesktop.framework.application;

import android.graphics.drawable.Drawable;

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
    private String routerPath;

    public ApplicationBean(String type, Drawable icon, String routerPath) {
        this.type = type;
        this.icon = icon;
        this.routerPath = routerPath;
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

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }
}
