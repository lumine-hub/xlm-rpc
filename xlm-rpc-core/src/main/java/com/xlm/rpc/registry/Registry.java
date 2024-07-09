package com.xlm.rpc.registry;


import com.xlm.rpc.config.RegistryConfig;
import com.xlm.rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心要实现的接口
 */
public interface Registry {
    /**
     * 初始化
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *  这里的serviceKey是serviceMetaInfo.getServiceNodeKey()
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测
     */
    void heartBeat();
}
