package com.huajia.os.mac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.huajia.os.mac.activity.appdesktop.AppDesktopActivity;
import com.huajia.os.mac.utils.UIHelper;
import com.huajia.os.mac.window.WindowsConstant;
import com.huajia.os.mac.window.WindowsManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    private View desktop;

    private View topContainer;

    private View bottomContainer;

    private ImageView mMusicApp;

    private ImageView mCameraApp;

    private ImageView mAppDesktop;

    private TextView tvTopTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UIHelper.initActivityUI(this);

        initView();

        initData();

        initTimeTask();
    }

    private void initView(){
        mCameraApp = findViewById(R.id.camera_button);
        desktop = findViewById(R.id.main_container);
        topContainer = findViewById(R.id.top_container);
        bottomContainer = findViewById(R.id.bottom_container);
        mMusicApp = findViewById(R.id.music_button);
        mAppDesktop = findViewById(R.id.app_desktop_button);
        mCameraApp.setOnClickListener(view -> {
            WindowsManager.getInstance().initWindow(WindowsConstant.CameraApplication);
        });
        mMusicApp.setOnClickListener(view -> {
            WindowsManager.getInstance().initWindow(WindowsConstant.MusicApplication);
        });
        mAppDesktop.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AppDesktopActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = this;
        WindowsManager.getInstance().init(mContext);
        getAppMaxLayout();
    }

    /**
     * 获取app最大宽高
     */
    private void getAppMaxLayout(){
        desktop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                desktop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                WindowsManager.getInstance().setMaxHeightApplciation(desktop.getHeight() - WindowsConstant.APP_MARGIN);
                WindowsManager.getInstance().setMaxWidthApplication(desktop.getWidth() - WindowsConstant.APP_MARGIN);
            }
        });
        topContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                topContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                WindowsManager.getInstance().setMaxHeightApplciation(WindowsManager.getInstance().getMaxHeightApplciation() - topContainer.getHeight());
                WindowsManager.getInstance().setTopHeight(topContainer.getHeight());
            }
        });
        bottomContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bottomContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                WindowsManager.getInstance().setMaxHeightApplciation(WindowsManager.getInstance().getMaxHeightApplciation() - bottomContainer.getHeight());
            }
        });
    }

    /**
     * 初始化桌面事件任务
     */
    private void initTimeTask(){
        tvTopTime = findViewById(R.id.top_time);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 EEEE HH:mm", Locale.getDefault());
                String nowTime = sdf.format(currentDate);
                tvTopTime.post(new Runnable() {
                    @Override
                    public void run() {
                        tvTopTime.setText(nowTime);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,0,6000);
    }
}