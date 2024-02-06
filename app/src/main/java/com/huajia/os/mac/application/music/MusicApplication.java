package com.huajia.os.mac.application.music;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;
import com.huajia.os.mac.application.BaseApplication;
import com.huajia.os.mac.application.music.adapter.MusicAdapter;
import com.huajia.os.mac.application.music.bean.MusicBean;
import com.huajia.os.mac.application.music.view.CircularProgressBar;
import com.huajia.os.mac.utils.ToastUtils;

import java.util.List;

public class MusicApplication extends BaseApplication {
    private static final String TAG = "MusicApplication";

    private ImageView ivClose;

    private RecyclerView mRecyclerView;

    private MusicAdapter mAdapter;

    private MediaPlayer mMediaPlayer;

    //旋转图片
    private ImageView mAnimImageView;

    private CircularProgressBar mProgressBar;

    private ImageView mPreviousButton;

    private ImageView mStopButton;

    private ImageView mNextButton;

    private ImageView mStartBUtton;

    //当前正在播放第几首歌曲
    private int mPlayerPosition = 0;

    private Handler mBackGroundHander;

    private Runnable mProgressRunable;

    private List<MusicBean> mMusicList = MusicList.getMusicList(getContext());

    public MusicApplication(@NonNull Context context) {
        super(context);
    }

    public MusicApplication(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MusicApplication(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_music);
        initView();
        initRecylerView();
    }

    private void initView() {
        ivClose = findViewById(R.id.close_button);
        mAnimImageView = findViewById(R.id.anim_image);
        mProgressBar = findViewById(R.id.progress_bar);
        mPreviousButton = findViewById(R.id.previous_button);
        mStopButton = findViewById(R.id.stop_button);
        mNextButton = findViewById(R.id.next_button);
        mStartBUtton = findViewById(R.id.start_button);
        ivClose.setOnClickListener(view -> {
            dismiss();
        });
        mPreviousButton.setOnClickListener(view -> {
            previousMusic();
        });
        mStopButton.setOnClickListener(view -> {
            stopMusic();
        });
        mNextButton.setOnClickListener(view -> {
            nextMusic();
        });
        mStartBUtton.setOnClickListener(view -> {
            startMusic();
        });
    }

    private void initRecylerView(){
        mRecyclerView = findViewById(R.id.music_list_recylerView);
        List<MusicBean> musicList = MusicList.getMusicList(getContext());
        mAdapter = new MusicAdapter(getContext(),musicList);
        mAdapter.setmListener((position) ->{
            changeSong(position);
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void changeSong(int position){
        if (mMediaPlayer != null){
            mMediaPlayer.release();
        }
        mMediaPlayer = MediaPlayer.create(getContext(),mMusicList.get(position).getmMusicUri());
        mProgressBar.setMaxProgress(mMediaPlayer.getDuration());
        updateProgress();
        mMediaPlayer.start();
        mPlayerPosition = position;
        rotating();
    }

    private void updateProgress(){
        // 创建一个定时任务，以一定的时间间隔更新进度条
        mBackGroundHander = new Handler();
        mProgressRunable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (mMediaPlayer == null){
                        return;
                    }
                    if (!mMediaPlayer.isPlaying()){
                        rotating();
                    }
                    int currentPosition = mMediaPlayer.getCurrentPosition();
                    mProgressBar.setProgress(currentPosition);
                    // 每隔一定时间再次执行该任务
                    mBackGroundHander.postDelayed(this, 1000); // 每秒更新一次
                }catch (Exception exception){
                    Log.i(TAG,"exception ： " + exception.getMessage());
                }

            }
        };
        mBackGroundHander.post(mProgressRunable);
    }

    /**
     * 图片旋转
     */
    private void rotating(){
        if (!mMediaPlayer.isPlaying()){
            mAnimImageView.clearAnimation();
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.music_keep_rotating_anim);
        animation.setInterpolator(new LinearInterpolator());
        mAnimImageView.startAnimation(animation);
    }

    private void previousMusic(){
        if (mPlayerPosition <= 0){
            ToastUtils.show(getContext(),"没有上一首");
            return;
        }
        changeSong(mPlayerPosition - 1);
    }

    private void stopMusic(){
        if (mMediaPlayer == null || !mMediaPlayer.isPlaying()){
            return;
        }
        mMediaPlayer.pause();
        mStopButton.setVisibility(View.GONE);
        mStartBUtton.setVisibility(View.VISIBLE);
    }

    private void nextMusic(){
        if (mPlayerPosition >= mMusicList.size() - 1){
            ToastUtils.show(getContext(),"没有下一首");
            return;
        }
        changeSong(mPlayerPosition + 1);
    }

    private void startMusic(){
        if (mMediaPlayer == null || mMediaPlayer.isPlaying()){
            return;
        }
        mMediaPlayer.start();
        mStopButton.setVisibility(View.VISIBLE);
        mStartBUtton.setVisibility(View.GONE);
        rotating();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBackGroundHander != null){
            mBackGroundHander.removeCallbacks(mProgressRunable);
        }
        if (mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    public interface onMusicListener{
        void onClick(int position);
    }

}
