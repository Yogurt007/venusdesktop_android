package com.huajia.mac.framework.application;

import static com.huajia.mac.framework.window.WindowsRouter.CameraApplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.annotation.NonNull;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.huajia.mac.framework.task.TaskQueue;
import com.huajia.mac.framework.window.WindowsConstants;
import com.huajia.mac.framework.window.WindowsManager;
import com.huajia.mac.framework.window.WindowsRouter;
import com.huajia.mac.framework.window.WindowsWant;
import com.huajia.mac.service.ui.desktop.AppDesktopActivity;
import com.huajia.mac.service.ui.desktop.bean.LocalAppBean;
import com.huajia.mac.utils.ToastUtils;
import com.huajia.os.mac.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: HuaJ1a
 * Date: 2024/6/30
 */
public class ApplicationManager {

    private static ApplicationManager instance;

    private List<ApplicationBean> appList = new ArrayList<>();

    private List<LocalAppBean> localAppList = new ArrayList<>();

    private ApplicationManager() {
    }

    public static ApplicationManager getInstance() {
        if (instance == null) {
            synchronized (ApplicationManager.class) {
                if (instance == null) {
                    instance = new ApplicationManager();
                }
            }
        }
        return ApplicationManager.instance;
    }

    public void init(Context context) {
        // 预置应用
        appList.add(new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL, context.getDrawable(R.drawable.icon_app_desktop), WindowsRouter.AppDesktop));
        appList.add(new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL, context.getDrawable(R.drawable.icon_camera), CameraApplication));
        appList.add(new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL, context.getDrawable(R.drawable.icon_music), WindowsRouter.MusicApplication));
        appList.add(new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL, context.getDrawable(R.drawable.icon_album), WindowsRouter.AlbumApplication));
        appList.add(new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL, context.getDrawable(R.drawable.icon_draw), WindowsRouter.DrawApplication));
        appList.add(new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL, context.getDrawable(R.drawable.icon_guitar), WindowsRouter.GuitarApplication));
        appList.add(new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL, context.getDrawable(R.drawable.icon_tang_poem), WindowsRouter.TangPoemApplication));

        // 获取本地应用列表
        TaskQueue.dispatch(new Runnable() {
            @Override
            public void run() {
                PackageManager packageManager = context.getPackageManager();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
                for (ResolveInfo resolveInfo : resolveInfoList) {
                    localAppList.add(new LocalAppBean(
                            (String) resolveInfo.activityInfo.loadLabel(packageManager),
                            resolveInfo.activityInfo.loadIcon(packageManager),
                            resolveInfo.activityInfo.packageName));
                }
            }
        });
    }

    public List<ApplicationBean> getAppList() {
        return appList;
    }

    public void openApplication(Context context, ApplicationBean applicationBean) {
        WindowsRouter router = applicationBean.getRouter();
        switch (router) {
            case AppDesktop:
                Intent intent = new Intent(context, AppDesktopActivity.class);
                context.startActivity(intent);
                break;
            case CameraApplication:
                XXPermissions.with(context)
                        .permission(Permission.CAMERA)
                        .request(new OnPermissionCallback() {
                            @Override
                            public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                                if (!allGranted) {
                                    return;
                                }
                                WindowsManager.getInstance().openWindow(WindowsRouter.CameraApplication);
                            }

                            @Override
                            public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                                ToastUtils.show(context, "请打开照相机权限");
                            }
                        });
                break;
            case MusicApplication:
                WindowsManager.getInstance().openWindow(WindowsRouter.MusicApplication);
                break;
            case AlbumApplication:
                WindowsManager.getInstance().openWindow(WindowsRouter.AlbumApplication);
                break;
            case DrawApplication:
                WindowsManager.getInstance().openWindow(WindowsRouter.DrawApplication);
                break;
            case GuitarApplication:
                XXPermissions.with(context)
                        .permission(Permission.RECORD_AUDIO)
                        .request(new OnPermissionCallback() {
                            @Override
                            public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                                if (!allGranted) {
                                    return;
                                }
                                WindowsManager.getInstance().openWindow(WindowsRouter.GuitarApplication);
                            }

                            @Override
                            public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                                ToastUtils.show(context, "请打开麦克风权限");
                            }
                        });
                break;
            case TangPoemApplication:
                WindowsManager.getInstance().openWindow(WindowsRouter.TangPoemApplication);
                break;
        }
    }

    public List<LocalAppBean> getLocalAppList() {
        return localAppList;
    }
}
