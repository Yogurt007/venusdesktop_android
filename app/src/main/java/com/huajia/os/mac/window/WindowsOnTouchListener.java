package com.huajia.os.mac.window;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huajia.os.mac.application.BaseApplication;

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
    private int viewX = 10;
    private int viewY = 300;

    public WindowsOnTouchListener(Window window,BaseApplication application,WindowManager.LayoutParams layoutParams){
        this.window = window;
        this.application = application;
        this.layoutParams = layoutParams;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:  //按下
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();
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
            case MotionEvent.ACTION_UP:  //单击事件
                int upX = (int) event.getRawX();
                int upY = (int) event.getRawY();
                if (downX == upX && downY == upY) {
                }
                break;
            default:
                break;
        }
        return false;
    }
}
