package com.huajia.mac.service.application.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.huajia.os.mac.R;

import java.util.List;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/3
 * @Description:
 */
public class AlbumRecyclerViewAdapter extends RecyclerView.Adapter {
    /**
     * 标题类型
     */
    public static final int VIEW_TYPE_TITLE = 1;

    /**
     * 照片类型
     */
    private static final int VIEW_TYPE_PHOTO = 2;

    private Context context;

    private OnPhotoClickListener onPhotoClickListener;

    private List<AlbumBean> list;

    public AlbumRecyclerViewAdapter(Context context, List<AlbumBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TITLE){
            View view = LayoutInflater.from(context).inflate(R.layout.item_of_album_title, parent, false);
            AlbumTitleViewHolder titleViewHolder = new AlbumTitleViewHolder(view);
            return titleViewHolder;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_of_album_photo, parent, false);
        AlbumPhotoViewHolder photoViewHolder = new AlbumPhotoViewHolder(view);
        return photoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AlbumTitleViewHolder){
            AlbumTitleViewHolder viewHolder = (AlbumTitleViewHolder) holder;
            viewHolder.titleView.setText(list.get(position).getId());
        } else if (holder instanceof AlbumPhotoViewHolder) {
            AlbumPhotoViewHolder viewHolder = (AlbumPhotoViewHolder) holder;
            Glide.with(context)
                    .load(list.get(position).getPhotoUrl())
                    .into(viewHolder.photoView);
            viewHolder.photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPhotoClickListener != null) {
                        onPhotoClickListener.onClick(list.get(position).getPhotoUrl());
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getPhotoUrl() == ""){
            return VIEW_TYPE_TITLE;
        }
        return VIEW_TYPE_PHOTO;
    }

    public void refreshData(List<AlbumBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    class AlbumTitleViewHolder extends RecyclerView.ViewHolder{
        private TextView titleView;

        public AlbumTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.album_title);
        }
    }

    class AlbumPhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView photoView;

        public AlbumPhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.album_photo);
        }
    }

    interface OnPhotoClickListener {
        void onClick(String photoUrl);
    }
}
