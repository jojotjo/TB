package com.jojotjo.ticketbooking.repository;

import com.jojotjo.ticketbooking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    List<Ticket> findByUserUserId(String userId);

    List<Ticket> findByGroupBookingGroupId(String groupId);

    @Query("SELECT t FROM Ticket t WHERE t.user.userId = :userId AND t.train.id = :trainId")
    List<Ticket> findUserTicketsForTrain(@Param("userId") String userId, @Param("trainId") String trainId);
}