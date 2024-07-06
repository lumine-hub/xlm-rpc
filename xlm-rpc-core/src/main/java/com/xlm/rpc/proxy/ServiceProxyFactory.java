package com.xlm.rpc.proxy;

import com.xlm.rpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（用于创建代理对象）

 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     */
    public static <T> T getProxy(Class<T> cls) {
        if (RpcApplication.getRpcConfig().isMock()) {
            return getMockProxy(cls);
        }
        return (T)Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[]{cls},
                new ServiceProxy());
    }

    /**
     * 根据服务类获取 Mock 代理对象
     */
    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy());
    }

}
