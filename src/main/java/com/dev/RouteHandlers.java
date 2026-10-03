package com.simon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteHandlers {
    public static class MyRouteHandler implements HttpHandler {

        ObjectMapper mapper = new ObjectMapper();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();

            String path = exchange.getRequestURI().getPath();

            System.out.println("Received a " + method + " request for " + path);

            if (method.equals("GET") && path.equals("/products")) {
                handleGetAllProducts(exchange);
            } else if (method.equals("GET") && path.equals("/products/1")) {
                handlePhone1(exchange);
            }

        }

        // Individual handlers
        private void handleGetAllProducts(HttpExchange exchange) throws IOException {
            // 2. Create individual phone data blocks using Maps
            Map<String, Object> phone1 = new HashMap<>();
            phone1.put("brand", "Apple");
            phone1.put("model", "iPhone 15 Pro");
            phone1.put("price", 999.99);

            Map<String, Object> phone2 = new HashMap<>();
            phone2.put("brand", "Samsung");
            phone2.put("model", "Galaxy S24 Ultra");
            phone2.put("price", 1199.99);

            Map<String, Object> phone3 = new HashMap<>();
            phone3.put("brand", "Google");
            phone3.put("model", "Pixel 8 Pro");
            phone3.put("price", 799.00);

            // 3. Group the phone maps into a List (the JSON array)
            List<Map<String, Object>> productsList = new ArrayList<>();
            productsList.add(phone1);
            productsList.add(phone2);
            productsList.add(phone3);

            // 4. Build the final top-level Response Map
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("status", "success");
            responseMap.put("totalResults", productsList.size());
            responseMap.put("products", productsList);

            // 5. Convert your map structure into a JSON String
            String jsonResponseString = mapper.writeValueAsString(responseMap);

            sendResponse(exchange, 200, jsonResponseString);
        }

        private void handlePhone1(HttpExchange exchange) throws IOException {
            Map<String, Object> phone1 = new HashMap<>();
            phone1.put("brand", "Apple");
            phone1.put("model", "iPhone 15 Pro");
            phone1.put("price", 999.99);

            String jsonResponseString = mapper.writeValueAsString(phone1);
            sendResponse(exchange, 200, jsonResponseString);
        }

        // Helper method to keep code clean
        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            exchange.sendResponseHeaders(statusCode, response.length());
            try(OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
