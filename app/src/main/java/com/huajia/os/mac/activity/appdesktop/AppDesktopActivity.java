package com.huajia.os.mac.activity.appdesktop;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;
import com.huajia.os.mac.activity.appdesktop.adpater.AppDesktopAdapter;
import com.huajia.os.mac.activity.appdesktop.bean.App;
import com.huajia.os.mac.activity.appdesktop.view.AppView;
import com.huajia.os.mac.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

public class AppDesktopActivity extends AppCompatActivity {
    private static final String TAG = "AppDesktopActivity";

    private RecyclerView mRecyclerView;

    private AppDesktopAdapter mAdapter;

    private PackageManager mPackageManager;

    private List<App> mList;

    private AppView mAppView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_desktop);

        UIHelper.initActivityUI(this);

        initView();

        intAppDesktop();

    }

    private void initView(){
        mAppView = findViewById(R.id.app_view);
    }

    private void intAppDesktop() {
//        mRecyclerView = findViewById(R.id.recyclerView);
//        mList = new ArrayList<>();
//        mPackageManager = getPackageManager();
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
//        for (ResolveInfo resolveInfo : resolveInfoList) {
//            mList.add(new App(
//                    (String) resolveInfo.activityInfo.loadLabel(mPackageManager),
//                    resolveInfo.activityInfo.loadIcon(mPackageManager),
//                    resolveInfo.activityInfo.packageName));
//        }
//        mAdapter = new AppDesktopAdapter(this, mList);
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
    }
}
