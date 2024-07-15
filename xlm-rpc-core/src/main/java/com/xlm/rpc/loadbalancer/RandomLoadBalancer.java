package com.xlm.rpc.loadbalancer;

import com.xlm.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author xlm
 * 2024/7/14 下午3:30
 */
public class RandomLoadBalancer implements LoadBalancer {
    private Random random = new Random();
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfos) {
        if (serviceMetaInfos == null || serviceMetaInfos.isEmpty()) {
            return null;
        }
        if (serviceMetaInfos.size() == 1) {
            return serviceMetaInfos.get(0);
        }
        int index = random.nextInt(serviceMetaInfos.size());
        return serviceMetaInfos.get(index);
    }
}
