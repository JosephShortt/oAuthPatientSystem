package com.joseph.terminal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PatientService {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void searchPatients(String name, String token) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/patients/search?name=" + name.replace(" ","%20")))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode patients = mapper.readTree(response.body());

        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }

        System.out.println("\n--- Results ---");
        for (JsonNode patient : patients) {
            System.out.println("Name: " + patient.get("firstName").asText() + " " + patient.get("lastName").asText());
            System.out.println("DOB: " + patient.get("dateOfBirth").asText());
            System.out.println("Gender: " + patient.get("gender").asText());
            System.out.println("Address: " + patient.get("address").asText() + ", " + patient.get("city").asText());

            JsonNode conditions = patient.get("conditions");
            if (conditions !=null && !conditions.isEmpty()){
                System.out.println("Conditions:");
                for (JsonNode condition : conditions){
                    System.out.println("  - " +condition.asText());
                }
            }
            else {
                System.out.println("Conditions: None");
            }
            System.out.println("----");

        }
    }
}
