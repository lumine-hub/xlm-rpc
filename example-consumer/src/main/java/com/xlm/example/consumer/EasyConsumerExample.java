package com.xlm.example.consumer;

import com.xlm.example.common.model.User;
import com.xlm.example.common.service.UserService;
import com.xlm.rpc.proxy.ServiceProxyFactory;


public class EasyConsumerExample {

    public static void main(String[] args) {
        User user = new User();
        user.setName("xlm");
        // 调用
//        UserServiceProxy proxy = new  UserServiceProxy(); // 静态代理
//        User newUser = proxy.getUser(user);
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        if (userService != null) {
            System.out.println(userService.getUser(user));
        } else {
            System.out.println("user == null");
        }
    }
}