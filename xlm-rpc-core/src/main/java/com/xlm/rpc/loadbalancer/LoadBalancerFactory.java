package com.xlm.rpc.loadbalancer;

import com.xlm.rpc.spi.SpiLoader;

/**
 * @author xlm
 * 2024/7/14 下午3:54
 */
public class LoadBalancerFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOADBALANCER = new RandomLoadBalancer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }

}
