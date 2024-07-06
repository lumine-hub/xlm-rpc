package com.xlm.rpc.server;


import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{
    @Override
    public void doPost(int port) {
        // 创建实例
        Vertx vertx = Vertx.vertx();
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(new HttpServerHandler()); // 按照官网案例传入是一个无返回值的Function接口，我们可以传入实现类。
//        httpServer.requestHandler(request -> {
//            // 处理 HTTP 请求
//            System.out.println("Received request: " + request.method() + " " + request.uri());
//
//            // 发送 HTTP 响应
//            request.response()
//                    .putHeader("content-type", "text/plain")
//                    .end("Hello from Vert.x HTTP server!");
//        });
        httpServer.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            } else {
                System.err.println("Failed to start server: " + result.cause());
            }
        });


//         下面是官网的案例，可以看看。
//        vertx.createHttpServer().requestHandler(req -> {
//            req.response()
//                    .putHeader("content-type", "text/plain")
//                    .end("Hello from Vert.x!");
//        }).listen(8888, http -> {
//            if (http.succeeded()) {
//                startPromise.complete();
//                System.out.println("HTTP server started on port 8888");
//            } else {
//                startPromise.fail(http.cause());
//            }
//        });
    }
}
