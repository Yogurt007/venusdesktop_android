package com.huajia.mac.service.ui.desktop.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;
import com.huajia.mac.service.ui.desktop.bean.App;

import java.util.ArrayList;
import java.util.List;

public class AppViewPagerAdapter extends RecyclerView.Adapter {
    private Context mContext;

    private List<App> mList = new ArrayList<>();

    /**
     * 一页显示的个数
     */
    private int mPageNum;

    public AppViewPagerAdapter(Context context,List<App> list,int pageNum){
        mContext = context;
        mList = list;
        mPageNum = pageNum;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_of_app_view,parent,false);
        AppViewPagerHolder holder = new AppViewPagerHolder(view,mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppViewPagerHolder){
            AppViewPagerHolder viewHoler = (AppViewPagerHolder) holder;
            int startIndex = position * mPageNum;
            int endIndex = mPageNum * position + mPageNum;
            int maxEndIndex = Math.min(endIndex,mList.size());
            viewHoler.setData(mList.subList(startIndex,maxEndIndex));
        }
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(mList.size() * 1.0f / mPageNum);
    }

}
