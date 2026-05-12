package com.joseph.terminal;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

public class CallbackServer {

    private final HttpServer server;
    private final CompletableFuture<String> codeFuture = new CompletableFuture<>();

    public CallbackServer(int port) throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/callback", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String code = extractCode(query);

            String response = "<html><body><h2>Login successful! You can close this tab.</h2></body></html>";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();

            codeFuture.complete(code);
            server.stop(0);
        });
        server.start();
    }

    public String waitForCode() throws Exception {
        return codeFuture.get();
    }

    private String extractCode(String query) {
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair[0].equals("code")) {
                return pair[1];
            }
        }
        throw new RuntimeException("No code found in callback");
    }
}
