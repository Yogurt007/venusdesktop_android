package com.huajia.mac.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Size;

import androidx.annotation.NonNull;

/**
 * @author: huajia
 * @date: 2023/9/13 22:57
 */

public abstract class BaseApplication extends Dialog {
    //app的尺寸
    private Size mSize;

    public BaseApplication(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void init() {
    }

    public Size getSize() {
        return mSize;
    }

    public void setSize(Size mSize) {
        this.mSize = mSize;
    }
}
