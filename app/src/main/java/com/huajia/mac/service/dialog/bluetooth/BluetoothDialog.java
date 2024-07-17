package com.huajia.mac.service.dialog.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.annotation.Route;
import com.huajia.mac.framework.router.TRouterPath;
import com.huajia.os.mac.R;
import com.huajia.mac.base.BaseApplication;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/8
 * @Description:
 */
@Route(path = TRouterPath.DIALOG_BLUETOOTH, widthPercent = 0.33f)
public class BluetoothDialog extends BaseApplication {
    private static final String TAG = "BluetoothDialog";

    private Context context;

    private BluetoothAdapter bluetoothAdapter;

    private CheckBox bluetoothSwitch;

    private boolean isFromBroadcast;

    private RecyclerView recyclerView;

    private BluetoothRecyclerViewAdapter adapter;

    private ImageView refreshButton;

    public BluetoothDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_bluetooth);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter switchFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        context.registerReceiver(receiver, switchFilter);

        IntentFilter start = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        context.registerReceiver(receiver, start);

        IntentFilter finish = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.registerReceiver(receiver, finish);

        IntentFilter found = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(receiver, found);
    }

    @Override
    protected void onStop() {
        super.onStop();
        context.unregisterReceiver(receiver);
    }

    @SuppressLint("MissingPermission")
    private void initView() {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothSwitch = findViewById(R.id.bluetooth_checkbox);
        bluetoothSwitch.setChecked(bluetoothAdapter.isEnabled());
        bluetoothSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isFromBroadcast) {
                    isFromBroadcast = false;
                    return;
                }
                if (isChecked) {
                    changeBluetoothStatus(true);
                } else {
                    changeBluetoothStatus(false);
                }
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new BluetoothRecyclerViewAdapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshButton = findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(view -> {
            if (bluetoothAdapter.isDiscovering()) {
                return;
            }
            adapter.cleanData();
            bluetoothAdapter.startDiscovery();
        });
    }

    @SuppressLint("MissingPermission")
    private void changeBluetoothStatus(boolean status) {
        if (bluetoothAdapter == null) {
            Log.i(TAG, "bluetoothAdapter is null");
            return;
        }
        if (status) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivity(intent);
        } else {
            bluetoothAdapter.disable();
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            isFromBroadcast = true;
            String action = intent.getAction();
            switch (action) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                    Log.i(TAG,"receiver,BluetoothAdapter.ACTION_STATE_CHANGED: " + state);
                    bluetoothSwitch.setChecked(bluetoothAdapter.isEnabled());
                    startDiscovery();
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    discoveryAnim(true);
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    discoveryAnim(false);
                    break;
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device == null || device.getName() == null) {
                        return;
                    }
                    adapter.refreshData(device);
                    break;
            }
        }
    };

    private void discoveryAnim(boolean status) {
        if (status) {
            RotateAnimation rotate = new RotateAnimation(0f, 360f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setInterpolator(new LinearInterpolator());
            rotate.setDuration(1500);
            rotate.setRepeatCount(Animation.INFINITE);
            refreshButton.startAnimation(rotate);
        } else {
            refreshButton.clearAnimation();
        }
    }

    @SuppressLint("MissingPermission")
    private void startDiscovery() {
        if (bluetoothAdapter == null) {
            Log.i(TAG, "bluetoothAdapter is null");
            return;
        }
        bluetoothAdapter.startDiscovery();
    }
}
