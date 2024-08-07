package com.huajia.venusdesktop.service.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.huajia.annotation.Route;
import com.huajia.venusdesktop.base.BaseApplication;
import com.huajia.venusdesktop.framework.router.TRouter;
import com.huajia.venusdesktop.framework.router.TRouterPath;
import com.huajia.venusdesktop.framework.router.TRouterProtocol;
import com.huajia.venusdesktop.R;

/**
 * @Author: HuaJ1a
 * @Email: 821759439@qq.com
 * @Date: 2024/5/13
 * @Description: 申请权限dialog
 */
@Route(path = TRouterPath.DIALOG_PERMISSION, heightPercent = 0.66f)
public class PermissionDialog extends BaseApplication {
    private static final String TAG = "PermissionDialog";

    public static final String PROTOCOL_REASON = "protocol_reason";

    private TextView tvReason;

    private Button btnOpen;

    public static void jumpToPermissionDialog(String reason) {
        TRouter.getInstance().build(TRouterPath.DIALOG_PERMISSION)
                .withMove(false)
                .withProtocol(PROTOCOL_REASON, reason)
                .navigation();
    }

    public PermissionDialog(@NonNull Context context, TRouterProtocol protocol) {
        super(context);
        this.protocol = protocol;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_permission);
        tvReason = findViewById(R.id.tv_reason);
        tvReason.setText(this.protocol.get(PROTOCOL_REASON, "使用该功能需要申请权限").toString());
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

}
