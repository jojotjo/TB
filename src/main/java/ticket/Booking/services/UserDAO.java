package ticket.Booking.services;

import ticket.Booking.enities.User;
import ticket.Booking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        String sql = "INSERT INTO users (name, hashed_password, tickets VALUES (?,?,?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,user.getName());
            pstmt.setString(2,user.getHashedPassword());
            pstmt.setString(3,"");
            pstmt.executeUpdate();
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
