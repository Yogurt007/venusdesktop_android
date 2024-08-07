package com.huajia.venusdesktop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.huajia.venusdesktop.base.BaseActivity;
import com.huajia.venusdesktop.framework.router.TRouter;
import com.huajia.venusdesktop.framework.service.KeepLiveService;
import com.huajia.venusdesktop.framework.storage.SharedPreferencesManager;
import com.huajia.venusdesktop.framework.window.WindowsManager;

/**
 * @Author: HuaJ1a
 * @Email: 821759439@qq.com
 * @Date: 2024/5/13
 * @Description: 处理逻辑
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * 初始化全局服务
     */
    private void init() {
        // sp初始化
        SharedPreferencesManager.getInstance().init(getApplicationContext());
        // 路由管理类
        TRouter.getInstance().init(MainActivity.this);
        // app管理类
        WindowsManager.getInstance().init(MainActivity.this);
        // 保活服务
        Intent intent = new Intent(MainActivity.this, KeepLiveService.class);
        startService(intent);
    }
}
