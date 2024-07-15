package com.xlm.example.provider;

import com.xlm.example.common.service.UserService;
import com.xlm.rpc.bootstrap.ProviderBootstrap;
import com.xlm.rpc.bootstrap.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xlm
 * 2024/7/15 下午3:18
 */
public class ProviderBootstrapTest {
    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
