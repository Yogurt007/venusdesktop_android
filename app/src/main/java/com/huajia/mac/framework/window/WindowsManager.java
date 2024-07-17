package com.huajia.mac.framework.window;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.huajia.mac.framework.router.TRouter;
import com.huajia.os.mac.R;
import com.huajia.mac.base.BaseApplication;
import com.huajia.mac.utils.SizeUtils;
import com.huajia.mac.utils.ToastUtils;

import java.util.HashMap;
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
    private final ConcurrentHashMap<String, BaseApplication> windowStack = new ConcurrentHashMap<>();

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

    /**
     * 打开窗口
     *
     * @param builder 参数
     * @param application 需要打开窗口的app
     */
    public void openWindow(TRouter.TRouterBuilder builder, BaseApplication application) {
        if (checkAlive(builder.getRouterPath())) {
            Log.i(TAG, "window is open, not continue open");
            return;
        }
        Window window = application.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (builder.getCoordinate() != null) {
            // 需要指定坐标的弹窗
            layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            layoutParams.x = builder.getCoordinate().x;
            layoutParams.y = WindowsManager.getInstance().getTopHeight();
            layoutParams.windowAnimations = R.style.DialogOpenAndCloseAnim;
            // 背景透明
            layoutParams.dimAmount = 0f;
        } else if (!builder.isAllowMove() && builder.getCoordinate() == null) {
            // 提示弹窗： 1、居中 2、不需要移动
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.windowAnimations = R.style.AppOpenAndCloseAnim;
        } else {
            layoutParams.gravity = Gravity.CENTER | Gravity.TOP;
            layoutParams.y = topHeight + WindowsConstants.APP_MARGIN / 2;
            // 开关过度动画
            layoutParams.windowAnimations = R.style.AppOpenAndCloseAnim;
            // 给window设置点击事件
            window.getDecorView().setOnTouchListener(new WindowsOnTouchListener(window,application,layoutParams,layoutParams.x, layoutParams.y));
        }
        window.setAttributes(layoutParams);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(application.getSize().getWidth(), application.getSize().getHeight());
        application.show();
        windowStack.put(builder.getRouterPath(), application);
    }

    /**
     * 检查app是否存货
     *
     * @param routerPath 路由路径
     * @return true: 存活； false: 不存活
     */
    private boolean checkAlive(String routerPath){
        if (!windowStack.containsKey(routerPath)){
            return false;
        }
        BaseApplication application = windowStack.get(routerPath);
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
        windowStack.remove(routerPath);
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











