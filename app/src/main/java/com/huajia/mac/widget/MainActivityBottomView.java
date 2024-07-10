package com.huajia.mac.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.huajia.mac.framework.application.ApplicationManager;
import com.huajia.mac.framework.window.WindowsWant;
import com.huajia.mac.service.dialog.PermissionDialog;
import com.huajia.mac.widget.adapter.BottomRecyclerviewAdapter;
import com.huajia.os.mac.R;
import com.huajia.mac.service.ui.desktop.AppDesktopActivity;
import com.huajia.mac.framework.window.WindowsConstants;
import com.huajia.mac.framework.window.WindowsManager;
import com.huajia.mac.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/2
 * @Description:
 */
public class MainActivityBottomView extends FrameLayout {
    private static final String TAG = "MainActivityBottomView";

    private Context context;

    private View rootView;

    private RecyclerView recyclerView;

    private BottomRecyclerviewAdapter adapter;

    public MainActivityBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.activity_main_bottom, this);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        adapter = new BottomRecyclerviewAdapter(getContext(), ApplicationManager.getInstance().getAppList());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(adapter.getTouchCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,"按下： x : " + event.getRawX() + ", y : " + event.getRawY());
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获得图标的中心坐标
     */
    private void getCenterCoordinate(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int centerX = location[0] + view.getWidth() / 2;
        int centerY = location[1] + view.getHeight() / 2;
        Log.i(TAG, "getCenterCoordinate: centerX = " + centerX + ", centerY = " + centerY);
    }
}
