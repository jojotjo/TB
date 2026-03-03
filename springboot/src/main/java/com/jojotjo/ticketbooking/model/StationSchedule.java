package com.jojotjo.ticketbooking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "station_schedules")
public class StationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(name = "station_name", nullable = false)
    private String stationName;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    // FIX: stopOrder ensures correct direction check in passesThrough()
    @Column(name = "stop_order")
    private Integer stopOrder;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "schedules")
    @JsonIgnore
    private List<Train> trains = new ArrayList<>();

    public StationSchedule() {}

    public StationSchedule(String stationName, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public StationSchedule(String stationName, LocalDateTime arrivalTime, LocalDateTime departureTime, int stopOrder) {
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopOrder = stopOrder;
    }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }
    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public Integer getStopOrder() { return stopOrder; }
    public void setStopOrder(Integer stopOrder) { this.stopOrder = stopOrder; }
    public List<Train> getTrains() { return trains; }
    public void setTrains(List<Train> trains) { this.trains = trains; }

    public void addTrain(Train train) {
        if (this.trains == null) this.trains = new ArrayList<>();
        if (!this.trains.contains(train)) this.trains.add(train);
    }

    public void removeTrain(Train train) {
        if (this.trains != null) this.trains.remove(train);
    }
}