package com.xlm.rpc.loadbalancer;

import com.xlm.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author xlm
 * 2024/7/14 下午3:34
 */
public class ConsistentHashLoadBalancer implements LoadBalancer{

    private static final TreeMap<Integer, ServiceMetaInfo> hash = new TreeMap<>();

    private static final int VIRTUAL_NODE_NUM = 100;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfos) {

        if (serviceMetaInfos.isEmpty()) {
            return null;
        }
        for (ServiceMetaInfo serviceMetaInfo : serviceMetaInfos) {
            for (int i =0; i < VIRTUAL_NODE_NUM; i++) {
                int key = (serviceMetaInfo.getServiceKey() + "&" + i).hashCode();
                hash.put(key,serviceMetaInfo);
            }
        }

        int key = requestParams.hashCode();
        Map.Entry<Integer, ServiceMetaInfo> entry = hash.ceilingEntry(key);
        if (entry == null) {
            entry = hash.firstEntry();
        }
        return entry.getValue();
    }
}
