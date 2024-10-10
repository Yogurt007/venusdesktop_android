package com.huajia.venusdesktop.service.application.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import com.orhanobut.logger.Logger;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.FileOutputOptions;
import androidx.camera.video.Recorder;
import androidx.camera.video.Recording;
import androidx.camera.video.VideoCapture;
import androidx.camera.video.VideoRecordEvent;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.huajia.venusdesktop.framework.eventbus.EventBusConstants;
import com.huajia.venusdesktop.framework.eventbus.MessageEvent;
import com.huajia.venusdesktop.utils.CacheUtils;
import com.huajia.venusdesktop.utils.TipSoundUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class CameraXController {
    private static final String TAG = "CameraXController";

    public static int TYPE_PHOTO = 0;

    public static int TYPE_VIDEO = 1;

    private Context mContext;

    private PreviewView mPreview;

    private ProcessCameraProvider mCameraProvider;

    private ImageCapture mImageCapture;

    private VideoCapture mVideoCapture;

    private Preview preview;

    private CameraSelector cameraSelector;

    private Recorder mRecorder;

    private Recording mRecording;

    private LifecycleOwner lifecycleOwner;

    private ICameraCallBack iCameraCallBack;

    @SuppressLint("RestrictedApi")
    public CameraXController(Context context, PreviewView preview, LifecycleOwner lifecycleOwner) {
        mContext = context;
        mPreview = preview;
        this.lifecycleOwner = lifecycleOwner;
        //拍照参数
        mImageCapture = new ImageCapture.Builder()
                .setTargetResolution(new Size(1080, 1920))
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .setJpegQuality(100)
                .build();
        mRecorder = new Recorder.Builder().build();
        mVideoCapture = VideoCapture.withOutput(mRecorder);
    }

    public void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(mContext);
        cameraProviderFuture.addListener(() -> {
            try {
                mCameraProvider = cameraProviderFuture.get();
                //创建预览用例
                preview = new Preview.Builder().build();
                //将预览绑定
                preview.setSurfaceProvider(mPreview.getSurfaceProvider());
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
                mCameraProvider.unbindAll();
                mCameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, mImageCapture);
                mCameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, mVideoCapture);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }, ContextCompat.getMainExecutor(mContext));
    }

    public void takePhoto() {
        TipSoundUtil.takePhoto(mContext);
        File cacheDirectory = CacheUtils.getAlbumDirectory(mContext);
        File file = new File(cacheDirectory, System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
        mImageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(mContext), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                EventBus.getDefault().post(new MessageEvent(EventBusConstants.TAKE_PHOTO_SUCCESS));
                Logger.i(TAG, "拍照成功");
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void record() {
        if (mRecording != null) {
            mRecording.stop();
            mRecording = null;
            Logger.i(TAG, "停止录制");
            iCameraCallBack.stopRecord();
            return;
        }
        Logger.i(TAG, "开始录制");
        File videoDirectory = CacheUtils.getAlbumDirectory(mContext);
        File file = new File(videoDirectory, System.currentTimeMillis() + ".mp4");
        FileOutputOptions fileOutputOptions = new FileOutputOptions.Builder(file).build();
        mRecording = mRecorder.prepareRecording(mContext, fileOutputOptions)
                .withAudioEnabled()
                .start(ContextCompat.getMainExecutor(mContext), videoListener);
    }

    private Consumer<VideoRecordEvent> videoListener = new Consumer<VideoRecordEvent>() {
        @Override
        public void accept(VideoRecordEvent videoRecordEvent) {
            if (videoRecordEvent instanceof VideoRecordEvent.Start) {
                iCameraCallBack.startRecord();
            } else if (videoRecordEvent instanceof VideoRecordEvent.Pause) {
                iCameraCallBack.stopRecord();
            }
        }
    };

    @SuppressLint("RestrictedApi")
    public void release() {
        mImageCapture.onUnbind();
        mCameraProvider.unbindAll();
    }

    public ICameraCallBack getCameraCallBack() {
        return iCameraCallBack;
    }

    public void setCameraCallBack(ICameraCallBack iCameraCallBack) {
        this.iCameraCallBack = iCameraCallBack;
    }

    public interface ICameraCallBack {
        void startRecord();

        void stopRecord();
    }

}
