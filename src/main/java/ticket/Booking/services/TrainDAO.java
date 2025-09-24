package ticket.Booking.services;

import ticket.Booking.enities.Train;
import ticket.Booking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainDAO {
    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM trains")) {

            while (rs.next()) {
                Train train = new Train();
                train.setTrainId(rs.getString("train_id"));
                train.setTrainName(rs.getString("train_name"));
                train.setStations(Arrays.asList(rs.getString("stations").split(",")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trains;
    }

    public void insertTrain(Train newTrain){
        String sql = "INSERT INTO trains (train_id, train_no, train_name , stations) VALUES (?,?,?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, newTrain.getTrainId());
            pstmt.setString(2, newTrain.getTrainName());
            pstmt.setString(3,String.join(",", newTrain.getStations()));
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateTrain(Train updatedTrain){
        String sql = "UPDATE trains SET train_name = ?, stations = ? WHERE train_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, updatedTrain.getTrainName());
            pstmt.setString(2,String.join(",", updatedTrain.getStations()));
            pstmt.setString(3, updatedTrain.getTrainId());
            pstmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
