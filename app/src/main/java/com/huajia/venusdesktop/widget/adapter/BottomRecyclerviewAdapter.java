package com.huajia.venusdesktop.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.venusdesktop.framework.application.ApplicationBean;
import com.huajia.venusdesktop.framework.application.ApplicationManager;
import com.huajia.venusdesktop.R;

import java.util.List;

/**
 * Description:
 * Author: HuaJ1a
 * Date: 2024/6/30
 */
public class BottomRecyclerviewAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<ApplicationBean> appList;

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

    public BottomRecyclerviewAdapter(Context context, List<ApplicationBean> appList) {
        this.appList = appList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_of_activity_bottom, parent, false);
        BottomRecyclerviewHolder viewHolder = new BottomRecyclerviewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BottomRecyclerviewHolder viewHolder = (BottomRecyclerviewHolder) holder;
        ApplicationBean applicationBean = this.appList.get(position);
        viewHolder.icon.setImageDrawable(applicationBean.getIcon());
        viewHolder.itemView.setOnClickListener( view -> {
            ApplicationManager.getInstance().openApplication(this.context, applicationBean);
        });
    }

    @Override
    public int getItemCount() {
        return appList == null ? 0 : appList.size();
    }

    public ItemTouchHelper.Callback getTouchCallback() {
        return touchCallback;
    }

    class BottomRecyclerviewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;

        public BottomRecyclerviewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon_view);
        }
    }
}
