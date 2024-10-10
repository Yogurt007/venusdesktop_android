package com.huajia.venusdesktop.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import com.orhanobut.logger.Logger;

import androidx.core.content.ContextCompat;

/**
 * 权限校验帮助类
 */
public class PermissionHelper {

    private static final String TAG = "PermissionHelper";

    public static void checkCameraPermission(Context context){
        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
            return;
        }
        try {
            Activity activity = (Activity) context;
            activity.requestPermissions(new String[]{ Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO },101);
        }catch (Exception e){
            Logger.i(TAG,e.getMessage());
        }
    }

    public static void checkWifiPermission(Context context) {
        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) ) {
            return;
        }
        try {
            Activity activity = (Activity) context;
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_COARSE_LOCATION},101);
        }catch (Exception e){
            Logger.i(TAG,e.getMessage());
        }
    }

}
