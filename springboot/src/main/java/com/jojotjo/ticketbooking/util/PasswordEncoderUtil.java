package com.jojotjo.ticketbooking.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class PasswordEncoderUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private PasswordEncoderUtil() {
        // utility class - prevent instantiation
    }

    /** Encode plain text password */
    public static String encode(String plainPassword) {
        return encoder.encode(plainPassword);
    }

    /** Verify plain password against encoded password */
    public static boolean matches(String plainPassword, String encodedPassword) {
        return encoder.matches(plainPassword, encodedPassword);
    }
}
