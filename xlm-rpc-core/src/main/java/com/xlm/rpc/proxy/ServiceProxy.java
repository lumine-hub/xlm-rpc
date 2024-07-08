package com.xlm.rpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xlm.rpc.RpcApplication;
import com.xlm.rpc.config.RegistryConfig;
import com.xlm.rpc.config.RpcConfig;
import com.xlm.rpc.constant.RpcConstant;
import com.xlm.rpc.model.RpcRequest;
import com.xlm.rpc.model.RpcResponse;
import com.xlm.rpc.model.ServiceMetaInfo;
import com.xlm.rpc.registry.Registry;
import com.xlm.rpc.registry.RegistryFactory;
import com.xlm.rpc.serializer.JdkSerializer;
import com.xlm.rpc.serializer.Serializer;
import com.xlm.rpc.serializer.SerializerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 服务代理（JDK 动态代理）
 *
 */
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//        Serializer serializer = new JdkSerializer();
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(method.getDeclaringClass().getName());
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (serviceMetaInfoList.isEmpty()) {
            throw new RuntimeException("暂无服务地址");
        }
        ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);


        HttpResponse response = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                .body(serializer.serialize(rpcRequest))
                .execute();
        RpcResponse result = serializer.deserialize(response.bodyBytes(), RpcResponse.class);

        return result.getData();
    }
}
