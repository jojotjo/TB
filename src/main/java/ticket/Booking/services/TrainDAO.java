package ticket.Booking.services;

import ticket.Booking.enities.Seat;
import ticket.Booking.enities.StationSchedule;
import ticket.Booking.enities.Train;
import ticket.Booking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {

    // FETCH ALL TRAINS + their schedules and seats
    public static List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM trains";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Train train = new Train();
                train.setTrainId(rs.getInt("train_id"));
                train.setTrainNo(rs.getString("train_no"));
                train.setTrainName(rs.getString("train_name"));
                train.setSource(rs.getString("source"));
                train.setDestination(rs.getString("destination"));

                // Load related schedules and seats
                train.setStationSchedules(getStationSchedules(train.getTrainId(), conn));
                train.setSeats(getSeats(train.getTrainId(), conn));

                trains.add(train);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trains;
    }

    // Helper: Load schedules (reusing existing connection)
    private static List<StationSchedule> getStationSchedules(int trainId, Connection conn) {
        List<StationSchedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM station_schedules WHERE train_id = ? ORDER BY station_order ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StationSchedule schedule = new StationSchedule();
                    schedule.setStationName(rs.getString("station_name"));
                    schedule.setArrivalTime(rs.getString("arrival_time"));
                    schedule.setDepartureTime(rs.getString("departure_time"));
                    schedules.add(schedule);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }

    // Helper: Load seats (reusing existing connection)
    private static List<Seat> getSeats(int trainId, Connection conn) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE train_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat();
                    seat.setSeatNumber(rs.getString("seat_number"));
                    seat.setBooked(rs.getBoolean("is_booked"));
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    // Helper: Load schedules for a specific train (new connection)
    private static List<StationSchedule> getStationSchedulesForTrain(int trainId) {
        List<StationSchedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM station_schedules WHERE train_id = ? ORDER BY station_order ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                StationSchedule schedule = new StationSchedule();
                schedule.setStationName(rs.getString("station_name"));
                schedule.setArrivalTime(rs.getString("arrival_time"));
                schedule.setDepartureTime(rs.getString("departure_time"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching station schedules: " + e.getMessage());
            e.printStackTrace();
        }
        return schedules;
    }

    // Helper: Load seats for a specific train (new connection)
    private static List<Seat> getSeatsForTrain(int trainId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE train_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatNumber(rs.getString("seat_number"));
                seat.setBooked(rs.getBoolean("is_booked"));
                seats.add(seat);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching seats: " + e.getMessage());
            e.printStackTrace();
        }
        return seats;
    }

    // Get train by ID
    public static Train getTrainById(int trainId) {
        String sql = "SELECT * FROM trains WHERE train_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Train train = new Train();
                train.setTrainId(rs.getInt("train_id"));
                train.setTrainNo(rs.getString("train_no"));
                train.setTrainName(rs.getString("train_name"));
                train.setSource(rs.getString("source"));
                train.setDestination(rs.getString("destination"));

                // Load station schedules and seats for this train
                train.setStationSchedules(getStationSchedulesForTrain(trainId));
                train.setSeats(getSeatsForTrain(trainId));

                return train;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching train by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Get train by train number
    public static Train getTrainByNumber(String trainNo) {
        String sql = "SELECT * FROM trains WHERE train_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trainNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Train train = new Train();
                train.setTrainId(rs.getInt("train_id"));
                train.setTrainNo(rs.getString("train_no"));
                train.setTrainName(rs.getString("train_name"));
                train.setSource(rs.getString("source"));
                train.setDestination(rs.getString("destination"));

                // Load station schedules and seats for this train
                train.setStationSchedules(getStationSchedulesForTrain(train.getTrainId()));
                train.setSeats(getSeatsForTrain(train.getTrainId()));

                return train;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching train by number: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Insert train
    public void insertTrain(Train train) {
        String sql = "INSERT OR IGNORE INTO trains (train_no, train_name, source, destination, total_seats, booked_seats, waiting_seats) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, train.getTrainNo());
                pstmt.setString(2, train.getTrainName());

                // First and last stations as source/destination
                pstmt.setString(3, train.getStationSchedules().get(0).getStationName());
                pstmt.setString(4, train.getStationSchedules().get(train.getStationSchedules().size() - 1).getStationName());
                pstmt.setInt(5, 0);
                pstmt.setInt(6, 0);
                pstmt.setInt(7, 0);

                pstmt.executeUpdate();

                int trainId = -1;
                try(ResultSet keys = pstmt.getGeneratedKeys()){
                    if (keys.next()) {
                        trainId = keys.getInt(1);
                    }
                }

                if (trainId != -1) {
                    train.setTrainId(trainId);
                    insertStationsSchedules(trainId, train.getStationSchedules(), conn);
                    insertSeats(trainId, train.getSeats(), conn);
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Insert schedules
    private void insertStationsSchedules(int trainId, List<StationSchedule> schedules, Connection conn) throws SQLException {
        String sql = "INSERT INTO station_schedules (train_id, station_order, station_name, arrival_time, departure_time) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < schedules.size(); i++) {
                StationSchedule s = schedules.get(i);
                pstmt.setInt(1, trainId);
                pstmt.setInt(2, i + 1);
                pstmt.setString(3, s.getStationName());
                pstmt.setString(4, s.getArrivalTime());
                pstmt.setString(5, s.getDepartureTime());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    // Insert seats
    private void insertSeats(int trainId, List<Seat> seats, Connection conn) throws SQLException {
        String sql = "INSERT INTO seats (train_id, seat_number, is_booked) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Seat s : seats) {
                pstmt.setInt(1, trainId);
                pstmt.setString(2, s.getSeatNumber());
                pstmt.setBoolean(3, s.isBooked());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    // Update train and seats
    public void updateTrain(Train updatedTrain) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Update seats
                String deleteSeatsSql = "DELETE FROM seats WHERE train_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteSeatsSql)) {
                    pstmt.setInt(1, updatedTrain.getTrainId());
                    pstmt.executeUpdate();
                }

                // Insert updated seats
                insertSeats(updatedTrain.getTrainId(), updatedTrain.getSeats(), conn);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error updating train: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}