package com.huajia.venusdesktop.service.ui.desktop.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.venusdesktop.R;
import com.huajia.venusdesktop.service.ui.desktop.bean.LocalAppBean;

import java.util.ArrayList;
import java.util.List;

public class AppViewRecylerAdapter extends RecyclerView.Adapter {
    private Context mContext;

    private List<LocalAppBean> mList = new ArrayList<>();

    private ItemTouchHelper.Callback touchCallback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            return makeMovementFlags(dragFlags, 0);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    public AppViewRecylerAdapter(Context context,List<LocalAppBean> list){
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

    public void refreshData(List<LocalAppBean> list){
        mList = list;
        notifyDataSetChanged();
    }

    public ItemTouchHelper.Callback getTouchCallback() {
        return touchCallback;
    }
}
