package com.huajia.os.mac.activity.appdesktop;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;
import com.huajia.os.mac.activity.appdesktop.adpater.AppDesktopAdapter;
import com.huajia.os.mac.activity.appdesktop.bean.App;
import com.huajia.os.mac.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

public class AppDesktopActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private AppDesktopAdapter mAdapter;

    private PackageManager mPackageManager;

    private List<App> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_desktop);

        UIHelper.initActivityUI(this);

        intAppDesktop();

    }

    private void intAppDesktop() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
        mPackageManager = getPackageManager();
        List<ApplicationInfo> applications = mPackageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo application : applications) {
            if ((application.flags & ApplicationInfo.FLAG_SYSTEM) <= 0){
                mList.add(new App((String) application.loadLabel(mPackageManager),application.loadIcon(mPackageManager),application.packageName));
            }
        }
        mAdapter = new AppDesktopAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,7));

    }
}
