package com.xlm.example.provider;

import com.xlm.example.common.service.UserService;
import com.xlm.rpc.RpcApplication;
import com.xlm.rpc.registry.LocalRegistry;
import com.xlm.rpc.server.HttpServer;
import com.xlm.rpc.server.VertxHttpServer;

public class Provider {

    public static void main(String[] args) {
        RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doPost(RpcApplication.getRpcConfig().getServerPort());
    }
}