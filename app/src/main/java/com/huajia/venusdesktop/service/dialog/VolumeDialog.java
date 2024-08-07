package com.huajia.venusdesktop.service.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.huajia.annotation.Route;
import com.huajia.venusdesktop.framework.router.TRouterPath;
import com.huajia.venusdesktop.R;
import com.huajia.venusdesktop.base.BaseApplication;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/8
 * @Description:
 */
@Route(path = TRouterPath.DIALOG_VOLUME, widthPercent = 0.33f, heightPercent = 0.33f)
public class VolumeDialog extends BaseApplication {
    private static final String ACTION_VOLUME = "android.media.VOLUME_CHANGED_ACTION";

    private Context context;

    private AudioManager audioManager;

    private SeekBar seekBar;

    private int maxVolume;

    /**
     * 是否来自广播的变化
     */
    private boolean isFromBroadcast;

    public VolumeDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_volume);
        this.context = context;
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ACTION_VOLUME);
        context.registerReceiver(volumeReceiver, intentFilter);
    }

    private void initView() {
        seekBar = findViewById(R.id.seek_bar);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        updateVolume();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (isFromBroadcast) {
                    isFromBroadcast = false;
                    return;
                }
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (progress / 100.0f * maxVolume), 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        context.unregisterReceiver(volumeReceiver);
    }

    private void updateVolume() {
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar.setProgress((int) (currentVolume * 1.0f / maxVolume * 100));
    }

    private BroadcastReceiver volumeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_VOLUME)) {
                isFromBroadcast = true;
                updateVolume();
            }
        }
    };


}
