package com.huajia.venusdesktop.service.ui.desktop.adpater;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.venusdesktop.R;
import com.huajia.venusdesktop.service.ui.desktop.bean.LocalAppBean;

import java.util.ArrayList;
import java.util.List;

public class AppDesktopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    private List<LocalAppBean> mList = new ArrayList<>();

    private PackageManager mPackageManager;

    public AppDesktopAdapter(Context context,List<LocalAppBean> list){
        this.mContext = context;
        this.mList = list;
        mPackageManager = mContext.getPackageManager();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_of_activity_app_desktop,parent,false);
        AppDesktopViewHolder viewHolder = new AppDesktopViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  AppDesktopViewHolder){
            AppDesktopViewHolder viewHolder = (AppDesktopViewHolder) holder;
            viewHolder.icon.setImageDrawable(mList.get(position).getIcon());
            viewHolder.name.setText(mList.get(position).getName());
            viewHolder.itemView.setOnClickListener(view -> {
                try {
                    Intent intent = mPackageManager.getLaunchIntentForPackage(mList.get(position).getPkgName());
                    mContext.startActivity(intent);
                }catch (Exception exception){

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class AppDesktopViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon;

        private TextView name;

        public AppDesktopViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.app_icon);
            name = itemView.findViewById(R.id.app_name);
        }
    }

}
