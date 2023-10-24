package com.huajia.os.mac.utils;

import com.huajia.os.mac.application.BaseApplication;
import com.huajia.os.mac.window.WindowsManager;

/**
 * @author: huajia
 * @date: 2023/10/14 14:52
 */

public class SizeUtils {

    //应用默认宽
    private static final int DEFAULT_WIDTH_APP = WindowsManager.getInstance().getMaxWidthApplication() / 2;

    private static final int DEFAULT_HEIGHT_APP = WindowsManager.getInstance().getMaxHeightApplciation();

    public static int getApplicationWidth(BaseApplication application){
        if (application == null || application.getmSize() == null){
            return DEFAULT_WIDTH_APP;
        }
        return application.getmSize().getWidth();
    }

    public static int getApplicationHeight(BaseApplication application){
        if (application == null || application.getmSize() == null){
            return DEFAULT_HEIGHT_APP;
        }
        return application.getmSize().getHeight();
    }

}
