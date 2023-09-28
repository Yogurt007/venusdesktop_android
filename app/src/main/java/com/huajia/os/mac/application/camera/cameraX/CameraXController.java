package com.huajia.os.mac.application.camera.cameraX;

import android.content.Context;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class CameraXController {
    private Context mContext;

    private PreviewView mPreview;

    private ProcessCameraProvider mCameraProvider;

    public CameraXController(Context context, PreviewView preview){
        mContext = context;
        mPreview = preview;
    }

    public void startCamera(LifecycleOwner lifecycleOwner){
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
                mCameraProvider.bindToLifecycle(lifecycleOwner,cameraSelector,preview);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }, ContextCompat.getMainExecutor(mContext));
    }

}
