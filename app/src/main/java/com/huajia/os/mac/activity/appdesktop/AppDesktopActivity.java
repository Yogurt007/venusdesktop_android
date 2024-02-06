package com.huajia.os.mac.activity.appdesktop;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;
import com.huajia.os.mac.activity.appdesktop.adpater.AppDesktopAdapter;
import com.huajia.os.mac.activity.appdesktop.bean.App;
import com.huajia.os.mac.activity.appdesktop.view.AppView;
import com.huajia.os.mac.base.BaseActivity;

import java.util.List;

public class AppDesktopActivity extends BaseActivity {
    private static final String TAG = "AppDesktopActivity";

    private AppView mAppView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_desktop);

        initView();

    }

    private void initView(){
        mAppView = findViewById(R.id.app_view);
    }

}
