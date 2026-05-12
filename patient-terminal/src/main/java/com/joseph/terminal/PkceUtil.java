package com.joseph.terminal;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PkceUtil {
    private final String verifier;
    private final String challenge;

    private PkceUtil(String verifier, String challenge) {
        this.verifier = verifier;
        this.challenge = challenge;
    }

    public static PkceUtil generate() throws Exception {
        // Generate random verifier
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        // Hash it to get the challenge
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(verifier.getBytes());
        String challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);

        return new PkceUtil(verifier, challenge);
    }

    public String getVerifier() { return verifier; }
    public String getChallenge() { return challenge; }
}
