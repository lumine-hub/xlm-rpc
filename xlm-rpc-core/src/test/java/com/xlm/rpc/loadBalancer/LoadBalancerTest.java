package com.xlm.rpc.loadBalancer;

import com.xlm.rpc.loadbalancer.LoadBalancer;
import com.xlm.rpc.loadbalancer.RandomLoadBalancer;
import com.xlm.rpc.model.ServiceMetaInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xlm
 * 2024/7/14 下午4:23
 */
public class LoadBalancerTest {
    private final static LoadBalancer loadBalancer = new RandomLoadBalancer();

    @Test
    public void test() {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("name", "xlm");
        requestParams.put("methodName", "apple");
        // service list
        ServiceMetaInfo si = new ServiceMetaInfo();
        si.setServiceName("apple");
        si.setServicePort(8081);
        ServiceMetaInfo si1 = new ServiceMetaInfo();
        si1.setServiceName("apple2");
        si1.setServicePort(8082);
        ServiceMetaInfo si2 = new ServiceMetaInfo();
        si2.setServiceName("apple3");
        si2.setServicePort(8083);
        ArrayList<ServiceMetaInfo> serviceMetaInfos = new ArrayList<>();
        serviceMetaInfos.add(si);
        serviceMetaInfos.add(si1);
        serviceMetaInfos.add(si2);
        // 发送请求
        for (int i= 0; i < 5;i++ ) {
            ServiceMetaInfo serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfos);
            System.out.println(serviceMetaInfo.getServicePort());
        }
    }
}
