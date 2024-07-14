package com.xlm.rpc.server2;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;

/**
 * @author xlm
 * 2024/7/14 13:06
 * 半包粘包测试客户端
 */
public class VertxTcpClient {
    public static void main(String[] args) {
        new VertxTcpClient().start();
//        new VertxTcpClient().start1();
    }
    public void start() {
        Vertx vertx = Vertx.vertx();
        vertx.createNetClient().connect(8849, "localhost", result -> {
           if (result.succeeded()) {
               System.out.println("Connected to TCP server");
               io.vertx.core.net.NetSocket socket = result.result();
               for (int i = 0; i < 1000; i++) {
                   // 发送数据
                   Buffer buffer = Buffer.buffer();
                   String str = "Hello, server!Hello, server!Hello, server!Hello, server!";
                   buffer.appendInt(0);
                   buffer.appendInt(str.getBytes().length);
                   buffer.appendBytes(str.getBytes());
                   socket.write(buffer);
               }
               // 接收响应
               socket.handler(buffer -> {
                   System.out.println("Received response from server: " + buffer.toString());
               });
           } else {
               System.out.println("failed to connect server");
           }
        });
    }

    // 半包粘包演示
    public void start1() {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        vertx.createNetClient().connect(8849, "localhost", result -> {
            if (result.succeeded()) {
                System.out.println("Connected to TCP server");
                io.vertx.core.net.NetSocket socket = result.result();
                for (int i = 0; i < 1000; i++) {
                    // 发送数据
                    socket.write("Hello, server!Hello, server!Hello, server!Hello, server!");
                }
                // 接收响应
                socket.handler(buffer -> {
                    System.out.println("Received response from server: " + buffer.toString());
                });
            } else {
                System.err.println("Failed to connect to TCP server");
            }
        });
    }
}
