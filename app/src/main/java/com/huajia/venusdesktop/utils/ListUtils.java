package com.huajia.venusdesktop.utils;

import java.util.List;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/4/6
 * @Description:
 */
public class ListUtils {

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty() || list.size() <= 0;
    }

}
