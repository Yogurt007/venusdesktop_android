package com.huajia.mac.service.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.huajia.mac.base.BaseApplication;
import com.huajia.os.mac.R;

import java.util.HashMap;

/**
 * @Author: HuaJ1a
 * @Email: 821759439@qq.com
 * @Date: 2024/5/13
 * @Description: 申请权限dialog
 */
public class PermissionDialog extends BaseApplication {
    private static final String TAG = "PermissionDialog";

    public static final String PARAM_REASON = "param_reason";

    private TextView tvReason;

    private Button btnOpen;

    public PermissionDialog(@NonNull Context context, HashMap<Object, Object> params) {
        super(context);
        initView();
        initData(params);
    }

    private void initView() {
        setContentView(R.layout.dialog_permission);
        tvReason = findViewById(R.id.tv_reason);
        btnOpen = findViewById(R.id.btn_open_permission);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                intent.setData(uri);
                getContext().startActivity(intent);
            }
        });
    }

    private void initData(HashMap<Object, Object> params) {
        if (params != null) {
            String reason = (String) params.get(PARAM_REASON);
            tvReason.setText(reason);
        }
    }

}
