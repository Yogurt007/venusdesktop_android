package com.huajia.mac.service.application.camera;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.tabs.TabLayout;
import com.huajia.annotation.Route;
import com.huajia.mac.framework.router.TRouterPath;
import com.huajia.mac.utils.PermissionHelper;
import com.huajia.mac.utils.TimeUtils;
import com.huajia.os.mac.R;
import com.huajia.mac.base.BaseApplication;

/**
 * @author: huajia
 * @date: 2023/9/12 22:11
 */

@Route(path = TRouterPath.CAMERA, heightPercent = 1)
public class CameraApplication extends BaseApplication {
    private static final String TAG = "CameraApplication";

    //相机的b比例 高 / 宽 4:3
    public static final double CAMERA_RATIO = 1.3334;

    private Context mContext;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;

    private CameraXController mCameraXController;

    private PreviewView mPreviewView;

    private ImageView mTakePhotoButton;

    private TabLayout mTabLayout;

    private TextView recordTimeView;

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
        setContentView(R.layout.application_camera);
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
        mPreviewView = findViewById(R.id.preview);
        mTakePhotoButton = findViewById(R.id.take_photo_button);
        mTakePhotoButton.setOnClickListener(view -> {
            if (type == CameraXController.TYPE_PHOTO) {
                mCameraXController.takePhoto();
            } else if (type == CameraXController.TYPE_VIDEO) {
                mCameraXController.record();
            }
        });
        mTabLayout = findViewById(R.id.camera_tab_layout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        recordTimeView = findViewById(R.id.record_time_view);
    }

    private void initCamera(){
        mPreviewView.setOutlineProvider(new RoundedCornerOutlineProvider(30));
        mPreviewView.setClipToOutline(true);
        mPreviewView.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);
        mCameraXController = new CameraXController(getContext(), mPreviewView, (LifecycleOwner) mContext);
        mCameraXController.startCamera();
        mCameraXController.setCameraCallBack(new CameraXController.ICameraCallBack() {
            @Override
            public void startRecord() {
                Log.i(TAG, "startRecord");
                recordTimeView.setVisibility(View.VISIBLE);
                switchRecordTimer(true);
            }

            @Override
            public void stopRecord() {
                Log.i(TAG, "stopRecord");
                recordTimeView.setVisibility(View.GONE);
                switchRecordTimer(false);
            }
        });
    }

    private void switchRecordTimer(boolean value) {
        isRecording = value;
        if (!value) {
            Log.i(TAG, "暂停时间线程");
            return;
        }
        recordTimerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 0;
                while (isRecording) {
                    time++;
                    int finalTime = time;
                    recordTimeView.post(new Runnable() {
                        @Override
                        public void run() {
                            recordTimeView.setText(TimeUtils.secToTime(finalTime));
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
