package com.huajia.venusdesktop.service.application.album;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.huajia.annotation.Route;
import com.huajia.venusdesktop.databinding.ApplicationAlbumBinding;
import com.huajia.venusdesktop.framework.router.TRouterPath;
import com.huajia.venusdesktop.framework.eventbus.EventBusConstants;
import com.huajia.venusdesktop.framework.eventbus.MessageEvent;
import com.huajia.venusdesktop.R;
import com.huajia.venusdesktop.base.BaseApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Route(path = TRouterPath.ALBUM, heightPercent = 1)
public class AlbumApplication extends BaseApplication {
    private static final String TAG = "AlbumApplication";

    private ApplicationAlbumBinding binding;

    private AlbumRecyclerViewAdapter adapter;

    private ImageView ivBack;

    private AlbumFileController albumController;

    public AlbumApplication(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ApplicationAlbumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        findViewById(R.id.close_button).setOnClickListener( view -> {
            dismiss();
        });
        adapter = new AlbumRecyclerViewAdapter(getContext(), albumController.getAlbumList());
        binding.recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == AlbumRecyclerViewAdapter.VIEW_TYPE_TITLE) {
                    return 3;
                }
                return 1;
            }
        });
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        adapter.setOnPhotoClickListener(new AlbumRecyclerViewAdapter.OnPhotoClickListener() {
            @Override
            public void onClick(String photoUrl) {
                previewPhoto(photoUrl);
            }
        });

        ivBack = findViewById(R.id.back_button);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.photoView.setVisibility(View.GONE);
                ivBack.setVisibility(View.GONE);
            }
        });
    }

    private void initData() {
        albumController = new AlbumFileController(getContext());
        scanAlbum();
    }

    private void scanAlbum() {
        albumController.scanAlbum(new AlbumFileController.OnScanAlbumListener() {
            @Override
            public void onSuccess() {
                binding.loadingView.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                adapter.refreshData(albumController.getAlbumList());
            }

            @Override
            public void onError() {

            }
        });
    }

    private void previewPhoto(String photoUrl) {
        ivBack.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);
        binding.photoView.setVisibility(View.VISIBLE);
        Bitmap bitmap = BitmapFactory.decodeFile(photoUrl);
        binding.photoView.setImageBitmap(bitmap);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (EventBusConstants.TAKE_PHOTO_SUCCESS.equals(event.getMessage())) {
            scanAlbum();
        }
    }
}
