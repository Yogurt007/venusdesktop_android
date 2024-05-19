package com.huajia.mac.service.application.draw;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.huajia.os.mac.R;
import com.huajia.mac.base.BaseApplication;
import com.huajia.mac.service.application.draw.view.DrawBoardView;

public class DrawApplication extends BaseApplication {
    private static final String TAG = "DrawApplication";

    private DrawBoardView mDrawBoardView;

    private SeekBar mPaintWidthBar;

    public DrawApplication(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_draw);
        initView();
    }


    private void initView() {
        findViewById(R.id.close_button).setOnClickListener( view -> {
            dismiss();
        });
        mPaintWidthBar = findViewById(R.id.paint_width_seekbar);
        mPaintWidthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG,"progress = " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        mDrawBoardView = findViewById(R.id.draw_board_view);
    }


}
