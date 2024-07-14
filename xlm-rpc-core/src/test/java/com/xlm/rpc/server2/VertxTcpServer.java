package com.xlm.rpc.server2;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;
import lombok.extern.slf4j.Slf4j;


/**
 * @author xlm
 * 2024/7/14 12:35
 * 半包粘包测试服务端
 */
@Slf4j
public class VertxTcpServer implements HttpServer{
    private byte[] handleRequest(byte[] requestData) {
        return "Hello, client!".getBytes();
    }
    @Override
    public void doPost(int port) {
        Vertx vertx = Vertx.vertx();
        NetServer server = vertx.createNetServer();
        // 处理连接请求
        server.connectHandler(socket -> {
           // 构造parser
            RecordParser parser = RecordParser.newFixed(8); // 请求头的长度是固定的
            parser.setOutput(new Handler<Buffer>() {
                int size = -1;
                // 一次完整的读取（请求头 + 请求题）
                Buffer resultBuffer = Buffer.buffer();
                @Override
                public void handle(Buffer buffer) {
                    if (size == -1) {
                        size = buffer.getInt(4); // 现获取请求头的长度
                        parser.fixedSizeMode(size);
                        // 写入请求头到结果
                        resultBuffer.appendBuffer(buffer);
                    } else {
                        // 写入体到结果
                        resultBuffer.appendBuffer(buffer);
                        //重置下一轮
                        parser.fixedSizeMode(8);
                        size = -1;
                        resultBuffer = Buffer.buffer();
                    }
                }
            });
            socket.handler(parser);
        });
        server.listen(port, result -> {
            if(result.succeeded()) {
                System.out.println("TCP server started on port " + port);
            } else {
                System.err.println("Failed to start TCP server: "+ result.cause());
            }
        });
    }

    // 半包粘包示例

    public void doPost1(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 TCP 服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
//        server.connectHandler(new TcpServerHandler());
        server.connectHandler(socket -> {
            socket.handler(buffer -> {
                String testMessage = "Hello, server!Hello, server!Hello, server!Hello, server!";
                int messageLength = testMessage.getBytes().length;
                if (buffer.getBytes().length < messageLength) {
                    System.out.println("半包, length = " + buffer.getBytes().length);
                    return;
                }
                if (buffer.getBytes().length > messageLength) {
                    System.out.println("粘包, length = " + buffer.getBytes().length);
                    return;
                }
                String str = new String(buffer.getBytes(0, messageLength));
                System.out.println(str);
                if (testMessage.equals(str)) {
                    System.out.println("good");
                }
            });
        });

        // 启动 TCP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                log.info("TCP server started on port " + port);
            } else {
                log.info("Failed to start TCP server: " + result.cause());
            }
        });
    }

    // 初步解决半包粘包（每次读取固定长度）

    public void doPost2(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 TCP 服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
//        server.connectHandler(new TcpServerHandler());
        server.connectHandler(socket -> {
            String testMessage = "Hello, server!Hello, server!Hello, server!Hello, server!";
            int messageLength = testMessage.getBytes().length;

            // 构造parser
            RecordParser parser = RecordParser.newFixed(messageLength);
            parser.setOutput(new Handler<Buffer>() {

                @Override
                public void handle(Buffer buffer) {
                    String str = new String(buffer.getBytes());
                    System.out.println(str);
                    if (testMessage.equals(str)) {
                        System.out.println("good");
                    }
                }
            });

            socket.handler(parser);
        });

        // 启动 TCP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                log.info("TCP server started on port " + port);
            } else {
                log.info("Failed to start TCP server: " + result.cause());
            }
        });
    }


    public static void main(String[] args) {
        new VertxTcpServer().doPost(8849);
    }

}
