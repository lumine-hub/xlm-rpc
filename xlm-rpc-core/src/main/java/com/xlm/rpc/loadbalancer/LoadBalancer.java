package com.xlm.rpc.loadbalancer;

import com.xlm.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @author xlm
 * 2024/7/14 15:15
 */
public interface LoadBalancer {
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfos);
}
