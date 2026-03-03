package com.jojotjo.ticketbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SeatBookingRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String trainId;

    @NotNull
    @Positive(message = "Seat number must be positive")
    private Integer seatNumber;

    @NotBlank
    private String source;

    @NotBlank
    public String destination;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

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
