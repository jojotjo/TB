package com.jojotjo.ticketbooking.config;

import com.jojotjo.ticketbooking.model.User;
import com.jojotjo.ticketbooking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            long userCount = userRepository.count();

            if (userCount == 0) {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("👤 INITIALIZING USER DATA");
                System.out.println("=".repeat(50) + "\n");

                createUsers();

                System.out.println("\n" + "=".repeat(50));
                System.out.println("✅ USER INITIALIZATION COMPLETE");
                System.out.println("=".repeat(50) + "\n");
            } else {
                System.out.println("\n✅ Users already exist. Skipping initialization.\n");
            }
        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    private void createUsers() {
        try {
            System.out.println("Creating test users...\n");

            // User 1: USER001
            User user1 = new User();
            user1.setUserId("USER001");
            user1.setName("Rajesh Kumar");
            user1.setEmail("rajesh@example.com");
            // ✅ IMPORTANT: Encode password with BCrypt
            user1.setHashedPassword(passwordEncoder.encode("password123"));
            userRepository.save(user1);
            System.out.println("  ✓ USER001 (password123)");

            // User 2: USER002
            User user2 = new User();
            user2.setUserId("USER002");
            user2.setName("Priya Singh");
            user2.setEmail("priya@example.com");
            user2.setHashedPassword(passwordEncoder.encode("password123"));
            userRepository.save(user2);
            System.out.println("  ✓ USER002 (password123)");

            // User 3: USER003
            User user3 = new User();
            user3.setUserId("USER003");
            user3.setName("Amit Patel");
            user3.setEmail("amit@example.com");
            user3.setHashedPassword(passwordEncoder.encode("password123"));
            userRepository.save(user3);
            System.out.println("  ✓ USER003 (password123)");

            // User 4: USER004
            User user4 = new User();
            user4.setUserId("USER004");
            user4.setName("Deepak Verma");
            user4.setEmail("deepak@example.com");
            user4.setHashedPassword(passwordEncoder.encode("password123"));
            userRepository.save(user4);
            System.out.println("  ✓ USER004 (password123)");

            System.out.println("\n📊 Verification:");
            System.out.println("  Users created: " + userRepository.count());
            System.out.println("\n💡 Login Credentials:");
            System.out.println("  Username: USER001, USER002, USER003, or USER004");
            System.out.println("  Password: password123");

        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}