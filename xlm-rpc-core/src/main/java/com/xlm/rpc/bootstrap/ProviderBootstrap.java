package com.xlm.rpc.bootstrap;

import com.xlm.rpc.RpcApplication;
import com.xlm.rpc.config.RegistryConfig;
import com.xlm.rpc.config.RpcConfig;
import com.xlm.rpc.model.ServiceMetaInfo;
import com.xlm.rpc.registry.Registry;
import com.xlm.rpc.registry.RegistryFactory;
import com.xlm.rpc.server.HttpServer;
import com.xlm.rpc.server.VertxHttpServer;

import java.util.List;

/**
 * @author xlm
 * 2024/7/15 下午3:12
 */
public class ProviderBootstrap {
    public static void init(List<ServiceRegisterInfo> serviceRegisterInfos) {
        // 初始化rpc项目，主要包含rpcConfig和register的初始化
        RpcApplication.init();
        // 获取rpcConfig
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfos) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doPost(RpcApplication.getRpcConfig().getServerPort());
        System.out.println("启动成功...");
    }
}
