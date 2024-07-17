package com.huajia.mac.framework.application;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.annotation.NonNull;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.huajia.mac.framework.router.TRouter;
import com.huajia.mac.framework.router.TRouterPath;
import com.huajia.mac.framework.task.TaskQueue;
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
        appList.add(
                new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL,
                        context.getDrawable(R.drawable.icon_app_desktop), TRouterPath.DESKTOP));
        appList.add(
                new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL,
                        context.getDrawable(R.drawable.icon_camera), TRouterPath.CAMERA));
        appList.add(
                new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL,
                        context.getDrawable(R.drawable.icon_music), TRouterPath.MUSIC));
        appList.add(
                new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL,
                        context.getDrawable(R.drawable.icon_album), TRouterPath.ALBUM));
        appList.add(
                new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL,
                        context.getDrawable(R.drawable.icon_draw), TRouterPath.DRAW));
        appList.add(
                new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL,
                        context.getDrawable(R.drawable.icon_guitar), TRouterPath.GUITAR));
        appList.add(
                new ApplicationBean(ApplicationConstants.TYPE_APP_LOCAL,
                        context.getDrawable(R.drawable.icon_tang_poem), TRouterPath.TANGPOEM));

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
        String routerPath = applicationBean.getRouterPath();
        switch (routerPath) {
            case TRouterPath.DESKTOP:
                Intent intent = new Intent(context, AppDesktopActivity.class);
                context.startActivity(intent);
                break;
            case TRouterPath.CAMERA:
                XXPermissions.with(context)
                        .permission(Permission.CAMERA)
                        .request(new OnPermissionCallback() {
                            @Override
                            public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                                if (!allGranted) {
                                    return;
                                }
                                TRouter.getInstance().build(TRouterPath.CAMERA).navigation();
                            }

                            @Override
                            public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                                ToastUtils.show(context, "请打开照相机权限");
                            }
                        });
                break;
            case TRouterPath.MUSIC:
                TRouter.getInstance().build(TRouterPath.MUSIC).navigation();
                break;
            case TRouterPath.ALBUM:
                TRouter.getInstance().build(TRouterPath.ALBUM).navigation();
                break;
            case TRouterPath.DRAW:
                TRouter.getInstance().build(TRouterPath.DRAW).navigation();
                break;
            case TRouterPath.GUITAR:
                XXPermissions.with(context)
                        .permission(Permission.RECORD_AUDIO)
                        .request(new OnPermissionCallback() {
                            @Override
                            public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                                if (!allGranted) {
                                    return;
                                }
                                TRouter.getInstance().build(TRouterPath.GUITAR).navigation();
                            }

                            @Override
                            public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                                ToastUtils.show(context, "请打开麦克风权限");
                            }
                        });
                break;
            case TRouterPath.TANGPOEM:
                TRouter.getInstance().build(TRouterPath.TANGPOEM).navigation();
                break;
        }
    }

    public List<LocalAppBean> getLocalAppList() {
        return localAppList;
    }
}
