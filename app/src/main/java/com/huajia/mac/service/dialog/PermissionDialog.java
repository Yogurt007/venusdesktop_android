package com.huajia.mac.service.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.huajia.mac.base.BaseApplication;
import com.huajia.os.mac.R;

/**
 * @Author: HuaJ1a
 * @Email: 821759439@qq.com
 * @Date: 2024/5/13
 * @Description: 申请权限dialog
 */
public class PermissionDialog extends BaseApplication {

    public PermissionDialog(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_permission);
    }

}
