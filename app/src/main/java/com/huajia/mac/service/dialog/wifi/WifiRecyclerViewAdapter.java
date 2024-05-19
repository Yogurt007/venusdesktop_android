package com.huajia.mac.service.dialog.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/6
 * @Description:
 */
public class WifiRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<ScanResult> list;

    public WifiRecyclerViewAdapter(Context context, List<ScanResult> list) {
        this.context = context;
        this.list = list;
        for (int i = 0; i < this.list.size(); i++) {
            if (list.get(i).SSID == "") {
                this.list.remove(i);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_of_wifi, parent, false);
        WifiRecyclerViewHolder viewHolder = new WifiRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WifiRecyclerViewHolder viewHolder = (WifiRecyclerViewHolder) holder;
        viewHolder.wifiSSID.setText(list.get(position).SSID);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class WifiRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView wifiSSID;

        public WifiRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            wifiSSID = itemView.findViewById(R.id.wifi_ssid);
        }
    }
}
