package com.xlm.example.provider;

import com.xlm.example.common.service.UserService;
import com.xlm.rpc.RpcApplication;
import com.xlm.rpc.model.ServiceMetaInfo;
import com.xlm.rpc.registry.LocalRegistry;
import com.xlm.rpc.server.tcp.VertxTcpServer;

/**
 * @author xlm
 * @date 2024/7/11 18:21
 * 测试自定义协议功能
 */
public class ProviderProtocol {
    public static void main(String[] args) throws Exception {
        // RPC框架初始化
        RpcApplication.init();
        // 注册到本地
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 注册到注册中心
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(UserService.class.getName());
        serviceMetaInfo.setServiceHost(RpcApplication.getRpcConfig().getServerHost());
        serviceMetaInfo.setServicePort(RpcApplication.getRpcConfig().getServerPort());

        RpcApplication.getRegistry().register(serviceMetaInfo);

        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

    }
}
