package com.huajia.os.mac.application.draw;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huajia.os.mac.R;
import com.huajia.os.mac.application.BaseApplication;
import com.huajia.os.mac.application.draw.view.DrawBoardView;

public class DrawApplication extends BaseApplication {
    private static final String TAG = "DrawApplication";

    private DrawBoardView mDrawBoardView;

    private ImageView ivClose;

    private SeekBar mPaintWidthBar;

    public DrawApplication(@NonNull Context context) {
        super(context);
    }

    public DrawApplication(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DrawApplication(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_draw);
        initView();
    }

    private void initView() {
        ivClose = findViewById(R.id.close_button);
        ivClose.setOnClickListener(view -> {
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
