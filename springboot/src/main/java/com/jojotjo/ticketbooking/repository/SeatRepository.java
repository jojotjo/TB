package com.jojotjo.ticketbooking.repository;

import com.jojotjo.ticketbooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    /**
     * Find all unbooked seats for a specific train
     * ✅ FIXED: Changed s.train.trainId to s.train.id
     */
    @Query("SELECT s FROM Seat s WHERE s.train.id = :trainId AND s.booked = false")
    List<Seat> findByTrainIdAndIsBookedFalse(@Param("trainId") Long trainId);

    /**
     * Find all seats for a specific train
     * ✅ FIXED: Changed s.train.trainId to s.train.id
     */
    @Query("SELECT s FROM Seat s WHERE s.train.id = :trainId")
    List<Seat> findByTrainId(@Param("trainId") Long trainId);

    /**
     * Find a specific seat by train and seat number
     * ✅ FIXED: Changed s.train.trainId to s.train.id
     */
    @Query("SELECT s FROM Seat s WHERE s.train.id = :trainId AND s.seatNumber = :seatNumber")
    Seat findByTrainIdAndSeatNumber(@Param("trainId") Long trainId, @Param("seatNumber") String seatNumber);

    /**
     * Find seat by seat ID
     */
    Seat findBySeatId(Long seatId);
}