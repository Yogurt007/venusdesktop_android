package com.huajia.os.mac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.huajia.os.mac.window.WindowsConstant;
import com.huajia.os.mac.window.WindowsManager;

public class MainActivity extends AppCompatActivity {

    private ImageButton cameraButton;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        initUI();

        initView();

    }

    private void initUI() {
        //沉浸式通知栏
        int uiOption = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOption);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //使用留海区域
        WindowManager.LayoutParams lp= getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;
        getWindow().setAttributes(lp);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void initView(){
        cameraButton = findViewById(R.id.camera_button);

        cameraButton.setOnClickListener(view -> {
            WindowsManager.getInstance().initWindow(this, WindowsConstant.CameraApplication);
        });
    }
}