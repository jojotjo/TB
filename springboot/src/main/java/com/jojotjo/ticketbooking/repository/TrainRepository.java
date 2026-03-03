package com.jojotjo.ticketbooking.repository;

import com.jojotjo.ticketbooking.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    Optional<Train> findById(Long id);
    Optional<Train> findByTrainNumber(String trainNumber);
    Optional<Train> findByTrainName(String trainName);
}