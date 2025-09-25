package ticket.Booking.services;

import ticket.Booking.enities.Train;
import ticket.Booking.enities.User;
import ticket.Booking.util.UserServiceUtil;
import ticket.Booking.util.ValidationUtil;

import java.util.*;


public class UserBookingService {
    private User user;
    private final UserDAO userDAO = new UserDAO();
    private final TrainService trainService = new TrainService();

    public UserBookingService(User user){
        this.user = user;
    }

    public UserBookingService() {};

    public Boolean loginUser(){
        User dbUser = userDAO.getUserByName(user.getName());
        if(dbUser!=null && UserServiceUtil.checkPassword(user.getPassword(),dbUser.getHashedPassword())){
            this.user=dbUser;
            return true;
        }
        return  false;
    }

    public Boolean signUp(User newUser){
        if(newUser == null){
            System.out.println("Invalid user object.");
            return false;
        }

        boolean isValid = true;

        if(!ValidationUtil.isValidUsername(newUser.getName())){
            System.out.println("Username must be at least 3 characters and contains only letters, number, or underscore.");
            isValid = false;
        }

        if(!ValidationUtil.isValidPassword(newUser.getPassword())){
            System.out.println("Password must be at least 8 characters, contain upper/lowercase, number, and special char.");
            isValid = false;
        }

        if(!isValid) return false;

        User existningUser = userDAO.getUserByName(newUser.getName());

        if(existningUser != null){
            System.out.println("Username already exists. Please choose another.");
            return false;
        }

        newUser.setHashedPassword(UserServiceUtil.hashPassword(newUser.getPassword()));
        newUser.setPassword(null);
        userDAO.insertUser(newUser);
        return true;
    }


    public void fetchBooking(){
        if(user!=null){
            user.printTickets();
        }
    }

    public Boolean cancelBooking(String ticketId) {
        if(ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return  false;
        }

        boolean removed = user.getTicketsBooked().removeIf(ticket-> ticket.getTicketId().equals(ticketId));

        if(removed){
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return  true;
        }else{
            System.out.println("No ticket found with iD " + ticketId);
            return false;
        }
    }



    public List<Train> getTrains(String source,String destination){
        return trainService.searchTrains(source,destination);
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train,int row,int seat) {
        List<List<Integer>> seats = train.getSeats();
        if(row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
            if(seats.get(row).get(seat) == 0){
                seats.get(row).set(seat,1);
                train.setSeats(seats);
                trainService.updateTrain(train);
                return true;
            }
        }
        return false;
    }

}
