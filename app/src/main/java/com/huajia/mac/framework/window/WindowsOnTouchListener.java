package com.huajia.mac.framework.window;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huajia.mac.base.BaseApplication;

/**
 * @author: huajia
 * @date: 2023/9/13 23:03
 */

public class WindowsOnTouchListener implements View.OnTouchListener{

    private Window window;

    private BaseApplication application;

    private WindowManager.LayoutParams layoutParams;

    private int x;

    private int y;

    private int downX;

    private int downY;

    private int viewX;

    private int viewY;

    /**
     * 双击事件
     */
    private static final long DOUBLE_CLICK_TIME_DELTA = 300; // 双击事件的时间间隔（毫秒）

    private long lastClickTime = 0;

    public WindowsOnTouchListener(Window window,BaseApplication application,WindowManager.LayoutParams layoutParams,int viewX,int viewY){
        this.window = window;
        this.application = application;
        this.layoutParams = layoutParams;
        this.viewX = viewX;
        this.viewY  =viewY;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        //移动事件
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:  //按下
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();

                //双击事件 关闭app
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
                    application.dismiss();
                }
                lastClickTime = clickTime;
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
                layoutParams.x = viewX;
                layoutParams.y = viewY;
                window.setAttributes(layoutParams);
                application.show();
                break;
            default:
                break;
        }
        return false;
    }
}
