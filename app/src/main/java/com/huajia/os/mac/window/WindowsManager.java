package com.huajia.os.mac.window;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.huajia.os.mac.R;
import com.huajia.os.mac.application.BaseApplication;
import com.huajia.os.mac.application.camera.CameraApplication;
import com.huajia.os.mac.application.music.MusicApplication;
import com.huajia.os.mac.viewmodel.MainActivityViewModel;

import java.util.HashMap;

/**
 * @author: huajia
 * @date: 2023/9/13 22:50
 */

public class WindowsManager {

    private Context context;

    //后台
    private final HashMap<String, BaseApplication> backgroundApplication = new HashMap<>();

    private static volatile WindowsManager instance;

    private MainActivityViewModel mViewModel;

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
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(MainActivityViewModel.class);
    }

    public void initWindow(String type){
        Window window;
        BaseApplication application;
        application = getApplication(context,type);
        //应用存活，不需要打开
        if (application == null){
            return;
        }
        window = application.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams = window.getAttributes();
        //设置参考系
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        //起始坐标
        layoutParams.x = getMiddleViewX(mViewModel.getMaxWidthApplication() / 3);
        layoutParams.y = mViewModel.getTopHeight() + WindowsConstant.APP_MARGIN / 2;
        layoutParams.windowAnimations = R.style.AppOpenAndCloseAnim;
        window.setAttributes(layoutParams);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        //touch监听
        window.getDecorView().setOnTouchListener(new WindowsOnTouchListener(window,application,layoutParams,layoutParams.x, layoutParams.y));
        //设置app宽高
        window.setLayout(mViewModel.getMaxWidthApplication() / 2,mViewModel.getMaxHeightApplciation());
        application.show();
        backgroundApplication.put(type,application);
    }

    private BaseApplication getApplication(Context context, String type) {
        if (checkBackground(type)){
            return null;
        }
        BaseApplication application = null;
        switch (type){
            case WindowsConstant.CameraApplication:
                application = new CameraApplication(context);
                break;
            case WindowsConstant.MusicApplication:
                application = new MusicApplication(context);
                break;
        }
        return application;
    }

    /**
     * 检查后台是否存活
     * 存活：动画提示
     * 不存货：重新打开应用
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
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.application_active_tip_anim);
            view.startAnimation(animation);
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
        int maxWidth = mViewModel.getMaxWidthApplication();
        return (maxWidth - width) / 2;
    }

}











