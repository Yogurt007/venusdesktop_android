package com.huajia.venusdesktop.service.ui.desktop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.huajia.venusdesktop.databinding.AppViewBinding;
import com.huajia.venusdesktop.framework.application.ApplicationManager;
import com.huajia.venusdesktop.service.ui.desktop.adpater.AppViewPagerAdapter;
import com.huajia.venusdesktop.R;
import com.huajia.venusdesktop.service.ui.desktop.bean.LocalAppBean;

import java.util.List;

public class AppView extends FrameLayout {
    private static final String TAG = "AppView";

    private AppViewBinding binding;

    private static final int PAGE_NUM = 15;

    private View rootView;

    private List<LocalAppBean> mList = ApplicationManager.getInstance().getLocalAppList();

    private AppViewPagerAdapter mAdapter;

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
        binding = AppViewBinding.bind(rootView);
        mAdapter = new AppViewPagerAdapter(getContext(),mList,PAGE_NUM);
        binding.viewPager.setAdapter(mAdapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.dotLayout.setCurrentItem(position);
            }
        });

        binding.dotLayout.init((int) Math.ceil(mList.size() / 16f));
    }
}
