package com.huajia.venusdesktop;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.huajia.venusdesktop.framework.window.WindowsConstants;
import com.huajia.venusdesktop.framework.window.WindowsManager;

/**
 * @Author: HuaJ1a
 * @Email: 821759439@qq.com
 * @Date: 2023/5/13
 * @Description: 处理UI
 */
public class UIMainActivity extends MainActivity {
    private static final String TAG = "MainActivity";

    private View desktop;

    private View topContainer;

    private View bottomContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,"按下： x : " + event.getRawX() + ", y : " + event.getRawY());
                break;
        }
        return super.onTouchEvent(event);
    }

    private void initView(){
        desktop = findViewById(R.id.main_container);
        topContainer = findViewById(R.id.top_container);
        bottomContainer = findViewById(R.id.bottom_container);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        getAppMaxLayout();
    }

    /**
     * 获取app最大宽高
     */
    private void getAppMaxLayout(){
        desktop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                desktop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                WindowsManager.getInstance().setMaxHeightApplication(desktop.getHeight() - WindowsConstants.APP_MARGIN);
                WindowsManager.getInstance().setMaxWidthApplication(desktop.getWidth() - WindowsConstants.APP_MARGIN);
            }
        });
        topContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                topContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                WindowsManager.getInstance().setMaxHeightApplication(WindowsManager.getInstance().getMaxHeightApplication() - topContainer.getHeight());
                WindowsManager.getInstance().setTopHeight(topContainer.getHeight());
            }
        });
        bottomContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bottomContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                WindowsManager.getInstance().setMaxHeightApplication(WindowsManager.getInstance().getMaxHeightApplication() - bottomContainer.getHeight());
            }
        });
    }
}