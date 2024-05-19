package com.huajia.mac.service.dialog.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/10
 * @Description:
 */
public class BluetoothRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<BluetoothDevice> list;

    public BluetoothRecyclerViewAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_of_bluetooth, parent, false);
        BluetoothRecyclerViewHolder viewHolder = new BluetoothRecyclerViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BluetoothRecyclerViewHolder viewHolder = (BluetoothRecyclerViewHolder) holder;
        viewHolder.nameView.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void refreshData(BluetoothDevice device) {
        for (BluetoothDevice bluetoothDevice : list) {
            if (bluetoothDevice.getAddress().equals(device.getAddress())){
                return;
            }
        }
        list.add(device);
        notifyDataSetChanged();
    }

    public void cleanData() {
        list.clear();
    }

    class BluetoothRecyclerViewHolder extends RecyclerView.ViewHolder{
        private TextView nameView;

        public BluetoothRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.bluetooth_name);
        }
    }
}
