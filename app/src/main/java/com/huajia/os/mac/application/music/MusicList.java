package com.huajia.os.mac.application.music;

import android.content.Context;

import com.huajia.os.mac.R;
import com.huajia.os.mac.application.music.bean.MusicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐列表
 * @author: huajia
 * @date: 2023/10/14 17:04
 */

public class MusicList {

    public static List<MusicBean> getMusicList(Context context){
        List<MusicBean> list = new ArrayList<>();
        list.add(new MusicBean(context.getResources().getDrawable(R.drawable.music_image_leijun),"Are you ok","雷军",R.raw.music_are_you_ok));
        list.add(new MusicBean(context.getResources().getDrawable(R.drawable.music_image_yuchengdong),"遥遥领先","余承东",R.raw.music_huawei));
        return list;
    }

}
