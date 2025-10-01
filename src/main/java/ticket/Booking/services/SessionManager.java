package ticket.Booking.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ticket.Booking.enities.User;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);
    private static User currentUser;
    private static Map<String,Integer> loginAttempts = new HashMap<>();
    private static final int MAX_ATTEMPTS = 3;

    public static User getCurrentUser(){
        return currentUser;
    }

    public static void setCurrentUser(User user){
        currentUser = user;
        if (user != null) {
            log.info("User '{}' logged in.", user.getName());
        }
    }

    public static boolean isBlocked(String username){
        return loginAttempts.getOrDefault(username,0) >= MAX_ATTEMPTS;
    }

    public static  void recordFailedAttempt(String username){
        int attempts = loginAttempts.getOrDefault(username,0);
        loginAttempts.put(username,attempts+1);
        log.warn("Failed login attempt {} for user '{}'", attempts, username);
    }

    public static  void resetAttempts(String username){
        loginAttempts.put(username,0);
        log.info("Login attempts reset for user '{}'", username);
    }

    public static void logout(){
        if (currentUser != null) {
            String username = currentUser.getName();
            resetAttempts(username);
            currentUser = null;
            log.info("User '{}' logged out successfully.", username);
            System.out.println("User '" + username + "' logged out successfully.");
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public static boolean isLoggedIn(){
        return currentUser != null;
    }

}
