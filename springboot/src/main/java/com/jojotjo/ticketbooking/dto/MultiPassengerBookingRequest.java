package com.jojotjo.ticketbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class MultiPassengerBookingRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String trainId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<@NotNull @Positive Integer> seatNumbers;

    @NotBlank
    private String source;

    @NotBlank
    private String destination;

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

    public List<Integer> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<Integer> seatNumbers) {
        this.seatNumbers = seatNumbers;
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
