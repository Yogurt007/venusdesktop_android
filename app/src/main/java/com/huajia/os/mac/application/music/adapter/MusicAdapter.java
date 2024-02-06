package com.huajia.os.mac.application.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;
import com.huajia.os.mac.application.music.MusicApplication;
import com.huajia.os.mac.application.music.bean.MusicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: huajia
 * @date: 2023/10/14 16:47
 */

public class MusicAdapter extends RecyclerView.Adapter {

    private List<MusicBean> list = new ArrayList<>();

    private Context mContext;

    private MusicApplication.onMusicListener mListener;

    public MusicAdapter(Context context,List<MusicBean> list){
        this.mContext = context;
        this.list = list;
    }

    public void setmListener(MusicApplication.onMusicListener listener){
        this.mListener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_of_music_list,null,false);
        MusicHolder holder = new MusicHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MusicHolder){
            MusicHolder musicHolder = (MusicHolder) holder;
            musicHolder.mImageView.setImageDrawable(list.get(position).getmImage());
            musicHolder.mSongNameView.setText(list.get(position).getmSongName());
            musicHolder.mSingerView.setText(list.get(position).getmSinger());
            musicHolder.itemView.setOnClickListener(view -> {
                mListener.onClick(position);
            });
            if (position == list.size() - 1){
                musicHolder.mLineView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;

        private TextView mSongNameView;

        private TextView mSingerView;

        private View mLineView;
        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.song_image);
            mSongNameView = itemView.findViewById(R.id.song_name);
            mSingerView = itemView.findViewById(R.id.singer);
            mLineView = itemView.findViewById(R.id.line_view);
        }
    }



}
