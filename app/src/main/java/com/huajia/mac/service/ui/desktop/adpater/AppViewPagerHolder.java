package com.huajia.mac.service.ui.desktop.adpater;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;
import com.huajia.mac.service.ui.desktop.bean.App;

import java.util.ArrayList;
import java.util.List;

public class AppViewPagerHolder extends RecyclerView.ViewHolder {
    private List<App> mList = new ArrayList<>();

    private RecyclerView mRecylerView;

    private AppViewRecylerAdapter mAdapter;

    public AppViewPagerHolder(@NonNull View itemView, Context context) {
        super(itemView);
        mRecylerView = itemView.findViewById(R.id.recycler_view);
        mAdapter = new AppViewRecylerAdapter(context,mList);
        mRecylerView.setAdapter(mAdapter);
        mRecylerView.setLayoutManager(new GridLayoutManager(context,5));
    }

    public void setData(List<App> list){
        mAdapter.refreshData(list);
    }
}
