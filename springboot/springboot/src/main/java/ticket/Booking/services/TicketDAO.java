package ticket.Booking.services;

import ticket.Booking.enities.Ticket;
import ticket.Booking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketDAO {
    public void insertTicket(Ticket ticket,String userId) throws SQLException {
        String sql = "INSERT INTO tickets (ticket_id, train_id, source, destination, seat_no, booking_data, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,ticket.getTicketId());
            pstmt.setString(2,ticket.getTrainId());
            pstmt.setString(3,ticket.getSource());
            pstmt.setString(4,ticket.getDestination());
            pstmt.setString(5,ticket.getBookingDate());
            pstmt.setString(6,ticket.getUserId());

            pstmt.executeUpdate();
            System.out.println("Ticket inserted successfully!");
        }catch (SQLException e){
            System.err.println("Error insertion ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
