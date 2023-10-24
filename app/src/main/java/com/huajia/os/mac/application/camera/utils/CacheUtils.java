package com.huajia.os.mac.application.camera.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 图片缓存工具类
 * @author: huajia
 * @date: 2023/10/8 22:31
 */

public class CacheUtils {

    public static File getCacheDirectory(Context context) {
        File mediaDir = new File(context.getFilesDir(), "album");

        if (!mediaDir.exists()) {
            mediaDir.mkdirs();
        }

        return mediaDir;
    }

}