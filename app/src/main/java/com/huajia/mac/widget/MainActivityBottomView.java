package com.huajia.mac.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.huajia.mac.framework.window.WindowsWant;
import com.huajia.mac.service.dialog.PermissionDialog;
import com.huajia.os.mac.R;
import com.huajia.mac.service.ui.desktop.AppDesktopActivity;
import com.huajia.mac.framework.window.WindowsConstants;
import com.huajia.mac.framework.window.WindowsManager;
import com.huajia.mac.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/2
 * @Description:
 */
public class MainActivityBottomView extends FrameLayout {
    private static final String TAG = "MainActivityBottomView";

    private Context context;

    private View rootView;

    private View container;

    private ImageView mMusicApp;

    private ImageView mCameraApp;

    private ImageView mAppDesktop;

    private ImageView mAlbumApp;

    private ImageView mDrawApp;

    private ImageView mTangApp;

    private ImageView mGuitarApp;
    public MainActivityBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.activity_main_bottom, this);
        container = rootView.findViewById(R.id.bottom_container);
        mMusicApp = rootView.findViewById(R.id.music_button);
        mAppDesktop = rootView.findViewById(R.id.app_desktop_button);
        mAlbumApp = rootView.findViewById(R.id.album_button);
        mDrawApp = rootView.findViewById(R.id.draw_button);
        mCameraApp = rootView.findViewById(R.id.camera_button);
        mGuitarApp = rootView.findViewById(R.id.guitar_button);
        mTangApp = rootView.findViewById(R.id.tang_poem_button);

        mCameraApp.setOnClickListener((view) -> {
            getCenterCoordinate(mCameraApp);
            WindowsManager.getInstance().initWindow(new WindowsWant(WindowsConstants.CameraApplication));
        });
        mMusicApp.setOnClickListener(view -> {
            getCenterCoordinate(mMusicApp);
            WindowsManager.getInstance().initWindow(new WindowsWant(WindowsConstants.MusicApplication));
        });
        mAppDesktop.setOnClickListener(view -> {
            Intent intent = new Intent(context, AppDesktopActivity.class);
            context.startActivity(intent);
        });
        mAlbumApp.setOnClickListener(view -> {
            WindowsManager.getInstance().initWindow(new WindowsWant(WindowsConstants.AlbumApplication));
        });
        mDrawApp.setOnClickListener(view -> {
            WindowsManager.getInstance().initWindow(new WindowsWant(WindowsConstants.DrawApplication));
        });
        mGuitarApp.setOnClickListener(view -> {
            XXPermissions.with(getContext())
                    .permission(Permission.RECORD_AUDIO)
                    .request(new OnPermissionCallback() {
                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            WindowsManager.getInstance().initWindow(new WindowsWant(WindowsConstants.GuitarApplication));
                        }

                        @Override
                        public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                            ToastUtils.show(getContext(), "请打开麦克风权限");
                        }
                    });
        });
        mTangApp.setOnClickListener(view -> {
//            WindowsManager.getInstance().initWindow(WindowsConstants.TangPoemApplication);
            HashMap<Object, Object> params = new HashMap<>();
            params.put(PermissionDialog.PARAM_REASON, "使用相机需要申请 “ 相机权限 ”");
            WindowsManager.getInstance().initWindow(new WindowsWant(WindowsConstants.PermissionDialog, false, null, params));
        });
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

    /**
     * 获得图标的中心坐标
     */
    private void getCenterCoordinate(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int centerX = location[0] + view.getWidth() / 2;
        int centerY = location[1] + view.getHeight() / 2;
        Log.i(TAG, "getCenterCoordinate: centerX = " + centerX + ", centerY = " + centerY);
    }
}
