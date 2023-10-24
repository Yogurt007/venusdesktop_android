package com.huajia.os.mac.application;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huajia.os.mac.R;

/**
 * @author: huajia
 * @date: 2023/9/13 22:57
 */

public class BaseApplication extends Dialog {
    //app的尺寸
    private Size mSize;

    public BaseApplication(@NonNull Context context) {
        super(context);
    }

    public BaseApplication(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseApplication(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_base);
    }

    public Size getmSize() {
        return mSize;
    }

    public void setmSize(Size mSize) {
        this.mSize = mSize;
    }
}
