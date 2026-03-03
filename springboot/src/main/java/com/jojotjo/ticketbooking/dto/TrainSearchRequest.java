package com.jojotjo.ticketbooking.dto;

import jakarta.validation.constraints.NotBlank;

public class TrainSearchRequest {

    @NotBlank(message = "Source station is required")
    private String source;

    @NotBlank(message = "Destination station is required")
    private String destination;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source.toLowerCase().trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination.toLowerCase().trim();
    }

}
