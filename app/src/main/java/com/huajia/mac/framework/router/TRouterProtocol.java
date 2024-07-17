package com.huajia.mac.framework.router;

import java.util.HashMap;

/**
 * Description: 携带数据给application
 * Author: HuaJ1a
 * Date: 2024/7/17
 */
public class TRouterProtocol {

    private HashMap<String, Object> data;

    public TRouterProtocol() {
        data = new HashMap<>();
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public Object get(String key, Object defaultValue) {
        if (!data.containsKey(key)) {
            return defaultValue;
        }
        return data.get(key);
    }

}
