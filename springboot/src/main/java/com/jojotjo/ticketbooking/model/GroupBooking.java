package com.jojotjo.ticketbooking.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "group_bookings")
public class GroupBooking {

    @Id
    @Column(name = "group_id")
    private String groupId;

    @OneToMany(mappedBy = "groupBooking", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public GroupBooking() {}

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
