package com.jojotjo.ticketbooking.service;

import com.jojotjo.ticketbooking.exception.SeatUnavailableException;
import com.jojotjo.ticketbooking.model.Seat;
import com.jojotjo.ticketbooking.repository.SeatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatAvailabilityService {

    private final SeatRepository seatRepository;

    public SeatAvailabilityService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    /**
     * Get all available seats for a train
     * ✅ FIXED: Changed trainId parameter from String to Long
     *
     * @param trainId the train ID (Long)
     * @return list of available seats
     */
    public List<Seat> getAvailableSeats(Long trainId) {
        return seatRepository.findByTrainIdAndIsBookedFalse(trainId);
    }

    /**
     * Validate and lock a seat for booking
     * ✅ FIXED: Changed trainId parameter from String to Long
     *
     * @param trainId the train ID (Long)
     * @param seatNumber the seat number (Integer)
     * @return the locked seat or throw exception if not available
     */
    public Seat validateAndLockSeat(Long trainId, Integer seatNumber) {
        // Find the seat
        Seat seat = seatRepository.findByTrainIdAndSeatNumber(trainId, String.valueOf(seatNumber));

        // Check if seat exists and is not booked
        if (seat != null && !seat.isBooked()) {
            // Lock the seat by marking as booked
            seat.setBooked(true);
            return seatRepository.save(seat);
        }

        // Throw exception if seat is not available
        throw new SeatUnavailableException("Seat " + seatNumber + " is not available for train " + trainId);
    }

    /**
     * Release a seat (unbook it)
     *
     * @param seatId the seat ID (Long)
     * @return the released seat
     */
    public Seat releaseSeat(Long seatId) {
        Seat seat = seatRepository.findBySeatId(seatId);

        if (seat != null) {
            seat.setBooked(false);
            return seatRepository.save(seat);
        }

        return null; // Seat not found
    }

    /**
     * Check if a specific seat is available
     * ✅ FIXED: Changed trainId parameter from String to Long
     *
     * @param trainId the train ID (Long)
     * @param seatNumber the seat number (String)
     * @return true if available, false otherwise
     */
    public boolean isSeatAvailable(Long trainId, String seatNumber) {
        Seat seat = seatRepository.findByTrainIdAndSeatNumber(trainId, seatNumber);
        return seat != null && !seat.isBooked();
    }
}