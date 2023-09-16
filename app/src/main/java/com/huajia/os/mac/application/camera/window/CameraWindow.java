package com.huajia.os.mac.application.camera.window;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huajia.os.mac.R;
import com.huajia.os.mac.application.camera.CameraApplication;

/**
 * @author: huajia
 * @date: 2023/9/12 21:38
 */

public class CameraWindow {
    private Window win;
    private WindowManager.LayoutParams lp;
    public CameraApplication application;
    private int viewX = 10;
    private int viewY = 300;
    private Context mContext;

    public static CameraWindow instance;

    public static CameraWindow getInstance() {
        if (instance == null) {
            instance = new CameraWindow();
        }
        return instance;
    }

    public void initDialog(Context context) {
        mContext = context;
        application = new CameraApplication(context);
        win = application.getWindow(); //获取Window对象
        //getDecorView()函数 -- 获取顶层View
        win.getDecorView().setOnTouchListener(new FloatingOnTouchListener()); //设置拖动响应
        lp = win.getAttributes();
        lp.gravity = Gravity.LEFT | Gravity.TOP;  //设置参考系
        lp.x = viewX;  //设置起始位置
        lp.y = viewY;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;  //设置不聚焦,不会阻碍下层控件响应
        win.setAttributes(lp);  //将参数设置回去
//        win.setLayout(500, 500); //设置显示的宽高
        win.setBackgroundDrawableResource(android.R.color.transparent);  //设置悬浮框图标
        application.show();
    }


    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        private int downX;
        private int downY;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:  //按下
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    downX = (int) event.getRawX();
                    downY = (int) event.getRawY();
                    Log.i("hc", "onTouch:ACTION_DOWN ");
                    break;
                case MotionEvent.ACTION_MOVE:  //移动
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    viewX = viewX + movedX;
                    viewY = viewY + movedY;
                    lp.x = viewX;
                    lp.y = viewY;
                    win.setAttributes(lp);
                    application.show();
                    Log.i("hc", "onTouch:ACTION_MOVE ");
                    break;
                case MotionEvent.ACTION_UP:  //单击事件
                    int upX = (int) event.getRawX();
                    int upY = (int) event.getRawY();
                    if (downX == upX && downY == upY) {
                        Log.i("hc", "onTouch:onClick ");
                    }
                    Log.i("hc", "onTouch:ACTION_UP ");
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
