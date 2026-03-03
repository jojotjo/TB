package ticket.Booking.util;

import ticket.Booking.enities.Seat;
import ticket.Booking.enities.StationSchedule;
import ticket.Booking.enities.Train;
import ticket.Booking.services.TrainDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSeeder {

    public static void seedData() {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            // Check if train data already exists
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM trains");
            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("Train data already exists — skipping seeding.");
                rs.close();
                stmt.close();
                conn.close();
                return;
            }

            rs.close();
            stmt.close();
            conn.close(); // Close before DAO operations

            System.out.println("Seeding sample train data...");
            TrainDAO dao = new TrainDAO();

            // Train 1
            Train t1 = new Train();
            t1.setTrainNo("12012");
            t1.setTrainName("Shatabdi Express");

            List<StationSchedule> s1 = new ArrayList<>();
            s1.add(new StationSchedule("chandigarh", "07:00", "07:05"));
            s1.add(new StationSchedule("ambala", "07:45", "07:50"));
            s1.add(new StationSchedule("panipat", "08:30", "08:35"));
            s1.add(new StationSchedule("delhi", "09:30", "--"));
            t1.setStationSchedules(s1);

            List<Seat> seats1 = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                seats1.add(new Seat(String.valueOf(i), i <= 20));
            }
            t1.setSeats(seats1);

            dao.insertTrain(t1);

            // Train 2
            Train t2 = new Train();
            t2.setTrainNo("12986");
            t2.setTrainName("Ajmer Shatabdi");

            List<StationSchedule> s2 = new ArrayList<>();
            s2.add(new StationSchedule("delhi", "06:00", "06:05"));
            s2.add(new StationSchedule("gurgaon", "06:40", "06:45"));
            s2.add(new StationSchedule("rewari", "07:30", "07:35"));
            s2.add(new StationSchedule("jaipur", "10:00", "--"));
            t2.setStationSchedules(s2);

            List<Seat> seats2 = new ArrayList<>();
            for (int i = 1; i <= 120; i++) {
                seats2.add(new Seat(String.valueOf(i), i <= 40));
            }
            t2.setSeats(seats2);

            dao.insertTrain(t2);

            System.out.println("Sample train data inserted successfully!");

        } catch (Exception e) {
            System.out.println("Error seeding database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
