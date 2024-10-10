package com.huajia.venusdesktop.service.application.album;

import android.content.Context;
import android.os.Handler;
import com.orhanobut.logger.Logger;

import com.huajia.venusdesktop.framework.task.TaskQueue;
import com.huajia.venusdesktop.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Description: 相册图片文件管理类
 *
 * @author tanhja
 * @date: 2024/10/10
 */
public class AlbumFileController {
    private static final String TAG = "AlbumFileController";

    private Context context;

    /**
     * 相册列表 - 1、图片 2、视频
     */
    private List<AlbumBean> albumList = new ArrayList<>();

    private Handler handler = new Handler();

    public AlbumFileController(Context context) {
        this.context = context;
    }

    /**
     * 扫描相册
     *
     * @param listener 监听
     */
    public void scanAlbum(OnScanAlbumListener listener) {
        TaskQueue.dispatch(() -> {
            try {
                // todo 需要改成局部刷新提高性能
                albumList.clear();
                File file = new File(context.getFilesDir(), "album");
                File[] albumFolder = file.listFiles();
                if (albumFolder == null) {
                    return;
                }
                Arrays.sort(albumFolder, new Comparator<File>() {
                    @Override
                    public int compare(File file1, File file2) {
                        if (TimeUtils.dateToStamp(file1.getName()) > TimeUtils.dateToStamp(file2.getName())) {
                            return -1;
                        }
                        return 0;
                    }
                });
                for (File album : albumFolder) {
                    if (album.isDirectory()) {
                        // 添加一个空，作为开头
                        albumList.add(new AlbumBean(album.getName(), ""));
                        File[] fileFolder = album.listFiles();
                        // 照片根据时间戳排序
                        Arrays.sort(fileFolder, new Comparator<File>() {
                            @Override
                            public int compare(File file1, File file2) {
                                long l1 = Long.parseLong(file1.getName().
                                        substring(0, file1.getName().indexOf(".")));
                                long l2 = Long.parseLong(file2.getName().
                                        substring(0, file2.getName().indexOf(".")));
                                Logger.i(TAG, String.valueOf(l1));
                                if (l1 > l2) {
                                    return -1;
                                }
                                return 0;
                            }
                        });
                        for (File albumFile : fileFolder) {
                            // 添加照片
                            AlbumBean albumBean = new AlbumBean();
                            albumBean.setTime(albumFile.getName());
                            albumBean.setFileUrl(albumFile.getAbsolutePath());
                            albumBean.setType(albumFile.getName()
                                    .substring(albumFile.getName().indexOf(".") + 1));
                            albumList.add(albumBean);
                        }
                    }
                }
                // 主线程回调
                handler.post(() -> {
                    listener.onSuccess();
                });
            } catch (Exception e) {
                Logger.i(TAG, "scanAlbum error, " + e.getMessage());
                handler.post(() -> {
                    listener.onError();
                });
            }
        });
    }

    public List<AlbumBean> getAlbumList() {
        return albumList;
    }

    interface OnScanAlbumListener {
        void onSuccess();

        void onError();
    }


}
