package com.xlm.rpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现本地注册中心
 */
public class LocalRegistry {
    private static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    public static void register(String serviceName, Class<?> implClass) {
        map.put(serviceName, implClass);
    }

    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    public static void remove(String serviceName) {
        map.remove(serviceName);
    }
}
