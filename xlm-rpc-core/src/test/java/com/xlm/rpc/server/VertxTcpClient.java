package com.xlm.rpc.server;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;

public class VertxTcpClient {
    public static void main(String[] args) {
        new VertxTcpClient().start();
    }
    public void start() {
        Vertx vertx = Vertx.vertx();
        vertx.createNetClient().connect(9091, "localhost", result -> {
           if (result.succeeded()) {
               System.out.println("connect to tcp server");
               NetSocket socket = result.result();
               socket.write("hello server");
               socket.handler(buffer -> {
                   System.out.println("receive from server：" + buffer.toString());
               });
           } else {
               System.out.println("failed to connect server");
           }
        });
    }
}
