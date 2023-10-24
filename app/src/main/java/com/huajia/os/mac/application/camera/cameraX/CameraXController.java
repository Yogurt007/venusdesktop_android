package com.huajia.os.mac.application.camera.cameraX;

import android.content.Context;
import android.util.Log;
import android.util.Size;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.huajia.os.mac.application.camera.utils.CacheUtils;
import com.huajia.os.mac.utils.PermissionHelper;
import com.huajia.os.mac.utils.ToastUtils;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class CameraXController {
    private static final String TAG = "CameraXController";

    private Context mContext;

    private PreviewView mPreview;

    private ProcessCameraProvider mCameraProvider;

    private ImageCapture mImageCapture;

    public CameraXController(Context context, PreviewView preview){
        mContext = context;
        mPreview = preview;
        //拍照参数
        mImageCapture = new ImageCapture.Builder()
                .setTargetResolution(new Size(1080,1920))
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .setJpegQuality(100)
                .build();
    }

    public void startCamera(LifecycleOwner lifecycleOwner){
        if (!PermissionHelper.hasCameraPermission(mContext)){
            ToastUtils.show(mContext,"没有相机权限");
        }
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(mContext);
        cameraProviderFuture.addListener(() -> {
            try {
                mCameraProvider = cameraProviderFuture.get();
                //创建预览用例
                Preview preview = new Preview.Builder().build();
                //将预览绑定
                preview.setSurfaceProvider(mPreview.getSurfaceProvider());
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
                mCameraProvider.unbindAll();
                mCameraProvider.bindToLifecycle(lifecycleOwner,cameraSelector,preview,mImageCapture);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }, ContextCompat.getMainExecutor(mContext));
    }

    public void takePhoto() {
        File cacheDirectory = CacheUtils.getCacheDirectory(mContext);
        File file = new File(cacheDirectory,System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
        mImageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(mContext), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                ToastUtils.show(mContext,"拍照成功");
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                ToastUtils.show(mContext,"拍照失败");
            }
        });
    }

}
