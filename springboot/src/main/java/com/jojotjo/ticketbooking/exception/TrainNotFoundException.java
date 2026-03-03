package com.jojotjo.ticketbooking.exception;

public class TrainNotFoundException extends RuntimeException {

    public TrainNotFoundException() {
        super("Train not found");
    }

    public TrainNotFoundException(String message) {
        super(message);
    }
}
