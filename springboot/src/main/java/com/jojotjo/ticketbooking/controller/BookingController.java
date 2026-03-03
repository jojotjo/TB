package com.jojotjo.ticketbooking.controller;

import com.jojotjo.ticketbooking.exception.ResourceNotFoundException;
import com.jojotjo.ticketbooking.exception.SeatUnavailableException;
import com.jojotjo.ticketbooking.model.Ticket;
import com.jojotjo.ticketbooking.service.BookingService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Book multiple seats for a train
     *
     * @param userId the user ID
     * @param trainId the train ID
     * @param seatNumbers list of seat numbers to book
     * @param source the source station
     * @param destination the destination station
     * @return list of booked tickets or error message
     */
    @PostMapping("/book")
    public ResponseEntity<?> bookSeats(
            @RequestParam @NotBlank String userId,
            @RequestParam @NotBlank String trainId,
            @RequestParam @NotEmpty List<Integer> seatNumbers,
            @RequestParam @NotBlank String source,
            @RequestParam @NotBlank String destination
    ) {
        try {
            List<Ticket> tickets = bookingService.bookMultipleSeats(userId, trainId, seatNumbers, source, destination);
            return ResponseEntity.status(HttpStatus.CREATED).body(tickets);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SeatUnavailableException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while booking seats");
        }
    }

    /**
     * Get all bookings for a user
     *
     * @param userId the user ID
     * @return list of tickets for the user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBookings(@PathVariable @NotBlank String userId) {
        try {
            List<Ticket> bookings = bookingService.getBookingsForUser(userId);
            return ResponseEntity.ok(bookings);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Cancel a booking
     *
     * @param ticketId the ticket ID to cancel
     * @return success message or error
     */
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<?> cancelBooking(@PathVariable @NotBlank String ticketId) {
        try {
            bookingService.cancelBooking(ticketId);
            return ResponseEntity.ok("Booking cancelled successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while cancelling the booking");
        }
    }
}