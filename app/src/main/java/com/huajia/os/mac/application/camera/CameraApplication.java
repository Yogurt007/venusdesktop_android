package com.huajia.os.mac.application.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.lifecycle.LifecycleOwner;

import com.huajia.os.mac.R;
import com.huajia.os.mac.application.BaseApplication;
import com.huajia.os.mac.application.camera.cameraX.CameraXController;
import com.huajia.os.mac.application.camera.cameraX.RoundedCornerOutlineProvider;
import com.huajia.os.mac.utils.PermissionHelper;
import com.huajia.os.mac.utils.ToastUtils;

/**
 * @author: huajia
 * @date: 2023/9/12 22:11
 */

public class CameraApplication extends BaseApplication {
    private static final String TAG = "CameraApplication";

    //相机的b比例 高 / 宽 4:3
    public static final double CAMERA_RATIO = 1.3334;

    private Context mContext;

    private ImageView ivClose;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;

    private CameraXController mCameraXController;

    private PreviewView mPreviewView;

    private ImageView mTakephotoButton;

    public CameraApplication(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public CameraApplication(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CameraApplication(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_camera);
        initView();
        initCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initView() {
        ivClose = findViewById(R.id.close_button);
        mPreviewView = findViewById(R.id.preview);
        mTakephotoButton = findViewById(R.id.take_photo_button);
        ivClose.setOnClickListener(view -> {
            dismiss();
        });
        mTakephotoButton.setOnClickListener(view -> {
            Log.i(TAG,"点击拍照");
            mCameraXController.takePhoto();
        });
    }

    private void initCamera(){
        mPreviewView.setOutlineProvider(new RoundedCornerOutlineProvider(30));
        mPreviewView.setClipToOutline(true);
        mPreviewView.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);
        mCameraXController = new CameraXController(getContext(),mPreviewView);
        mCameraXController.startCamera((LifecycleOwner) mContext);
    }

    private void checkPermission(){
        if (!PermissionHelper.hasCameraPermission(getContext())){
            try {
                Activity activity = (Activity) mContext;
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},101);
            }catch (Exception e){
                Log.i(TAG,e.getMessage());
            }
        }
    }

}
