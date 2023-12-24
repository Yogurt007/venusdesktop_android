package com.huajia.os.mac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.huajia.os.mac.activity.appdesktop.AppDesktopActivity;
import com.huajia.os.mac.utils.UIHelper;
import com.huajia.os.mac.window.WindowsConstants;
import com.huajia.os.mac.window.WindowsManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Context mContext;

    private View desktop;

    private View topContainer;

    private View bottomContainer;

    private ImageView mMusicApp;

    private ImageView mCameraApp;

    private ImageView mAppDesktop;

    private ImageView mAlbumApp;

    private ImageView mDrawApp;

    private TextView tvTopTime;

    /**
     * 线程池，装载多线程任务
     */
    private ThreadPoolExecutor mThreadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UIHelper.initActivityUI(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initOpenApp();

        initData();

        initTimeTask();
    }

    private void initView(){

        mCameraApp = findViewById(R.id.camera_button);
        desktop = findViewById(R.id.main_container);
        desktop.setSystemUiVisibility(View.VISIBLE);
        topContainer = findViewById(R.id.top_container);
        bottomContainer = findViewById(R.id.bottom_container);
        mMusicApp = findViewById(R.id.music_button);
        mAppDesktop = findViewById(R.id.app_desktop_button);
        mAlbumApp = findViewById(R.id.album_button);
        mDrawApp = findViewById(R.id.draw_button);
        tvTopTime = findViewById(R.id.top_time);

    }

    private void initOpenApp(){
        mCameraApp.setOnClickListener(view -> {
            WindowsManager.getInstance().initWindow(WindowsConstants.CameraApplication);
        });
        mMusicApp.setOnClickListener(view -> {
            WindowsManager.getInstance().initWindow(WindowsConstants.MusicApplication);
        });
        mAppDesktop.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AppDesktopActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        });
        mAlbumApp.setOnClickListener(view -> {
            WindowsManager.getInstance().initWindow(WindowsConstants.AlbumApplication);
        });
        mDrawApp.setOnClickListener(view -> {
            WindowsManager.getInstance().initWindow(WindowsConstants.DrawApplication);
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mThreadPool = new ThreadPoolExecutor(
                5, // 核心线程数
                10, // 最大线程数
                60L,  //线程超时时间
                TimeUnit.SECONDS,  // 时间单位
                new ArrayBlockingQueue<>(1000), //任务队列
                Executors.defaultThreadFactory(), // 线程工厂
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );
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
                WindowsManager.getInstance().setMaxHeightApplciation(desktop.getHeight() - WindowsConstants.APP_MARGIN);
                WindowsManager.getInstance().setMaxWidthApplication(desktop.getWidth() - WindowsConstants.APP_MARGIN);
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
        mThreadPool.submit(() -> {
            String lastTime = "";
            while (true){
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 EEEE HH:mm", Locale.getDefault());
                String nowTime = sdf.format(currentDate);
                if (!TextUtils.equals(lastTime,nowTime)){
                    lastTime = nowTime;
                    tvTopTime.post(() -> {
                        tvTopTime.setText(nowTime);
                    });
                }
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    Log.i(TAG,"time task is error:" + e.getMessage());
                }
            }
        });
    }
}