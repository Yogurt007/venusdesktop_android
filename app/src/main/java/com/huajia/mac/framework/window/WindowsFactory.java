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
import com.huajia.os.mac.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/1/3
 * @Description: app工厂
 */
public class WindowsFactory {

    private WindowsFactory() {
    }

    /**
     * 构造application对象
     *
     * @param context context
     * @param router 路由
     * @param params 启动参数
     * @return
     */
    public static BaseApplication createWindow(Context context, WindowsRouter router, HashMap<Object, Object> params) {
        BaseApplication application = null;
        switch (router){
            case CameraApplication:
                application = new CameraApplication(context);
                break;
            case MusicApplication:
                application = new MusicApplication(context);
                break;
            case AlbumApplication:
                application = new AlbumApplication(context);
                break;
            case DrawApplication:
                application = new DrawApplication(context);
                break;
            case GuitarApplication:
                application = new GuitarApplication(context);
                break;
            case WifiDialog:
                application = new WifiDialog(context);
                application.setSize(new Size(WindowsManager.getInstance().getMaxWidthApplication() / 3,
                        WindowsManager.getInstance().getMaxHeightApplication() / 2));
                break;
            case VolumeDialog:
                application = new VolumeDialog(context);
                application.setSize(new Size(WindowsManager.getInstance().getMaxWidthApplication() / 3,
                        WindowsManager.getInstance().getMaxHeightApplication() / 3));
                break;
            case BluetoothDialog:
                application = new BluetoothDialog(context);
                application.setSize(new Size(WindowsManager.getInstance().getMaxWidthApplication() / 3,
                        WindowsManager.getInstance().getMaxHeightApplication() / 2));
                break;
            case TangPoemApplication:
                application = new TangPoemApplication(context);
                application.setSize(new Size((int) (WindowsManager.getInstance().getMaxWidthApplication() / 1.5f),
                        WindowsManager.getInstance().getMaxHeightApplication()));
            case PermissionDialog:
                application = new PermissionDialog(context, params);
                application.setSize(new Size((int) (WindowsManager.getInstance().getMaxWidthApplication() / 2.5f),
                        (int) (WindowsManager.getInstance().getMaxHeightApplication() / 1.5f)));
                break;
        }
        return application;
    }
}
