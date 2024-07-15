package com.xlm.examplespringbootprovider.service;

import com.xlm.example.common.model.User;
import com.xlm.example.common.service.UserService;
import com.xlm.rpc.springbootstarter.annotation.RpcService;

/**
 * @author xlm
 * 2024/7/15 下午8:23
 */

@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("调用的用户名为：" + user.getName());
        return user;
    }
}
