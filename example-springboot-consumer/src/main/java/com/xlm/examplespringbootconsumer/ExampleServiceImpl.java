package com.xlm.examplespringbootconsumer;

import com.xlm.example.common.model.User;
import com.xlm.example.common.service.UserService;
import com.xlm.rpc.springbootstarter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * @author xlm
 * 2024/7/15 下午8:27
 */

@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("xlm");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
