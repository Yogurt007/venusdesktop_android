package com.huajia.os.mac.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huajia.os.mac.MainActivity;
import com.huajia.os.mac.R;
import com.huajia.os.mac.base.BaseActivity;

/**
 * 开机动画
 */
public class PowerOnActivity extends BaseActivity {

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
        anim.setDuration(3000);
        anim.setInterpolator(new DecelerateInterpolator());
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        anim.start();
    }
}
