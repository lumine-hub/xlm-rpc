package com.xlm.example.provider;

import com.xlm.example.common.service.UserService;
import com.xlm.rpc.RpcApplication;
import com.xlm.rpc.config.RegistryConfig;
import com.xlm.rpc.config.RpcConfig;
import com.xlm.rpc.model.ServiceMetaInfo;
import com.xlm.rpc.registry.LocalRegistry;
import com.xlm.rpc.registry.Registry;
import com.xlm.rpc.registry.RegistryFactory;
import com.xlm.rpc.server.HttpServer;
import com.xlm.rpc.server.VertxHttpServer;

public class Provider {

    public static void main(String[] args) throws Exception {
        RpcApplication.init();

        // 本地注册服务
//        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(UserService.class.getName());
        registry.register(serviceMetaInfo);
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doPost(RpcApplication.getRpcConfig().getServerPort());
        System.out.println("启动成功...");
    }
}