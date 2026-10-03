package com.dev;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Map<Integer, String> portAndOrigin = ArgumentParser.argumentParser(args);

        Map.Entry<Integer, String> entry = portAndOrigin.entrySet().iterator().next();

        int port = entry.getKey();
        String originUrl = entry.getValue();

        System.out.println("Starting proxy on port: " + port);
        System.out.println("Proxying requests to: " + originUrl);


        // Create the server instance bound to port
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new RouteHandlers.MyRouteHandler());

//        // Map a URI path to a request handler
//        server.createContext("/test", new HttpHandler() {
//            @Override
//            public void handle(HttpExchange exchange) throws IOException {
//                String response = "Hello world from Java HTTP server";
//
//                // Send standard HTTP 200 OK headers along with response length
//                exchange.sendResponseHeaders(200, response.length());
//
//                // Write the response payload
//                try(OutputStream os = exchange.getResponseBody()) {
//                    os.write(response.getBytes());
//                }
//            }
//        });

        server.setExecutor(null);

        server.start();

        System.out.println("Server started on port " + port + ". Navigate to http://localhost:3000/test");


    }

}