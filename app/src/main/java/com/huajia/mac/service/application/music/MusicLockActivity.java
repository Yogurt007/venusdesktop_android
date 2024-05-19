package com.huajia.mac.service.application.music;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.huajia.os.mac.R;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/4/14
 * @Description:
 */
public class MusicLockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_lock);
        initView();
    }

    @Override
    public void onBackPressed() {
    }

    private void initView() {
        ImmersionBar.with(MusicLockActivity.this)
                .statusBarDarkFont(true)
                .init();
    }

}
