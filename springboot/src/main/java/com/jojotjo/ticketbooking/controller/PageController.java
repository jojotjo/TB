package com.jojotjo.ticketbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {

    /**
     * Show login page
     * Accessible to everyone (permitAll in SecurityConfig)
     */
    @GetMapping("/login")
    public String login() {
        return "login";  // Returns login.html from templates folder
    }

    /**
     * Show home page
     * Only accessible after login (requires authentication)
     */
    @GetMapping("/")
    public String home() {
        return "index";  // Returns index.html from templates folder
    }

    /**
     * Alternative home page path
     */
    @GetMapping("/home")
    public String homeAlternative() {
        return "index";  // Returns index.html from templates folder
    }

    @GetMapping("/search-trains")
    public String searchTrains() {
        return "search-trains";
    }

    @GetMapping("/book-tickets")
    public String bookTickets() {
        return "book-tickets";
    }

    @GetMapping("/my-bookings")
    public String myBookings() {
        return "my-bookings";
    }

    @GetMapping("/edit-profile")
    public String editProfile() {
        return "edit-profile";
    }

    @GetMapping("/get-help")
    public String getHelp() {
        return "get-help";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }



}