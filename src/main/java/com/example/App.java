package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class App {

    public static String greeting(String name) {
        return "Hello, " + name + "!";
    }

    public static String healthJson() {
        return "{\"status\":\"UP\"}";
    }

    public static String nameFromQuery(String query) {
        if (query == null) {
            return "Harness";
        }
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2 && kv[0].equals("name") && !kv[1].isEmpty()) {
                return URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
            }
        }
        return "Harness";
    }

    private static void send(HttpExchange exchange, String text) throws IOException {
        byte[] body = text.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, body.length);
        try (var os = exchange.getResponseBody()) {
            os.write(body);
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", exchange -> send(exchange, greeting("Harness v2")));
        server.createContext("/hello", exchange ->
            send(exchange, greeting(nameFromQuery(exchange.getRequestURI().getRawQuery()))));
        server.createContext("/health", exchange -> send(exchange, healthJson()));
        server.start();
    }
}
