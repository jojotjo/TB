package com.jojotjo.ticketbooking.service;

import com.jojotjo.ticketbooking.exception.ResourceNotFoundException;
import com.jojotjo.ticketbooking.exception.SeatUnavailableException;
import com.jojotjo.ticketbooking.model.*;
import com.jojotjo.ticketbooking.repository.GroupBookingRepository;
import com.jojotjo.ticketbooking.repository.TicketRepository;
import com.jojotjo.ticketbooking.repository.TrainRepository;
import com.jojotjo.ticketbooking.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    private final TicketRepository ticketRepository;
    private final GroupBookingRepository groupBookingRepository;
    private final SeatAvailabilityService seatAvailabilityService;
    private final UserRepository userRepository;
    private final TrainRepository trainRepository;

    public BookingService(
            TicketRepository ticketRepository,
            GroupBookingRepository groupBookingRepository,
            SeatAvailabilityService seatAvailabilityService,
            UserRepository userRepository,
            TrainRepository trainRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.groupBookingRepository = groupBookingRepository;
        this.seatAvailabilityService = seatAvailabilityService;
        this.userRepository = userRepository;
        this.trainRepository = trainRepository;
    }

    @Transactional
    public List<Ticket> bookMultipleSeats(
            String userId,
            String trainId,
            List<Integer> seatNumbers,
            String source,
            String destination
    ) {
        // Validate input
        if (seatNumbers == null || seatNumbers.isEmpty()) {
            throw new IllegalArgumentException("At least one seat must be selected");
        }

        // Fetch and validate user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        // Fetch and validate train
        // FIXED: Changed from findById(id) to findById(Long.parseLong(trainId))
        Train train = trainRepository.findById(Long.parseLong(trainId))
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with ID: " + trainId));

        // Validate source and destination
        if (source == null || source.isBlank() || destination == null || destination.isBlank()) {
            throw new IllegalArgumentException("Source and destination cannot be empty");
        }

        if (source.equalsIgnoreCase(destination)) {
            throw new IllegalArgumentException("Source and destination must be different");
        }

        // Create group booking
        GroupBooking groupBooking = new GroupBooking();
        groupBooking.setGroupId(UUID.randomUUID().toString());
        groupBooking = groupBookingRepository.save(groupBooking);

        // Capture variables as final for lambda usage
        final String finalTrainId = trainId;
        final User finalUser = user;
        final Train finalTrain = train;
        final String finalSource = source;
        final String finalDestination = destination;
        final GroupBooking finalGroupBooking = groupBooking;

        //  FIXED: Convert trainId to Long for SeatAvailabilityService
        final Long finalTrainIdLong = Long.parseLong(trainId);

        return seatNumbers.stream().map(seatNumber -> {
            try {
                // Validate and lock seat
                //  FIXED: Pass Long trainId instead of String
                seatAvailabilityService.validateAndLockSeat(finalTrainIdLong, seatNumber);

                // Create ticket
                Ticket ticket = new Ticket();
                ticket.setTicketId(UUID.randomUUID().toString());
                ticket.setUser(finalUser);
                ticket.setTrain(finalTrain);
                ticket.setSeatNo(String.valueOf(seatNumber));
                ticket.setSource(finalSource);
                ticket.setDestination(finalDestination);
                ticket.setBookingDate(LocalDateTime.now());
                ticket.setGroupBooking(finalGroupBooking);

                return ticketRepository.save(ticket);

            } catch (SeatUnavailableException e) {
                throw new SeatUnavailableException("Seat " + seatNumber + " is not available for train " + finalTrainId);
            }
        }).toList();
    }

    public List<Ticket> getBookingsForUser(String userId) {
        // Validate user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        return ticketRepository.findByUserUserId(userId);
    }

    @Transactional
    public void cancelBooking(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + ticketId));

        // Release the seat back to availability
        Long seatId = ticket.getSeatId();
        seatAvailabilityService.releaseSeat(seatId);

        // Delete ticket
        ticketRepository.delete(ticket);
    }
}