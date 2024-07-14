package com.xlm.rpc.loadbalancer;

import com.xlm.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xlm
 * 2024/7/14 15:18
 * 轮询方法实现负载均衡
 */
public class RoundRobinLoadBalancer implements LoadBalancer {

    private final AtomicInteger currentIndex = new AtomicInteger(0);

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfos) {
        if (serviceMetaInfos == null || serviceMetaInfos.isEmpty()) {
            return null;
        }
        if (serviceMetaInfos.size() == 1) {
            return serviceMetaInfos.get(0);
        }
        int index = currentIndex.getAndIncrement() % serviceMetaInfos.size();

        return serviceMetaInfos.get(index);
    }

}
