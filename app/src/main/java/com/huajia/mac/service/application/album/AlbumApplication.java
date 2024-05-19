package com.huajia.mac.service.application.album;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.huajia.mac.framework.eventbus.EventBusConstants;
import com.huajia.mac.framework.eventbus.MessageEvent;
import com.huajia.os.mac.R;
import com.huajia.mac.base.BaseApplication;

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

public class AlbumApplication extends BaseApplication {
    private static final String TAG = "AlbumApplication";

    /**
     * 相册列表
     */
    private List<AlbumBean> albumList;

    private RecyclerView recyclerView;

    private AlbumRecyclerViewAdapter adapter;

    private PhotoView photoView;

    private ImageView ivBack;

    public AlbumApplication(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_album);
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
        photoView = findViewById(R.id.photo_view);
        albumList = new ArrayList<>();
        scanPhoto();
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new AlbumRecyclerViewAdapter(getContext(), albumList);
        recyclerView.setAdapter(adapter);
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
        recyclerView.setLayoutManager(gridLayoutManager);
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
                recyclerView.setVisibility(View.VISIBLE);
                photoView.setVisibility(View.GONE);
                ivBack.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 扫描所有图片
     */
    private void scanPhoto() {
        File file = new File(getContext().getFilesDir(), "album");
        File[] albumFolder = file.listFiles();
        Arrays.sort(albumFolder, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                if (dateToStamp(file1.getName()) > dateToStamp(file2.getName())) {
                    return -1;
                }
                return 0;
            }
        });
        for (File album : albumFolder) {
            if (album.isDirectory()) {
                // 添加一个空，作为开头
                albumList.add(new AlbumBean(album.getName(), ""));
                File[] photoFolder = album.listFiles();
                // 照片根据时间戳排序
                Arrays.sort(photoFolder, new Comparator<File>() {
                    @Override
                    public int compare(File file1, File file2) {
                        long l1 = Long.parseLong(file1.getName().replace(".jpg", ""));
                        long l2 = Long.parseLong(file2.getName().replace(".jpg", ""));
                        if (l1 > l2) {
                            return -1;
                        }
                        return 0;
                    }
                });
                for (File photo : photoFolder) {
                    // 添加照片
                    albumList.add(new AlbumBean(album.getName(), photo.getAbsolutePath()));
                }
            }
        }
        Log.i(TAG, "albumList" + new Gson().toJson(albumList));
    }

    private void previewPhoto(String photoUrl) {
        ivBack.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        photoView.setVisibility(View.VISIBLE);
        Bitmap bitmap = BitmapFactory.decodeFile(photoUrl);
        photoView.setImageBitmap(bitmap);
    }

    private long dateToStamp(String sDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(sDate);
            return date.getTime();
        } catch (Exception e) {
            Log.i(TAG,e.getMessage());
        }
        return 0L;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (EventBusConstants.TAKE_PHOTO_SUCCESS.equals(event.getMessage())) {
            albumList.clear();
            scanPhoto();
            adapter.refreshData(albumList);
        }
    }
}
