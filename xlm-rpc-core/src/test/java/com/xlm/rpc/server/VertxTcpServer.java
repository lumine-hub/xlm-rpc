package com.xlm.rpc.server;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;

public class VertxTcpServer implements HttpServer{
    private byte[] handleRequest(byte[] requestData) {
        return "Hello, client!".getBytes();
    }
    @Override
    public void doPost(int port) {
        Vertx vertx = Vertx.vertx();
        NetServer server = vertx.createNetServer();
        server.connectHandler(socket -> {
            socket.handler(buffer -> {
                byte[] bytes = buffer.getBytes();
                byte[] res = handleRequest(bytes);
                socket.write(Buffer.buffer(res));
            });
        });
        server.listen(port, result -> {
            if(result.succeeded()) {
                System.out.println("TCP server started on port " + port);
            } else {
                System.err.println("Failed to start TCP server: "+ result.cause());
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpServer().doPost(8849);
    }
}
