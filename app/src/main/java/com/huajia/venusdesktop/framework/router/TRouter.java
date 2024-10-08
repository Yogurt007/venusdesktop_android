package com.huajia.venusdesktop.framework.router;

import android.content.Context;
import android.graphics.Point;
import com.orhanobut.logger.Logger;
import android.util.Size;

import com.huajia.annotation.RouteMeta;
import com.huajia.venusdesktop.RouterMap;
import com.huajia.venusdesktop.base.BaseApplication;
import com.huajia.venusdesktop.framework.window.WindowsManager;

import java.lang.reflect.Constructor;

/**
 * Description: 路由跳转
 * Author: HuaJ1a
 * Date: 2024/7/14
 */
public class TRouter {

    private static final String TAG = "TRouter";

    private static TRouter instance;

    /**
     * 该context必须为activity的context，创建dialog需要以来activity
     */
    private Context context;

    private TRouterBuilder builder;

    private TRouter() {}

    public static TRouter getInstance() {
        if (instance == null) {
            synchronized (TRouter.class) {
                if (instance == null) {
                    instance = new TRouter();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public TRouter build(String routerPath) {
        builder = new TRouterBuilder();
        builder.routerPath = routerPath;
        return this;
    }

    public TRouter withMove(boolean allowMove) {
        if (this.builder == null) {
            throw new RuntimeException("TRouter: builder is null");
        }
        this.builder.allowMove = allowMove;
        return this;
    }

    public TRouter withCoordinate(Point coordinate) {
        if (this.builder == null) {
            throw new RuntimeException("TRouter: builder is null");
        }
        this.builder.coordinate = coordinate;
        return this;
    }

    public TRouter withProtocol(String key, Object value) {
        if (this.builder == null) {
            throw new RuntimeException("TRouter: builder is null");
        }
        if (this.builder.protocol == null) {
            this.builder.protocol = new TRouterProtocol();
        }
        this.builder.protocol.put(key, value);
        return this;
    }

    public void navigation() {
        if (builder == null) {
            Logger.i(TAG, "builder is null");
            return;
        }

        RouteMeta routeMeta = RouterMap.map.get(builder.routerPath);
        if (routeMeta == null) {
            return;
        }
        String className = routeMeta.getClassName();
        String packageName = routeMeta.getPackageName();
        try {
            // 反射创建application对象，实现解耦
            Class<?> applicationClass = Class.forName(String.format("%s.%s", packageName, className));
            Object object = null;
            if (this.builder.protocol == null) {
                Constructor<?> constructor = applicationClass.getConstructor(Context.class);
                object = constructor.newInstance(this.context);
            } else {
                Constructor<?> constructor = applicationClass.getConstructor(Context.class, TRouterProtocol.class);
                object = constructor.newInstance(this.context, this.builder.protocol);
            }

            if (!(object instanceof BaseApplication)) {
                Logger.i(TAG, "object not instanceof BaseApplication,name :" + object.getClass().getName());
                return;
            }
            BaseApplication application = (BaseApplication) object;
            Size size = new Size(
                    (int) (WindowsManager.getInstance().getMaxWidthApplication() * routeMeta.getWidthPercent()),
                    (int) (WindowsManager.getInstance().getMaxHeightApplication() * routeMeta.getHeightPercent())
            );
            application.setSize(size);
            WindowsManager.getInstance().openWindow(this.builder, application);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 重置builder，避免下次启动有干扰
            this.builder = null;
        }
    }

    public class TRouterBuilder {

        private String routerPath;

        /**
         * 允许移动
         */
        private boolean allowMove = true;

        /**
         * 左上角坐标
         */
        private Point coordinate;

        /**
         * 协议
         */
        private TRouterProtocol protocol;

        public String getRouterPath() {
            return routerPath;
        }

        public boolean isAllowMove() {
            return allowMove;
        }

        public Point getCoordinate() {
            return coordinate;
        }
    }

}
