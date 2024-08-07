package com.huajia.venusdesktop.utils;

import com.huajia.venusdesktop.framework.window.WindowsManager;
import com.huajia.venusdesktop.base.BaseApplication;

/**
 * @author: huajia
 * @date: 2023/10/14 14:52
 */

public class SizeUtils {

    /**
     * app默认宽度
     */
    private static final int DEFAULT_WIDTH_APP = WindowsManager.getInstance().getMaxWidthApplication() / 2;

    /**
     * app默认高度
     */
    private static final int DEFAULT_HEIGHT_APP = WindowsManager.getInstance().getMaxHeightApplication();

    /**
     * 获取app的宽度
     *
     * @param application app
     * @return 宽度
     */
    public static int   getApplicationWidth(BaseApplication application){
        if (application == null || application.getSize() == null){
            return DEFAULT_WIDTH_APP;
        }
        return application.getSize().getWidth();
    }

    /**
     * 获取app的高度
     *
     * @param application app
     * @return 高度
     */
    public static int getApplicationHeight(BaseApplication application){
        if (application == null || application.getSize() == null){
            return DEFAULT_HEIGHT_APP;
        }
        return application.getSize().getHeight();
    }

}
