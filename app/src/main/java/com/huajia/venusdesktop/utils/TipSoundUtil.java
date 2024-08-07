package com.huajia.venusdesktop.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.huajia.venusdesktop.R;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/24
 * @Description:
 */
public class TipSoundUtil {

    public static void takePhoto(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.shutter);
        mediaPlayer.start();
    }

}
