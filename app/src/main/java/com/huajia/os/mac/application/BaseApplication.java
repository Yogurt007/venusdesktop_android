package com.huajia.os.mac.application;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: huajia
 * @date: 2023/9/13 22:57
 */

public class BaseApplication extends Dialog {
    public BaseApplication(@NonNull Context context) {
        super(context);
    }

    public BaseApplication(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseApplication(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
