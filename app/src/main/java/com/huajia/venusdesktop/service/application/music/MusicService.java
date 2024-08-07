package com.huajia.venusdesktop.service.application.music;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import com.huajia.venusdesktop.service.application.music.bean.MusicBean;
import com.huajia.venusdesktop.utils.TextUtils;
import com.huajia.venusdesktop.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/25
 * @Description:
 */
public class MusicService extends Service {
    private static final String TAG = "MusicService";

    private MediaPlayer mediaPlayer;

    private List<MusicBean> musics;

    /**
     * 当前歌曲序号
     */
    private int currentIndex;

    private RemoteViews remoteViews;

    private Notification.Builder notificationBuilder;

    private NotificationManager notificationManager;

    private PendingIntent prePendingIntent;

    private PendingIntent nextPendingIntent;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case MusicService.MIDI_SERVICE:
                    if (intent.getBooleanExtra(MusicServiceConstants.NEXT_SONG, false)) {
                        nextSong();
                    }
                    if (intent.getBooleanExtra(MusicServiceConstants.PREVIOUS_SONG, false)) {
                        preSong();
                    }
                    switchSong(intent.getStringExtra(MusicServiceConstants.SWITCH_SONG));
                    break;
                case Intent.ACTION_SCREEN_OFF:
                    if (!mediaPlayer.isPlaying()) {
                        Log.i(TAG, "music is not playing， not open lock activity");
                        return;
                    }
                    Intent lockIntent = new Intent(MusicService.this, MusicLockActivity.class);
                    lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(lockIntent);
                    break;
                default:
                    break;
            }
        }
    };

    private Timer progressTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        musics = MusicList.getMusicList(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicServiceConstants.MUSIC_SERVICE);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playSong();
        startForeground(MusicServiceConstants.MUSIC_SERVICE_NOTIFICATION_ID, getMusicNotification());
        progressTimer = new Timer();
        TimerTask progressTask = new TimerTask() {
            @Override
            public void run() {
                if (!mediaPlayer.isPlaying()) {
                    return;
                }
                Intent intent = new Intent();
                intent.setAction(MusicServiceConstants.MUSIC_SERVICE);
                intent.putExtra(MusicServiceConstants.PROGRESS_SONG, mediaPlayer.getCurrentPosition());
                intent.putExtra(MusicServiceConstants.DURATION_SONG, mediaPlayer.getDuration());
                sendBroadcast(intent);
            }
        };
        progressTimer.schedule(progressTask, 5, 1000);
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
        unregisterReceiver(receiver);
        progressTimer.cancel();
        stopForeground(true);
        mediaPlayer.release();
        mediaPlayer = null;

        // 发送关闭广播
        Intent intent = new Intent();
        intent.setAction(MusicServiceConstants.MUSIC_SERVICE);
        intent.putExtra(MusicServiceConstants.SERVICE_STOP, true);
        sendBroadcast(intent);
    }

    /**
     * 下一首歌
     */
    private void nextSong() {
        if (currentIndex == musics.size() - 1) {
            currentIndex = 0;
        } else {
            currentIndex++;
        }
        updateRemoteViews();
        playSong();
    }

    /**
     * 上一首歌
     */
    private void preSong() {
        if (currentIndex == 0) {
            currentIndex = musics.size() - 1;
        } else {
            currentIndex--;
        }
        updateRemoteViews();
        playSong();
    }

    private void playSong() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        mediaPlayer = MediaPlayer.create(MusicService.this, musics.get(currentIndex).getmMusicUri());
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                nextSong();
            }
        });
        Intent intent = new Intent();
        intent.setAction(MusicServiceConstants.MUSIC_SERVICE);
        intent.putExtra(MusicServiceConstants.SONG_NAME, musics.get(currentIndex).getmSongName());
        sendBroadcast(intent);
        updateRemoteViews();
    }

    /**
     * 开关歌曲
     *
     * @param value 开： on | 关：off
     */
    private void switchSong(String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        Log.i(TAG, "switchSong： " + value);
        if (MusicServiceConstants.START_SONG.equals(value)) {
            mediaPlayer.start();
        } else {
            mediaPlayer.pause();
        }
        updateRemoteViews();
    }

    private Notification getMusicNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(MusicServiceConstants.MUSIC_SERVICE_CHANNEL_ID,
                MusicServiceConstants.MUSIC_SERVICE_CHANNEL_NAME, NotificationManager.IMPORTANCE_MIN);
        notificationManager.createNotificationChannel(notificationChannel);
        setRemoteViews();
        notificationBuilder = new Notification.Builder(this, MusicServiceConstants.MUSIC_SERVICE_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_panda_logo)
                .setCustomContentView(remoteViews)
                .setAutoCancel(false);
        return notificationBuilder.build();
    }

    private void updateRemoteViews() {
        if (notificationManager == null || notificationBuilder == null) {
            return;
        }
        setRemoteViews();
        notificationBuilder.setCustomContentView(remoteViews);
        notificationManager.notify(MusicServiceConstants.MUSIC_SERVICE_NOTIFICATION_ID, notificationBuilder.build());
    }

    private void setRemoteViews() {
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_music_service);
        remoteViews.setTextViewText(R.id.music_singer, musics.get(currentIndex).getmSinger());
        remoteViews.setTextViewText(R.id.music_song, musics.get(currentIndex).getmSongName());
        remoteViews.setImageViewResource(R.id.music_image, R.drawable.music_image_leijun);
        remoteViews.setImageViewResource(R.id.music_switch, mediaPlayer.isPlaying() ? R.drawable.icon_music_song_select : R.drawable.icon_start_music_select);

        // 通知栏上一首点击事件
        if (prePendingIntent == null) {
            Intent preIntent = new Intent();
            preIntent.setAction(MusicServiceConstants.MUSIC_SERVICE);
            preIntent.putExtra(MusicServiceConstants.PREVIOUS_SONG, true);
            prePendingIntent = PendingIntent.getBroadcast(this, 1, preIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        remoteViews.setOnClickPendingIntent(R.id.music_previous, prePendingIntent);

        // 通知栏下一首点击事件
        if (nextPendingIntent == null) {
            Intent nextIntent = new Intent();
            nextIntent.setAction(MusicServiceConstants.MUSIC_SERVICE);
            nextIntent.putExtra(MusicServiceConstants.NEXT_SONG, true);
            nextPendingIntent = PendingIntent.getBroadcast(this, 2, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        remoteViews.setOnClickPendingIntent(R.id.music_next, nextPendingIntent);

        // 通知栏暂停与停止时间
        Intent switchIntent = new Intent();
        switchIntent.setAction(MusicServiceConstants.MUSIC_SERVICE);
        switchIntent.putExtra(MusicServiceConstants.SWITCH_SONG, mediaPlayer.isPlaying() ? MusicServiceConstants.STOP_SONG : MusicServiceConstants.START_SONG);
        PendingIntent switchPendingIntent = PendingIntent.getBroadcast(this, 3, switchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.music_switch, switchPendingIntent);

    }
}
