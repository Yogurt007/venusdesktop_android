package com.huajia.os.mac.application.camera;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huajia.os.mac.R;
import com.huajia.os.mac.application.BaseApplication;

/**
 * @author: huajia
 * @date: 2023/9/12 22:11
 */

public class CameraApplication extends BaseApplication {
    public CameraApplication(@NonNull Context context) {
        super(context);
        setContentView(R.layout.application_camera);
    }

    public CameraApplication(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CameraApplication(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
