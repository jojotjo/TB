package com.jojotjo.ticketbooking.config;

import com.jojotjo.ticketbooking.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(
            CustomUserDetailsService customUserDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Configure the DaoAuthenticationProvider
     * Uses CustomUserDetailsService to load users from database
     * Uses BCryptPasswordEncoder from WebConfig for password comparison
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * Configure AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configure HTTP Security and Filter Chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Security matcher for all requests
                .securityMatcher("/**")
                // Disable CSRF (for development)
                .csrf(csrf -> csrf.disable())
                // Configure authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        // Public endpoints
                        .requestMatchers("/login", "/register", "/error", "/css/**", "/js/**", "/images/**", "/api/public/**").permitAll()

                        // Protected page endpoints
                        .requestMatchers("/search-trains","/book-tickets","/my-bookings","/edit-profile","/get-help","/profile").authenticated()

                        // ✅ ADDED: Allow authenticated users to access train API
                        .requestMatchers("/api/trains/**").authenticated()

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                // Configure form login
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/api/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                // Configure logout
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                // Configure CORS (disabled for now)
                .cors(cors -> cors.disable());

        return http.build();
    }
}