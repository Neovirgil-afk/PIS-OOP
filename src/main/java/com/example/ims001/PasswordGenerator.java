package com.example.ims001;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*";

    public static String generate(int length) {
        if (length < 8) length = 8; // minimum 8 chars

        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = new ArrayList<>();

        // Ensure at least 1 character of each type
        passwordChars.add(UPPER.charAt(random.nextInt(UPPER.length())));
        passwordChars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        passwordChars.add(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        passwordChars.add(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));

        // Fill remaining characters randomly
        String allChars = UPPER + LOWER + NUMBERS + SYMBOLS;
        for (int i = 4; i < length; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle to randomize order
        Collections.shuffle(passwordChars, random);

        // Convert to string
        StringBuilder sb = new StringBuilder();
        for (char c : passwordChars) sb.append(c);
        return sb.toString();
    }
}
