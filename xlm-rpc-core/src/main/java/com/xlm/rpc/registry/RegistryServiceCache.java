package com.xlm.rpc.registry;

import com.xlm.rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 维护一个本地缓存
 */
public class RegistryServiceCache {

    private List<ServiceMetaInfo> serviceCache;
    /**
     * 写缓存
     */
    void writeCache(List<ServiceMetaInfo> newServiceCache){
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     */
    List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    void clearCache() {
        this.serviceCache = null;
    }
}
