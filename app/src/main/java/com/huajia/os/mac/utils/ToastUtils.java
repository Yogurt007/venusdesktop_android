package com.huajia.os.mac.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.huajia.os.mac.R;
import com.huajia.os.mac.window.WindowsManager;

/**
 * @author: huajia
 * @date: 2023/10/7 22:01
 */

public class ToastUtils {

    private static MediaPlayer mMediaPlayer;

    public static void show(Context context,String text){
        if (mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(context,R.raw.mac_tip_sound);
        }
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_dialog_view, null, false);
        TextView textView = view.findViewById(R.id.toast_text);
        textView.setText(text);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,
                WindowsManager.getInstance().getMaxWidthApplication() / 2 - toast.getView().getWidth(),
                -(WindowsManager.getInstance().getMaxHeightApplciation() / 2 - toast.getView().getHeight()));
        toast.show();
        mMediaPlayer.start();
    }



}
