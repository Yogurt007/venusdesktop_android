package com.huajia.mac.service.application.music;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.annotation.Route;
import com.huajia.mac.framework.router.TRouterPath;
import com.huajia.mac.service.application.music.adapter.MusicAdapter;
import com.huajia.mac.service.application.music.bean.MusicBean;
import com.huajia.mac.service.application.music.view.CircularProgressBar;
import com.huajia.os.mac.R;
import com.huajia.mac.base.BaseApplication;

import java.util.List;

@Route(path = TRouterPath.MUSIC, heightPercent = 1)
public class MusicApplication extends BaseApplication {
    private static final String TAG = "MusicApplication";

    private RecyclerView mRecyclerView;

    private MusicAdapter mAdapter;

    //旋转图片
    private ImageView mAnimImageView;

    private CircularProgressBar mProgressBar;

    private ImageView mPreviousButton;

    private ImageView mStopButton;

    private ImageView mNextButton;

    private ImageView mStartButton;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MusicServiceConstants.MUSIC_SERVICE.equals(action)) {
                int duration = intent.getIntExtra(MusicServiceConstants.DURATION_SONG, 0);
                int progress = intent.getIntExtra(MusicServiceConstants.PROGRESS_SONG, 0);
                mProgressBar.setMaxProgress(duration);
                mProgressBar.setProgress(progress);
            }
        }
    };

    public MusicApplication(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_music);
        initView();
        initRecyclerView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicServiceConstants.MUSIC_SERVICE);
        getContext().registerReceiver(receiver, intentFilter);
    }

    private void initView() {
        findViewById(R.id.close_button).setOnClickListener( view -> {
            stopMusicService();
            dismiss();
        });
        findViewById(R.id.reduce_close).setOnClickListener( view -> {
            dismiss();
        });
        mAnimImageView = findViewById(R.id.anim_image);
        mProgressBar = findViewById(R.id.progress_bar);
        mPreviousButton = findViewById(R.id.previous_button);
        mStopButton = findViewById(R.id.stop_button);
        mNextButton = findViewById(R.id.next_button);
        mStartButton = findViewById(R.id.start_button);
        mPreviousButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(MusicServiceConstants.MUSIC_SERVICE);
            intent.putExtra(MusicServiceConstants.PREVIOUS_SONG, true);
            getContext().sendBroadcast(intent);
        });
        mStopButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(MusicServiceConstants.MUSIC_SERVICE);
            intent.putExtra(MusicServiceConstants.SWITCH_SONG, MusicServiceConstants.STOP_SONG);
            getContext().sendBroadcast(intent);
            mStartButton.setVisibility(View.VISIBLE);
            mStopButton.setVisibility(View.GONE);
        });
        mNextButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(MusicServiceConstants.MUSIC_SERVICE);
            intent.putExtra(MusicServiceConstants.NEXT_SONG, true);
            getContext().sendBroadcast(intent);
        });
        mStartButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(MusicServiceConstants.MUSIC_SERVICE);
            intent.putExtra(MusicServiceConstants.SWITCH_SONG, MusicServiceConstants.START_SONG);
            getContext().sendBroadcast(intent);
            mStartButton.setVisibility(View.GONE);
            mStopButton.setVisibility(View.VISIBLE);
        });
    }

    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.music_list_recycler_view);
        List<MusicBean> musicList = MusicList.getMusicList(getContext());
        mAdapter = new MusicAdapter(getContext(),musicList);
        mAdapter.setmListener((position) ->{
            startMusicService();
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void startMusicService() {
        if (isMusicServiceLive()){
            return;
        }
        Intent intent = new Intent(getContext(), MusicService.class);
        getContext().startService(intent);
    }

    /**
     * 音乐服务是否存活
     */
    private boolean isMusicServiceLive() {
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo runningService : runningServices) {
            if (runningService.service.getClassName().equals(MusicServiceConstants.MUSIC_SERVICE_NAME)) {
                Log.i(TAG, "music service is running");
                return true;
            }
        }
        return false;
    }

    /**
     * 关闭音乐服务
     */
    private void stopMusicService() {
        if (isMusicServiceLive()) {
            Intent intent = new Intent(getContext(), MusicService.class);
            getContext().stopService(intent);
        }
    }


    /**
     * 图片旋转
     */
    private void rotating(){
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.music_keep_rotating_anim);
        animation.setInterpolator(new LinearInterpolator());
        mAnimImageView.startAnimation(animation);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getContext().unregisterReceiver(receiver);
    }

    public interface onMusicListener{
        void onClick(int position);
    }

}
