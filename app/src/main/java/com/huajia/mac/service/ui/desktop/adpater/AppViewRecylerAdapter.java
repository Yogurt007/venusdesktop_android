package com.huajia.mac.service.ui.desktop.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;
import com.huajia.mac.service.ui.desktop.bean.App;

import java.util.ArrayList;
import java.util.List;

public class AppViewRecylerAdapter extends RecyclerView.Adapter {
    private Context mContext;

    private List<App> mList = new ArrayList<>();

    public AppViewRecylerAdapter(Context context,List<App> list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_of_app_recyler_view,parent,false);
        AppViewRecylerHolder holder = new AppViewRecylerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  AppViewRecylerHolder){
            AppViewRecylerHolder viewHolder = (AppViewRecylerHolder) holder;
            viewHolder.appName.setText(mList.get(position).getName());
            viewHolder.appIcon.setImageDrawable(mList.get(position).getIcon());
            viewHolder.mContainer.setOnClickListener(view -> {
                try {
                    Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mList.get(position).getPkgName());
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

    public void refreshData(List<App> list){
        mList = list;
        notifyDataSetChanged();
    }
}
