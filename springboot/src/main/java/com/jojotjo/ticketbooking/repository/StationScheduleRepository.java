package com.jojotjo.ticketbooking.repository;

import com.jojotjo.ticketbooking.model.StationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationScheduleRepository extends JpaRepository<StationSchedule, Long> {
}