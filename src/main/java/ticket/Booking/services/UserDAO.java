package ticket.Booking.services;

import ticket.Booking.enities.User;
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
            pstmt.setString(1,user.getUserId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getHashedPassword());
            pstmt.executeUpdate();
            System.out.println("User inserted successfully!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User getUserByName(String name){
        String sql = "SELECT * FROM users WHERE name = ? ";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,name);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setName(rs.getString("name"));
                user.setHashedPassword(rs.getString("hashed_password"));
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }



}
