package com.xlm.example.consumer;

import com.xlm.example.common.model.User;
import com.xlm.example.common.service.UserService;
import com.xlm.rpc.bootstrap.ConsumerBootstrap;
import com.xlm.rpc.proxy.ServiceProxyFactory;

/**
 * @author xlm
 * @date 2024/7/11 18:51
 */
public class ConsumerProtocol {
    public static void main(String[] args) {
        User user = new User();
        user.setName("xlm");
        ConsumerBootstrap.init();
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User res = userService.getUser(user);
        System.out.println(res);
    }

}
