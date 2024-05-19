package com.huajia.mac.framework.window;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.huajia.os.mac.R;
import com.huajia.mac.base.BaseApplication;
import com.huajia.mac.utils.SizeUtils;
import com.huajia.mac.utils.ToastUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: huajia
 * @date: 2023/9/13 22:50
 * app管理类
 */

public class WindowsManager {
    private static final String TAG = "WindowsManager";

    private Context context;

    /**
     * 后台
     */
    private final ConcurrentHashMap<String, BaseApplication> backgroundApplication = new ConcurrentHashMap<>();

    private static WindowsManager instance;

    /**
     * app可以设置的最大高度
     */
    private int maxHeightApplication;

    /**
     * app可以设置的最大宽度
     */
    private int maxWidthApplication;

    private int topHeight;

    private WindowsManager(){}

    public static WindowsManager getInstance(){
        if (instance == null){
            synchronized (WindowsManager.class){
                if (instance == null){
                    instance = new WindowsManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context){
        this.context = context;
    }

    public void initWindow(String type) {
        initWindow(type, true, null);
    }

    public void initWindow(String type, Point coordinate) {
        initWindow(type, false, coordinate);
    }

    /**
     * 打开弹窗
     * @param type 类型
     * @param isNeedMove 是否需要可移动
     * @param coordinate 起始坐标
     */
    public void initWindow(String type, boolean isNeedMove, Point coordinate) {
        Window window;
        BaseApplication application;
        application = getApplication(type);
        //应用存活，不需要打开
        if (application == null){
            return;
        }
        window = application.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (coordinate != null) {
            // 需要指定坐标的弹窗
            layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            layoutParams.x = coordinate.x;
            layoutParams.y = WindowsManager.getInstance().getTopHeight();
            layoutParams.windowAnimations = R.style.DialogOpenAndCloseAnim;
            // 背景透明
            layoutParams.dimAmount = 0f;
        } else if (!isNeedMove && coordinate == null) {
            // 提示弹窗： 1、居中 2、不需要移动
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.windowAnimations = R.style.AppOpenAndCloseAnim;
        } else {
            // app类型
            layoutParams.gravity = Gravity.CENTER | Gravity.TOP;
            layoutParams.y = topHeight + WindowsConstants.APP_MARGIN / 2;
            layoutParams.windowAnimations = R.style.AppOpenAndCloseAnim;
            window.getDecorView().setOnTouchListener(new WindowsOnTouchListener(window,application,layoutParams,layoutParams.x, layoutParams.y));
        }
        window.setAttributes(layoutParams);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        //设置app宽高
        window.setLayout(SizeUtils.getApplicationWidth(application),SizeUtils.getApplicationHeight(application));
        application.show();
        backgroundApplication.put(type,application);
    }

    /**
     * 创建app实例
     * @param type app类型
     * @return
     */
    private BaseApplication getApplication(String type) {
        if (checkBackground(type)){
            return null;
        }
        BaseApplication application = WindowsFactory.getWindow(context, type);
        return application;
    }

    /**
     * 检查后台是否存活
     * 存活：动画提示
     * 不存活：重新打开应用
     * @return
     */
    private boolean checkBackground(String type){
        if (!backgroundApplication.containsKey(type)){
            return false;
        }
        BaseApplication application = backgroundApplication.get(type);
        if (application == null){
            return false;
        }
        if (application.isShowing()){
            View view = application.findViewById(R.id.application_layout);
            if (view == null) {
                Log.i(TAG, "application findViewById is null");
                return true;
            }
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.application_active_tip_anim);
            view.startAnimation(animation);
            ToastUtils.show(context,"应用已打开", true);
            return true;
        }
        backgroundApplication.remove(type);
        return false;
    }

    /**
     * 获取应用打开X坐标，居中显示
     *
     * width 应用的宽度
     * @return
     */
    private int getMiddleViewX(int width){
        return (maxWidthApplication - width) / 2;
    }

    /**
     * 获取可以设置的最大高度
     * @return
     */
    public int getMaxHeightApplication() {
        return maxHeightApplication;
    }

    public void setMaxHeightApplication(int maxHeightApplication) {
        this.maxHeightApplication = maxHeightApplication;
    }

    /**
     * 获取可以设置的最大宽度
     * @return
     */
    public int getMaxWidthApplication() {
        return maxWidthApplication;
    }

    public void setMaxWidthApplication(int maxWidthApplication) {
        this.maxWidthApplication = maxWidthApplication;
    }

    public int getTopHeight() {
        return topHeight;
    }

    public void setTopHeight(int topHeight) {
        this.topHeight = topHeight;
    }
}











