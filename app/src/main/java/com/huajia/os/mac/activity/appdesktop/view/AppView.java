package com.huajia.os.mac.activity.appdesktop.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.huajia.os.mac.R;
import com.huajia.os.mac.activity.appdesktop.adpater.AppViewPagerAdapter;
import com.huajia.os.mac.activity.appdesktop.bean.App;

import java.util.ArrayList;
import java.util.List;

public class AppView extends FrameLayout {
    private static final String TAG = "AppView";

    private static final int PAGE_NUM = 15;

    private View rootView;

    private ViewPager2 mViewPager;

    private List<App> mList;

    private PackageManager mPackageManager;

    private AppViewPagerAdapter mAdapter;

    private DotLayout dotLayout;

    public AppView(Context context) {
        super(context);
        initView();
    }

    public AppView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AppView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.app_view,this);
        mViewPager = rootView.findViewById(R.id.view_pager);
        initData();
        mAdapter = new AppViewPagerAdapter(getContext(),mList,PAGE_NUM);
        mViewPager.setAdapter(mAdapter);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                dotLayout.setCurrentItem(position);
            }
        });

        dotLayout = rootView.findViewById(R.id.dot_layout);
        dotLayout.init((int) Math.ceil(mList.size() / 16f));
    }

    private void initData(){
        mList = new ArrayList<>();
        mPackageManager = getContext().getPackageManager();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfoList = getContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            mList.add(new App(
                    (String) resolveInfo.activityInfo.loadLabel(mPackageManager),
                    resolveInfo.activityInfo.loadIcon(mPackageManager),
                    resolveInfo.activityInfo.packageName));
        }
    }

}
