package com.huajia.mac.service.dialog.wifi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.annotation.Route;
import com.huajia.mac.framework.router.TRouterPath;
import com.huajia.os.mac.R;
import com.huajia.mac.base.BaseApplication;
import com.huajia.mac.utils.PermissionHelper;

import java.util.List;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/5
 * @Description:
 */
@Route(path = TRouterPath.DIALOG_WIFI, widthPercent = 0.33f)
public class WifiDialog extends BaseApplication {
    private static final String TAG = "WifiDialog";

    private Context context;

    private WifiManager wifiManager;

    private RecyclerView recyclerView;

    private WifiRecyclerViewAdapter adapter;

    private CheckBox wifiSwitch;
    public WifiDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_wifi);
        this.context = context;
        initView();
    }

    @SuppressLint("MissingPermission")
    private void initView() {
        PermissionHelper.checkWifiPermission(context);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = wifiManager.getScanResults();
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new WifiRecyclerViewAdapter(getContext(), scanResults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        wifiSwitch = findViewById(R.id.wifi_checkbox);
        wifiSwitch.setChecked(wifiManager.isWifiEnabled());
    }
}
