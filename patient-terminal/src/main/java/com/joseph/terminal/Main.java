package com.joseph.terminal;

import java.awt.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("   Patient Admin System");
        System.out.println("=================================");
        System.out.println("Opening browser for login...");

        // Step 1 - Generate PKCE pair
        PkceUtil pkce = PkceUtil.generate();

        // Step 2 - Start callback server to catch the code
        CallbackServer callbackServer = new CallbackServer(8888);

        // Step 3 - Open browser with authorize URL
        String authorizeUrl = "http://localhost:9000/oauth2/authorize" +
                "?response_type=code" +
                "&client_id=patient-client" +
                "&redirect_uri=http://localhost:8888/callback" +
                "&scope=read" +
                "&code_challenge=" + pkce.getChallenge() +
                "&code_challenge_method=S256";

        Desktop.getDesktop().browse(new java.net.URI(authorizeUrl));

        // Step 4 - Wait for the code
        String code = callbackServer.waitForCode();
        System.out.println("Login successful!");

        // Step 5 - Exchange code for token
        String token = AuthService.exchangeCodeForToken(code, pkce.getVerifier());

        // Step 6 - Show menu
        showMenu(scanner, token);
    }

    private static void showMenu(Scanner scanner, String token) throws Exception {
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Search patient");
            System.out.println("2. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter patient name: ");
                String name = scanner.nextLine();
                PatientService.searchPatients(name, token);
            } else if (choice.equals("2")) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }
}
