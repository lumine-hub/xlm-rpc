package com.xlm.rpc.registry;


import com.xlm.rpc.config.RegistryConfig;
import com.xlm.rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心要实现的接口
 */
public interface Registry {
    /**
     * 初始化 `consumer`和`provider`都需要
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务 `provider`
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务 `provider`
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *  这里的serviceKey是serviceMetaInfo.getServiceNodeKey()
     *  `consumer`端进行服务发现，主要是调用远处服务时，进行发现，其实现是先查询缓存，在查询注册中心。
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * `provider`的jvm退出时，会遍历localRegisterNodeKeySet里面的key
     * 然后删除etcd里面的东西。这时候其实etcd也会发送delete到 `consumer`的
     * watch函数，不过这个过程时etcd实现的，跟我们没关系。
     */
    void destroy();

    /**
     * 心跳检测：`provider`端去进行心跳检测，因为regiter维护一个localRegisterNodeKeySet，
     * 这里面放了`provider`注册了那些服务，心跳检测就会定时遍历这个set，如果未过期，就会给
     * 这里的服务进行续期。
     */
    void heartBeat();

    /**
     * 监听: `consumer`端去监听etcd中的服务是否下线，下线了更新`consumer`端的
     * 本地缓存registryServiceCache，这里存放了consumer发送请求时不用每次去注册中心查询了，可以先
     * 从这个缓存查询。
     * @param serviceNodeKey
     */
    void watch(String serviceNodeKey);

}
