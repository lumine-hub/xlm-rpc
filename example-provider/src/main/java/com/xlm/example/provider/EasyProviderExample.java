package com.xlm.example.provider;

import com.xlm.example.common.service.UserService;
import com.xlm.rpc.registry.LocalRegistry;
import com.xlm.rpc.server.HttpServer;
import com.xlm.rpc.server.VertxHttpServer;

public class EasyProviderExample {

    public static void main(String[] args) {
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 提供服务
        HttpServer server = new VertxHttpServer();
        server.doPost(8080);
    }
}