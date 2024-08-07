package com.huajia.venusdesktop.framework.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.Nullable;

import com.huajia.venusdesktop.R;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/24
 * @Description: 保活服务
 */
public class KeepLiveService extends Service {
    private static final String TAG = "KeepLiveService";

    private static final int KEEP_LIVE_NOTIFICATION_ID = 1001;

    private static final String KEEP_LIVE_NOTIFICATION_CHANNEL_ID = "1002";

    private static final String KEEP_LIVE_NOTIFICATION_CHANNEL_NAME = "keep_live_service";

    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate() {
        super.onCreate();
        getWakeLock();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(KEEP_LIVE_NOTIFICATION_ID, getNotification());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseWakeLock();
        stopForeground(true);
    }

    @SuppressLint("InvalidWakeLockTag")
    private void getWakeLock() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, TAG);
        if (!wakeLock.isHeld()) {
            wakeLock.acquire();
        }
    }

    private void releaseWakeLock() {
        wakeLock.release();
    }

    private Notification getNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = new NotificationChannel(KEEP_LIVE_NOTIFICATION_CHANNEL_ID,
                KEEP_LIVE_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_MIN);
        notificationManager.createNotificationChannel(notificationChannel);
        Notification notification = new Notification.Builder(this, KEEP_LIVE_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.icon_panda_logo)
                .setContentTitle("panda")
                .setContentText("panda正在运行中")
                .build();
        return notification;
    }
}
