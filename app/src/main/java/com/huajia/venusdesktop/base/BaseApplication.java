package com.huajia.venusdesktop.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Size;

import androidx.annotation.NonNull;

import com.huajia.venusdesktop.framework.router.TRouterProtocol;

/**
 * @author: huajia
 * @date: 2023/9/13 22:57
 */

public abstract class BaseApplication extends Dialog {
    //app的尺寸
    protected Size mSize;

    // 数据传递
    protected TRouterProtocol protocol;

    public BaseApplication(@NonNull Context context) {
        super(context);
    }

    public BaseApplication(Context context, TRouterProtocol protocol) {
        super(context);
        this.protocol = protocol;
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
