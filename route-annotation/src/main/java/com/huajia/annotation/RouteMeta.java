package com.huajia.annotation;

/**
 * Description: 路由元数据
 * Author: HuaJ1a
 * Date: 2024/7/10
 */
public class RouteMeta {

    /**
     * 路径
     */
    private String path;

    /**
     * 类名
     */
    private String className;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 高度占整个屏幕的百分比 - 默认50%
     */
    private float heightPercent;

    /**
     * 宽度占整个屏幕的百分比 - 默认50%
     */
    private float widthPercent;

    public RouteMeta(String path, String className, String packageName, float heightPercent, float widthPercent) {
        this.path = path;
        this.className = className;
        this.packageName = packageName;
        this.heightPercent = heightPercent;
        this.widthPercent = widthPercent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public float getHeightPercent() {
        return heightPercent;
    }

    public void setHeightPercent(float heightPercent) {
        this.heightPercent = heightPercent;
    }

    public float getWidthPercent() {
        return widthPercent;
    }

    public void setWidthPercent(float widthPercent) {
        this.widthPercent = widthPercent;
    }
}
