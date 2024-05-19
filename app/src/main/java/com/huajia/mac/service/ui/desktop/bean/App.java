package com.huajia.mac.service.ui.desktop.bean;

import android.graphics.drawable.Drawable;

public class App {
    private String name;

    private Drawable icon;

    private String pkgName;

    public App(String name,Drawable icon,String pkgName){
        this.name = name;
        this.icon = icon;
        this.pkgName = pkgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
