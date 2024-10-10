package com.huajia.venusdesktop.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/4/8
 * @Description:
 */
public class TimeUtils {

    public static String secToTime(int sec) {
        int hour = sec / 3600;
        int minute = (sec - hour * 3600) / 60;
        int second = (sec - hour * 3600 - minute * 60);
        if (hour > 0) {
            return String.format("%d:%d:%d", hour, minute, second);
        }
        if (minute > 0) {
            return String.format("%d:%d", minute, second);
        }
        if (second < 10) {
            return String.format("00:0%d", second);
        }
        return String.format("00:%d", second);
    }

    public static long dateToStamp(String sDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(sDate);
            return date.getTime();
        } catch (Exception e) {

        }
        return 0L;
    }

}
