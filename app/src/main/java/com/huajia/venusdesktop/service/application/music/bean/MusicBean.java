package com.huajia.venusdesktop.service.application.music.bean;

import android.graphics.drawable.Drawable;

/**
 * @author: huajia
 * @date: 2023/10/14 16:52
 */

public class MusicBean {
    //图片
    private Drawable mImage;

    //歌名
    private String mSongName;

    //歌手
    private String mSinger;

    private int mMusicUri;

    public MusicBean(Drawable image,String songName,String singer,int musicUri){
        this.mImage = image;
        this.mSongName = songName;
        this.mSinger = singer;
        this.mMusicUri = musicUri;
    }

    public Drawable getmImage() {
        return mImage;
    }

    public void setmImage(Drawable mImage) {
        this.mImage = mImage;
    }

    public String getmSongName() {
        return mSongName;
    }

    public void setmSongName(String mSongName) {
        this.mSongName = mSongName;
    }

    public String getmSinger() {
        return mSinger;
    }

    public void setmSinger(String mSinger) {
        this.mSinger = mSinger;
    }

    public int getmMusicUri() {
        return mMusicUri;
    }

    public void setmMusicUri(int mMusicUri) {
        this.mMusicUri = mMusicUri;
    }
}
