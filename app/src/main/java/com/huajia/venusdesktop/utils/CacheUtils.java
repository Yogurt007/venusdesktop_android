package com.huajia.venusdesktop.utils;

import android.content.Context;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 图片缓存工具类
 * @author: huajia
 * @date: 2023/10/8 22:31
 */

public class CacheUtils {

    public static File getAlbumDirectory(Context context) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        String currentDate = dateFormat.format(calendar.getTime());
        String fileName = "album/" + currentDate;
        File mediaDir = new File(context.getFilesDir(), fileName);
        if (!mediaDir.exists()) {
            mediaDir.mkdirs();
        }
        return mediaDir;
    }

}