package com.xlm.rpc.server;


import cn.hutool.log.Log;
import com.xlm.rpc.model.RpcRequest;
import com.xlm.rpc.model.RpcResponse;
import com.xlm.rpc.registry.LocalRegistry;
import com.xlm.rpc.serializer.JdkSerializer;
import com.xlm.rpc.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;


public class HttpServerHandler implements Handler<HttpServerRequest>  {

    @Override
    public void handle(HttpServerRequest request) {
        final Serializer serializer = new JdkSerializer();
        System.out.println("Received request: " + request.method() + " " + request.uri());

        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // rpcRequest为空，直接返回
            RpcResponse rpcResponse = new RpcResponse();
            if (rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(request, rpcResponse, serializer);
                return;
            }
            // rpcRequest不为空，通过反射获取
            String serviceName = rpcRequest.getServiceName();
            Class<?> cls = LocalRegistry.get(serviceName);
            try {
                Method method = cls.getDeclaredMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(cls.newInstance(), rpcRequest.getArgs());
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("获取数据成功!");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setData(null);
                rpcResponse.setMessage("获取数据失败!");
            }
            doResponse(request, rpcResponse, serializer);
        });
    }

    /**
     * 响应
     *
     * @param request
     * @param rpcResponse
     * @param serializer
     */
    void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("content-type", "application/json");
        try {
            // 序列化
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }

}
