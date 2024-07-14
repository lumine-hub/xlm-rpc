package com.xlm.rpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xlm.rpc.RpcApplication;
import com.xlm.rpc.config.RpcConfig;
import com.xlm.rpc.constant.RpcConstant;
import com.xlm.rpc.loadbalancer.LoadBalancer;
import com.xlm.rpc.loadbalancer.LoadBalancerFactory;
import com.xlm.rpc.model.RpcRequest;
import com.xlm.rpc.model.RpcResponse;
import com.xlm.rpc.model.ServiceMetaInfo;
import com.xlm.rpc.registry.Registry;
import com.xlm.rpc.registry.RegistryFactory;
import com.xlm.rpc.serializer.Serializer;
import com.xlm.rpc.serializer.SerializerFactory;
import com.xlm.rpc.server.tcp.VertxTcpClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // 添加负载均衡
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        // 将调用方法名（请求路径）作为负载均衡参数
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("methodName", method.getName());
        ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
//        ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);
        // 发送 TCP 请求
        RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
        return rpcResponse.getData();

        // 原来的使用http请求
//        HttpResponse response = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
//                .body(serializer.serialize(rpcRequest))
//                .execute();
//        RpcResponse result = serializer.deserialize(response.bodyBytes(), RpcResponse.class);

        // 使用tcp请求
//        Vertx vertx = Vertx.vertx();
//        NetClient netClient = vertx.createNetClient();
//        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
//        netClient.connect(selectedServiceMetaInfo.getServicePort(), selectedServiceMetaInfo.getServiceHost(),
//                result -> {
//                    if (result.succeeded()) {
//                        System.out.println("Connected to TCP server");
//                        io.vertx.core.net.NetSocket socket = result.result();
//                        // 发送数据
//                        // 构造消息
//                        ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
//                        ProtocolMessage.Header header = new ProtocolMessage.Header();
//                        header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
//                        header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
//                        header.setSerializer((byte) ProtocolMessageSerializerEnum.getEnumByValue(RpcApplication.getRpcConfig().getSerializer()).getKey());
//                        header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
//                        header.setRequestId(IdUtil.getSnowflakeNextId());
//                        protocolMessage.setHeader(header);
//                        protocolMessage.setBody(rpcRequest);
//                        // 编码请求
//                        try {
//                            Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
//                            socket.write(encodeBuffer);
//                        } catch (IOException e) {
//                            throw new RuntimeException("协议消息编码错误");
//                        }
//
//                        // 接收响应
//                        socket.handler(buffer -> {
//                            try {
//                                ProtocolMessage<RpcResponse> rpcResponseProtocolMessage = (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
//                                responseFuture.complete(rpcResponseProtocolMessage.getBody());
//                            } catch (IOException e) {
//                                throw new RuntimeException("协议消息解码错误");
//                            }
//                        });
//                    } else {
//                        System.out.println("Failed to connect to TCP serve");
//                    }
//                });
//        RpcResponse rpcResponse = responseFuture.get();
//        netClient.close();
//        return rpcResponse.getData();
    }


    private static RpcResponse doHttpRequest(ServiceMetaInfo selectedServiceMetaInfo, byte[] bodyBytes) throws IOException {
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        // 发送 HTTP 请求
        try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                .body(bodyBytes)
                .execute()) {
            byte[] result = httpResponse.bodyBytes();
            // 反序列化
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse;
        }
    }
}
