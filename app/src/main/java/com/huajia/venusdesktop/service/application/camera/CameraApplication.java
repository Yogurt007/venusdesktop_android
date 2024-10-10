package com.huajia.venusdesktop.service.application.camera;

import android.content.Context;
import android.os.Bundle;
import com.orhanobut.logger.Logger;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.tabs.TabLayout;
import com.huajia.annotation.Route;
import com.huajia.venusdesktop.databinding.ApplicationCameraBinding;
import com.huajia.venusdesktop.framework.router.TRouterPath;
import com.huajia.venusdesktop.utils.PermissionHelper;
import com.huajia.venusdesktop.utils.TimeUtils;
import com.huajia.venusdesktop.R;
import com.huajia.venusdesktop.base.BaseApplication;

/**
 * @author: huajia
 * @date: 2023/9/12 22:11
 */

@Route(path = TRouterPath.CAMERA, heightPercent = 1)
public class CameraApplication extends BaseApplication {
    private static final String TAG = "CameraApplication";

    private ApplicationCameraBinding binding;

    //相机的b比例 高 / 宽 4:3
    public static final double CAMERA_RATIO = 1.3334;

    private Context mContext;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;

    private CameraXController mCameraXController;

    private Thread recordTimerThread;

    private boolean isRecording;

    /**
     * 功能类型 0：拍照(默认) 1：录像
     */
    private int type;

    public CameraApplication(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PermissionHelper.checkCameraPermission(getContext());
        super.onCreate(savedInstanceState);
        binding = ApplicationCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCameraXController.release();
    }

    private void initView() {
        findViewById(R.id.close_button).setOnClickListener( view -> {
            dismiss();
        });
        binding.takePhotoButton.setOnClickListener(view -> {
            if (type == CameraXController.TYPE_PHOTO) {
                mCameraXController.takePhoto();
            } else if (type == CameraXController.TYPE_VIDEO) {
                mCameraXController.record();
            }
        });
        binding.cameraTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initCamera(){
        binding.preview.setOutlineProvider(new RoundedCornerOutlineProvider(30));
        binding.preview.setClipToOutline(true);
        binding.preview.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);
        mCameraXController = new CameraXController(getContext(), binding.preview, (LifecycleOwner) mContext);
        mCameraXController.startCamera();
        mCameraXController.setCameraCallBack(new CameraXController.ICameraCallBack() {
            @Override
            public void startRecord() {
                Logger.i(TAG, "startRecord");
                binding.recordTimeView.setVisibility(View.VISIBLE);
                switchRecordTimer(true);
            }

            @Override
            public void stopRecord() {
                Logger.i(TAG, "stopRecord");
                binding.recordTimeView.setVisibility(View.GONE);
                switchRecordTimer(false);
            }
        });
    }

    private void switchRecordTimer(boolean value) {
        isRecording = value;
        if (!value) {
            Logger.i(TAG, "暂停时间线程");
            return;
        }
        recordTimerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 0;
                while (isRecording) {
                    time++;
                    int finalTime = time;
                    binding.recordTimeView.post(new Runnable() {
                        @Override
                        public void run() {
                            binding.recordTimeView.setText(TimeUtils.secToTime(finalTime));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        recordTimerThread.start();
    }
}
