package ticket.Booking.services;

import ticket.Booking.enities.Train;
import ticket.Booking.enities.User;
import ticket.Booking.enities.Ticket;
import ticket.Booking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setName(rs.getString("name"));
                user.setHashedPassword(rs.getString("hashed_password"));
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }


    public void insertUser(User user){
        String sql = "INSERT INTO users (user_id, name, hashed_password) VALUES (?, ?, ?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getHashedPassword());
            pstmt.executeUpdate();
            System.out.println("User inserted successfully!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User getUserByName(String name){
        String sql = "SELECT * FROM users WHERE name = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setName(rs.getString("name"));
                user.setHashedPassword(rs.getString("hashed_password"));
                // Load tickets for this user
                user.setTicketsBooked(getTicketsForUser(rs.getString("user_id")));
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(String userId){
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setName(rs.getString("name"));
                user.setHashedPassword(rs.getString("hashed_password"));
                user.setTicketsBooked(getTicketsForUser(userId));
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User user){
        String sql = "UPDATE users SET name = ?, hashed_password = ? WHERE user_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getHashedPassword());
            pstmt.setString(3, user.getUserId());
            pstmt.executeUpdate();

            // Update tickets (delete old ones and insert new ones)
            updateUserTickets(user);

            System.out.println("User updated successfully!");
        }catch (SQLException e){
            System.out.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateUserTickets(User user){
        // First delete all existing tickets for this user
        String deleteSql = "DELETE FROM tickets WHERE user_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(deleteSql)){
            pstmt.setString(1, user.getUserId());
            pstmt.executeUpdate();

            // Then insert all current tickets
            insertTicketsForUser(user);

        }catch (SQLException e){
            System.out.println("Error updating user tickets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void insertTicketsForUser(User user){
        String sql = "INSERT INTO tickets (ticket_id, user_id, train_id, source, destination, seat_no, booking_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            for(Ticket ticket : user.getTicketsBooked()){
                pstmt.setString(1, ticket.getTicketId());
                pstmt.setString(2, user.getUserId());
                pstmt.setString(3, ticket.getTrainId());
                pstmt.setString(4, ticket.getSource());
                pstmt.setString(5, ticket.getDestination());
                pstmt.setString(6, ticket.getSeatNo());
                pstmt.setString(7, ticket.getBookingDate());
                pstmt.executeUpdate();
            }

        }catch (SQLException e){
            System.out.println("Error inserting tickets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Ticket> getTicketsForUser(String userId){
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE user_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int trainId = rs.getInt("train_id");
                Train train = TrainDAO.getTrainById(trainId);

                Ticket ticket = new Ticket(
                        rs.getString("ticket_id"),
                        String.valueOf(trainId),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getString("seat_no"),
                        rs.getString("booking_date"),
                        userId,
                        train
                );
                tickets.add(ticket);
            }

        }catch (SQLException e){
            System.out.println("Error fetching tickets: " + e.getMessage());
            e.printStackTrace();
        }
        return tickets;
    }
}