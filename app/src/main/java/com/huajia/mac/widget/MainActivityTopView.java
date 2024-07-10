package com.huajia.mac.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.huajia.mac.framework.window.WindowsRouter;
import com.huajia.mac.framework.window.WindowsWant;
import com.huajia.mac.utils.ToastUtils;
import com.huajia.os.mac.R;
import com.huajia.mac.service.application.music.MusicService;
import com.huajia.mac.service.application.music.MusicServiceConstants;
import com.huajia.mac.utils.TextUtils;
import com.huajia.mac.utils.UIHelper;
import com.huajia.mac.framework.window.WindowsConstants;
import com.huajia.mac.framework.window.WindowsManager;
import com.huajia.mac.view.MarqueeTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/2
 * @Description:
 */
public class MainActivityTopView extends FrameLayout {

    private Context context;

    private View rootView;

    private TextView tvTopTime;

    private ImageView wifiButton;

    private ImageView volumeButton;

    private ImageView bluetoothButton;

    private LinearLayout topMusicLayout;

    private MarqueeTextView songNameView;

    private ImageView topMusicStop;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Intent.ACTION_TIME_TICK:
                    updateSystemTime();
                    break;
                case MusicServiceConstants.MUSIC_SERVICE:
                    String songName = intent.getStringExtra(MusicServiceConstants.SONG_NAME);
                    if (!TextUtils.isEmpty(songName)) {
                        topMusicLayout.setVisibility(VISIBLE);
                        songNameView.setText(songName);
                    }
                    boolean serviceStop = intent.getBooleanExtra(MusicServiceConstants.SERVICE_STOP, false);
                    if (serviceStop) {
                        topMusicLayout.setVisibility(GONE);
                    }
                    break;
            }
        }
    };

    public MainActivityTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();

        initData();
    }

    private void initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.activity_main_top, this);
        tvTopTime = rootView.findViewById(R.id.top_time);
        topMusicLayout = rootView.findViewById(R.id.top_music);
        songNameView = rootView.findViewById(R.id.top_music_song_name);
        wifiButton = findViewById(R.id.wifi_button);
        wifiButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Point coordinate = UIHelper.getViewLBottomCoordinate(wifiButton);
                WindowsManager.getInstance().openWindow(new WindowsWant(WindowsRouter.WifiDialog, false, coordinate));
            }
        });
        volumeButton = findViewById(R.id.volume_button);
        volumeButton.setOnClickListener(view -> {
            Point coordinate = UIHelper.getViewLBottomCoordinate(volumeButton);
            WindowsManager.getInstance().openWindow(new WindowsWant(WindowsRouter.VolumeDialog, false, coordinate));
        });

        bluetoothButton = findViewById(R.id.bluetooth_button);
        bluetoothButton.setOnClickListener(view -> {
            XXPermissions.with(context)
                    .permission(Permission.ACCESS_FINE_LOCATION, Permission.BLUETOOTH_SCAN, Permission.BLUETOOTH_CONNECT)
                    .request(new OnPermissionCallback() {
                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            Point coordinate = UIHelper.getViewLBottomCoordinate(bluetoothButton);
                            WindowsManager.getInstance().openWindow(new WindowsWant(WindowsRouter.BluetoothDialog, false, coordinate));
                        }

                        @Override
                        public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                            ToastUtils.show(context, "请打开蓝牙权限");
                        }
                    });
        });
        topMusicStop = rootView.findViewById(R.id.top_music_stop);
        topMusicStop.setOnClickListener( view -> {
            Intent intent = new Intent(getContext(), MusicService.class);
            getContext().stopService(intent);
        });
    }
    
    private void initData() {
        // 时间更新广播
        updateSystemTime();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(MusicServiceConstants.MUSIC_SERVICE);
        context.registerReceiver(receiver, intentFilter);
    }

    private void updateSystemTime(){
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 EEEE HH:mm", Locale.getDefault());
        String nowTime = sdf.format(currentDate);
        tvTopTime.setText(nowTime);
    }

}
