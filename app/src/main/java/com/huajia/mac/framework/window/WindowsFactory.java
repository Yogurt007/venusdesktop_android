package com.huajia.mac.framework.window;

import android.content.Context;
import android.util.Size;

import com.huajia.mac.service.application.album.AlbumApplication;
import com.huajia.mac.service.application.draw.DrawApplication;
import com.huajia.mac.service.application.guitar.GuitarApplication;
import com.huajia.mac.base.BaseApplication;
import com.huajia.mac.service.application.camera.CameraApplication;
import com.huajia.mac.service.application.music.MusicApplication;
import com.huajia.mac.service.application.tang.TangPoemApplication;
import com.huajia.mac.service.dialog.PermissionDialog;
import com.huajia.mac.service.dialog.bluetooth.BluetoothDialog;
import com.huajia.mac.service.dialog.VolumeDialog;
import com.huajia.mac.service.dialog.wifi.WifiDialog;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/1/3
 * @Description: app工厂
 */
public class WindowsFactory {
    private static WindowsFactory instance;

    private WindowsFactory(){
    }

    public static BaseApplication getWindow(Context context, String type){
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
            case WindowsConstants.GuitarApplication:
                application = new GuitarApplication(context);
                break;
            case WindowsConstants.WifiDialog:
                application = new WifiDialog(context);
                application.setSize(new Size(WindowsManager.getInstance().getMaxWidthApplication() / 3,
                        WindowsManager.getInstance().getMaxHeightApplication() / 2));
                break;
            case WindowsConstants.VolumeDialog:
                application = new VolumeDialog(context);
                application.setSize(new Size(WindowsManager.getInstance().getMaxWidthApplication() / 3,
                        WindowsManager.getInstance().getMaxHeightApplication() / 3));
                break;
            case WindowsConstants.BluetoothDialog:
                application = new BluetoothDialog(context);
                application.setSize(new Size(WindowsManager.getInstance().getMaxWidthApplication() / 3,
                        WindowsManager.getInstance().getMaxHeightApplication() / 2));
                break;
            case WindowsConstants.TangPoemApplication:
                application = new TangPoemApplication(context);
                application.setSize(new Size((int) (WindowsManager.getInstance().getMaxWidthApplication() / 1.5f),
                        WindowsManager.getInstance().getMaxHeightApplication()));
            case WindowsConstants.PermissionDialog:
                application = new PermissionDialog(context);
                application.setSize(new Size(WindowsManager.getInstance().getMaxWidthApplication() / 3,
                        WindowsManager.getInstance().getMaxHeightApplication() / 2));
                break;
        }
        return application;
    }
}
