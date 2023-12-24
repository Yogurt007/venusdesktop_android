package com.huajia.os.mac.application.album;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huajia.os.mac.R;
import com.huajia.os.mac.application.BaseApplication;

public class AlbumApplication extends BaseApplication {
    public AlbumApplication(@NonNull Context context) {
        super(context);
    }

    public AlbumApplication(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AlbumApplication(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_album);
    }
}
