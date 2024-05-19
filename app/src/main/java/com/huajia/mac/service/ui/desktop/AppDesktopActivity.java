package com.huajia.mac.service.ui.desktop;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.huajia.mac.service.ui.desktop.view.AppView;
import com.huajia.os.mac.R;
import com.huajia.mac.base.BaseActivity;

public class AppDesktopActivity extends BaseActivity {
    private static final String TAG = "AppDesktopActivity";

    private AppView mAppView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_desktop);

        initView();

    }

    private void initView(){
        mAppView = findViewById(R.id.app_view);
    }

}
