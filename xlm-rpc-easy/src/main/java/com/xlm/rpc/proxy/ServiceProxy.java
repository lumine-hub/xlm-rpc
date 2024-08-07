package com.xlm.rpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xlm.rpc.model.RpcRequest;
import com.xlm.rpc.model.RpcResponse;
import com.xlm.rpc.serializer.JdkSerializer;
import com.xlm.rpc.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务代理（JDK 动态代理）
 *
 */
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Serializer serializer = new JdkSerializer();

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        HttpResponse response = HttpRequest.post("http://localhost:8080")
                .body(serializer.serialize(rpcRequest))
                .execute();
        RpcResponse result = serializer.deserialize(response.bodyBytes(), RpcResponse.class);

        return result.getData();
    }
}
