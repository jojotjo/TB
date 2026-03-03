package com.jojotjo.ticketbooking.repository;

import com.jojotjo.ticketbooking.model.GroupBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupBookingRepository extends JpaRepository<GroupBooking, String> {

}
