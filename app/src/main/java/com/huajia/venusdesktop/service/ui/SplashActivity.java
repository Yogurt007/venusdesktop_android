package com.huajia.venusdesktop.service.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huajia.venusdesktop.UIMainActivity;
import com.huajia.venusdesktop.R;
import com.huajia.venusdesktop.base.BaseActivity;

/**
 * 开机动画
 */
public class SplashActivity extends BaseActivity {

    private SeekBar mSeekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_on);

        mSeekBar = findViewById(R.id.seekBar);

        initAnim();
    }

    private void initAnim() {
        ValueAnimator anim = ValueAnimator.ofInt(0, 100);
        anim.setDuration(2500);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator value) {
                int progress = (int) value.getAnimatedValue();
                mSeekBar.setProgress(progress);
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(getApplicationContext(), UIMainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        anim.start();
    }
}
