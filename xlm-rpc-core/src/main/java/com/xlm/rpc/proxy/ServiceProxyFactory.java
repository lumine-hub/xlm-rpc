package com.xlm.rpc.proxy;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（用于创建代理对象）

 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     */
    public static <T> T getProxy(Class<T> cls) {
        return (T)Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[]{cls},
                new ServiceProxy());
    }

}
