package com.huajia.mac.framework.window;

import android.graphics.Point;

import java.util.HashMap;

/**
 * @Author: HuaJ1a
 * @Email: 821759439@qq.com
 * @Date: 2024/5/25
 * @Description: windows启动参数封装
 */
public class WindowsWant {

    /**
     * application启动类型， 通常指 {@link WindowsConstants}
     */
    private WindowsRouter router;

    /**
     * application是否允许移动
     * 1、app类型允许移动
     * 2、功能弹窗不允许移动
     */
    private boolean move;

    /**
     * 指定window启动的坐标
     * 某些功能弹窗需要用到
     */
    private Point coordinate;

    /**
     * 用于初始化application的参数
     */
    private HashMap<Object, Object> params;

    public WindowsWant(WindowsRouter type) {
        this(type, true, null, null);
    }

    public WindowsWant(WindowsRouter type, boolean move, Point coordinate) {
        this(type, move, coordinate, null);
    }

    public WindowsWant(WindowsRouter type, boolean move, Point coordinate, HashMap<Object, Object> params) {
        this.router = type;
        this.move = move;
        this.coordinate = coordinate;
        this.params = params;
    }

    public WindowsRouter getRouter() {
        return router;
    }

    public boolean isMove() {
        return move;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public HashMap<Object, Object> getParams() {
        return params;
    }
}
