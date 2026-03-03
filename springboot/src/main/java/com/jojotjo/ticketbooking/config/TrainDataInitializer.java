package com.jojotjo.ticketbooking.config;

import com.jojotjo.ticketbooking.model.Seat;
import com.jojotjo.ticketbooking.model.StationSchedule;
import com.jojotjo.ticketbooking.model.Train;
import com.jojotjo.ticketbooking.repository.SeatRepository;
import com.jojotjo.ticketbooking.repository.StationScheduleRepository;
import com.jojotjo.ticketbooking.repository.TrainRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class TrainDataInitializer implements CommandLineRunner {

    private final TrainRepository trainRepository;
    private final StationScheduleRepository stationScheduleRepository;
    private final SeatRepository seatRepository;

    public TrainDataInitializer(TrainRepository trainRepository,
                                StationScheduleRepository stationScheduleRepository,
                                SeatRepository seatRepository) {
        this.trainRepository = trainRepository;
        this.stationScheduleRepository = stationScheduleRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            if (trainRepository.count() == 0) {
                System.out.println(" ====== INITIALIZING TRAIN DATA ====== ");
                        initializeData();
                System.out.println(" ====== INITIALIZATION COMPLETE ====== ");
            } else {
                System.out.println("Trains already exist. Skipping initialization.");
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeData() {
        // Delhi -> Gwalior -> Agra (for TR001 and TR002)
        StationSchedule d1 = save("Delhi",     1, 2026,1,13,6,0,  6,30);
        StationSchedule g1 = save("Gwalior",   2, 2026,1,13,9,0,  9,30);
        StationSchedule a1 = save("Agra",      3, 2026,1,13,11,30,12,0);

        StationSchedule d2 = save("Delhi",     1, 2026,1,13,7,0,  7,30);
        StationSchedule g2 = save("Gwalior",   2, 2026,1,13,10,0, 10,30);
        StationSchedule a2 = save("Agra",      3, 2026,1,13,12,30,13,0);

        // Delhi -> Agra direct (TR003)
        StationSchedule d3 = save("Delhi",     1, 2026,1,13,8,0,  8,30);
        StationSchedule a3 = save("Agra",      2, 2026,1,13,11,0, 11,30);

        // Mumbai -> Bangalore (TR004)
        StationSchedule mb = save("Mumbai",    1, 2026,1,13,8,0,  8,30);
        StationSchedule bl = save("Bangalore", 2, 2026,1,13,18,0, 18,30);

        // Delhi -> Agra -> Gwalior -> Bhopal -> Indore (TR005)
        StationSchedule df = save("Delhi",     1, 2026,1,13,6,0,  6,30);
        StationSchedule af = save("Agra",      2, 2026,1,13,9,0,  9,30);
        StationSchedule gf = save("Gwalior",   3, 2026,1,13,11,0, 11,30);
        StationSchedule bf = save("Bhopal",    4, 2026,1,13,14,0, 14,30);
        StationSchedule inf= save("Indore",    5, 2026,1,13,17,0, 17,30);

        Train t1 = mk("TR001", "Rajdhani Express", 120, d1, g1, a1);
        Train t2 = mk("TR002", "Shatabdi Express", 100, d2, g2, a2);
        Train t3 = mk("TR003", "Taj Express",      150, d3, a3);
        Train t4 = mk("TR004", "Mumbai Express",   150, mb, bl);
        Train t5 = mk("TR005", "Jan Shatabdi",     200, df, af, gf, bf, inf);

        seats(t1, "A", 20); seats(t2, "B", 20); seats(t3, "C", 20);
        seats(t4, "D", 20); seats(t5, "E", 30);

        System.out.println("Trains: " + trainRepository.count() + ", Schedules: " + stationScheduleRepository.count());
        System.out.println("Valid searches: Delhi->Agra, Delhi->Gwalior, Delhi->Bhopal, Delhi->Indore, Mumbai->Bangalore");
    }

    private StationSchedule save(String name, int order, int y, int mo, int d, int h, int m, int h2, int m2) {
        StationSchedule s = new StationSchedule(name,
                LocalDateTime.of(y,mo,d,h,m), LocalDateTime.of(y,mo,d,h2,m2), order);
        return stationScheduleRepository.save(s);
    }

    private Train mk(String num, String name, int seats, StationSchedule... schedules) {
        Train t = new Train(num, name, seats);
        for (StationSchedule s : schedules) t.addSchedule(s);
        return trainRepository.save(t);
    }

    private void seats(Train train, String prefix, int count) {
        for (int i = 1; i <= count; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(prefix + i);
            seat.setBooked(false);
            seat.setTrain(train);
            seatRepository.save(seat);
        }
    }
}