package com.joseph.terminal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String exchangeCodeForToken(String code, String verifier) throws Exception {
        String body = "grant_type=authorization_code" +
                "&client_id=patient-client" +
                "&client_secret=secret" +
                "&code=" + code +
                "&redirect_uri=http://localhost:8888/callback" +
                "&code_verifier=" + verifier;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9000/oauth2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode json = mapper.readTree(response.body());
        return json.get("access_token").asText();
    }
}
