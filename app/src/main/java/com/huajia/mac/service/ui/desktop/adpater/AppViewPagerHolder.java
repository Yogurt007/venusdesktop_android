package com.huajia.mac.service.ui.desktop.adpater;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;
import com.huajia.mac.service.ui.desktop.bean.LocalAppBean;

import java.util.ArrayList;
import java.util.List;

public class AppViewPagerHolder extends RecyclerView.ViewHolder {
    private List<LocalAppBean> mList = new ArrayList<>();

    private RecyclerView mRecyclerView;

    private AppViewRecylerAdapter mAdapter;

    public AppViewPagerHolder(@NonNull View itemView, Context context) {
        super(itemView);
        mRecyclerView = itemView.findViewById(R.id.recycler_view);
        mAdapter = new AppViewRecylerAdapter(context,mList);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mAdapter.getTouchCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,5));
    }

    public void setData(List<LocalAppBean> list){
        mAdapter.refreshData(list);
    }
}
