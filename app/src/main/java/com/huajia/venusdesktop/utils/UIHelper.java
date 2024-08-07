package com.huajia.venusdesktop.utils;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class UIHelper {
    private static final String TAG = "UIHelper";

    /**
     * 初始化activity样式
     *
     * @param activity
     */
    public static void initActivityUI(AppCompatActivity activity) {
        int uiOption = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        activity.getWindow().getDecorView().setSystemUiVisibility(uiOption);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager.LayoutParams lp= activity.getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;
        activity.getWindow().setAttributes(lp);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * dp转px
     */
    public static int dp2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * px转dp
     */
    public static int px2dp(Context ctx, float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获得view左下角的坐标
     *
     * @param view view
     * @return 左下角的坐标
     */
    public static Point getViewLBottomCoordinate(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Point point = new Point();
        point.set(location[0] - view.getWidth(), location[1] + view.getHeight());
        Log.i(TAG,"getViewLBottomCoordinate,x: " + point.x + " , y: " + point.y);
        return point;
    }
}
