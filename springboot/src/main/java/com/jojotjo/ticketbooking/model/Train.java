package com.jojotjo.ticketbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "trains")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private Long id;

    @Column(name = "train_number", nullable = false, unique = true)
    private String trainNumber;

    @Column(name = "train_name", nullable = false)
    private String trainName;

    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    @JsonIgnore  // FIX: Prevents LazyInitializationException - seats not needed in search results
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "train_station_schedule",
            joinColumns = @JoinColumn(name = "train_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private List<StationSchedule> schedules = new ArrayList<>();

    public Train() {}

    public Train(String trainNumber, String trainName, Integer totalSeats) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.totalSeats = totalSeats;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }
    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }
    public List<StationSchedule> getSchedules() { return schedules; }
    public void setSchedules(List<StationSchedule> schedules) { this.schedules = schedules; }

    public void addSeat(Seat seat) {
        if (this.seats == null) this.seats = new ArrayList<>();
        this.seats.add(seat);
        seat.setTrain(this);
    }

    public void removeSeat(Seat seat) {
        if (this.seats != null) { this.seats.remove(seat); seat.setTrain(null); }
    }

    public void addSchedule(StationSchedule schedule) {
        if (this.schedules == null) this.schedules = new ArrayList<>();
        if (!this.schedules.contains(schedule)) this.schedules.add(schedule);
    }

    public void removeSchedule(StationSchedule schedule) {
        if (this.schedules != null) this.schedules.remove(schedule);
    }

    // FIX: Sort schedules by stopOrder before comparing indices
    public boolean passesThrough(String source, String destination) {
        try {
            String src = source.toLowerCase().trim();
            String dest = destination.toLowerCase().trim();

            if (this.schedules == null || this.schedules.isEmpty()) return false;

            List<StationSchedule> sorted = new ArrayList<>(this.schedules);
            sorted.sort(Comparator.comparingInt(s -> s.getStopOrder() != null ? s.getStopOrder() : 0));

            int sourceIndex = -1, destinationIndex = -1;
            for (int i = 0; i < sorted.size(); i++) {
                String name = sorted.get(i).getStationName();
                if (name.equalsIgnoreCase(src)) sourceIndex = i;
                if (name.equalsIgnoreCase(dest)) destinationIndex = i;
            }
            return sourceIndex >= 0 && destinationIndex > sourceIndex;
        } catch (Exception e) {
            System.err.println("ERROR in passesThrough: " + e.getMessage());
            return false;
        }
    }
}