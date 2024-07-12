package com.xlm.rpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现本地注册中心，当consumer的代理发来请求时，会从这个LocalRegistry获取本地的类，然后通过反射实现方法，
 * 而etcd那个注册中心，是consumer用来找到provider的host和port，进一步找到服务器地址而已。
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
