package com.huajia.os.mac.window;

import android.content.Context;
import android.util.Size;

import com.huajia.os.mac.application.BaseApplication;
import com.huajia.os.mac.application.album.AlbumApplication;
import com.huajia.os.mac.application.camera.CameraApplication;
import com.huajia.os.mac.application.draw.DrawApplication;
import com.huajia.os.mac.application.music.MusicApplication;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/1/3
 * @Description: 创建dialog
 */
public class WindowsFactory {
    private static WindowsFactory instance;

    private WindowsFactory(){
    }

    public static WindowsFactory getInstance(){
        if (instance == null){
            synchronized (WindowsFactory.class){
                if (instance == null){
                    instance = new WindowsFactory();
                }
            }
        }
        return instance;
    }

    public BaseApplication getWindow(Context context, String type){
        BaseApplication application = null;
        switch (type){
            case WindowsConstants.CameraApplication:
                application = new CameraApplication(context);
                break;
            case WindowsConstants.MusicApplication:
                application = new MusicApplication(context);
                break;
            case WindowsConstants.AlbumApplication:
                application = new AlbumApplication(context);
                break;
            case WindowsConstants.DrawApplication:
                application = new DrawApplication(context);
                break;
        }
        return application;
    }
}
