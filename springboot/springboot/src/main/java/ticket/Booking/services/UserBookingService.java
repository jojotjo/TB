package ticket.Booking.services;

import ticket.Booking.enities.*;
import ticket.Booking.util.PasswordUtil;
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



    public Boolean loginUser(String username,String plainPassword){
        if(SessionManager.isBlocked(username)){
            System.out.println("Account locked.Too many failed login attempts.");
            return false;
        }

        User user = userDAO.getUserByName(username);
        if(user == null){
            System.out.println("User not found.");
            return false;
        }

        if (!PasswordUtil.verifyPassword(plainPassword, user.getHashedPassword())) {
            System.out.println("Incorrect password.");
            SessionManager.recordFailedAttempt(username);
            return false;
        }


        SessionManager.resetAttempts(username);
        SessionManager.setCurrentUser(user);

        System.out.println("Login successful. Welcom " + username + "!");
        return true;
    }

    public Boolean signUp(String username,String plainPassword){
        if(username == null || plainPassword==null){
            System.out.println("Username or password cannot be null.");
            return false;
        }

        boolean isValid = true;

        if(!ValidationUtil.isValidUsername(username)){
            System.out.println("Username must be at least 3 characters and contains only letters, number, or underscore.");
            isValid = false;
        }

        if(!ValidationUtil.isValidPassword(plainPassword)){
            System.out.println("Password must be at least 8 characters, contain upper/lowercase, number, and special char.");
            isValid = false;
        }

        if(!isValid) return false;

        User existningUser = userDAO.getUserByName(username);

        if(existningUser != null){
            System.out.println("Username already exists. Please choose another.");
            return false;
        }

        String hashedPassword =  PasswordUtil.hashPassword(plainPassword);
        User newUser = new User(username,hashedPassword,new ArrayList<>(),UUID.randomUUID().toString());
        userDAO.insertUser(newUser);
        return true;
    }


    public void fetchBooking(){
        if(user!=null){
            user.printTickets();
        }
    }

    public Boolean cancelBooking(String ticketId, User user) {
        if(ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return false;
        }

        Ticket ticketToCancel = null;
        for (Ticket ticket : user.getTicketsBooked()) {
            if (ticket.getTicketId().equals(ticketId)) {
                ticketToCancel = ticket;
                break;
            }
        }

        if (ticketToCancel == null) {
            System.out.println("No ticket found with ID " + ticketId);
            return false;
        }

        // Free up the seat in the train
        try {
            int trainId = Integer.parseInt(ticketToCancel.getTrainId());
            Train train = TrainDAO.getTrainById(trainId);
            if (train != null) {
                for (Seat seat : train.getSeats()) {
                    if (seat.getSeatNumber().equals(ticketToCancel.getSeatNo())) {
                        seat.setBooked(false);
                        break;
                    }
                }
                new TrainService().updateTrain(train);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid train ID in ticket");
        }

        // Remove ticket from user
        user.getTicketsBooked().remove(ticketToCancel);
        new UserDAO().updateUser(user);

        return true;
    }



    public List<Train> getTrains(String source,String destination){
        source = source.trim().toLowerCase();
        destination = destination.trim().toLowerCase();

        List<Train> allTrains = TrainDAO.getAllTrains();
        List<Train> matchedTrains = new ArrayList<>();

        for (Train t : allTrains) {
            List<StationSchedule> schedule = t.getStationSchedules();
            if (schedule.isEmpty()) continue;

            int sourceIndex = -1;
            int destIndex = -1;

            for (int i = 0; i < schedule.size(); i++) {
                String stationName = schedule.get(i).getStationName().trim().toLowerCase();
                if (stationName.equals(source)) sourceIndex = i;
                if (stationName.equals(destination)) destIndex = i;
            }

            if (sourceIndex != -1 && destIndex != -1 && sourceIndex < destIndex) {
                matchedTrains.add(t);
            }
        }

        return  matchedTrains;
    }

    public Boolean bookSeat(Train train, String seatNumber, User user, String source, String destination) {
        if (train == null || seatNumber == null || user == null) {
            System.out.println("Invalid booking parameters.");
            return false;
        }

        List<Seat> seats = train.getSeats();

        // Find the seat
        Seat targetSeat = null;
        for (Seat seat : seats) {
            if (seat.getSeatNumber().equals(seatNumber)) {
                targetSeat = seat;
                break;
            }
        }

        if (targetSeat == null) {
            System.out.println("Seat number not found.");
            return false;
        }

        if (targetSeat.isBooked()) {
            System.out.println("Seat already booked.");
            return false;
        }

        // Book the seat
        targetSeat.setBooked(true);

        // Create a ticket with current date
        String currentDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

        Ticket newTicket = new Ticket(
                UUID.randomUUID().toString(),
                String.valueOf(train.getTrainId()),
                source,
                destination,
                seatNumber,
                currentDate,
                user.getUserId(),
                train
        );


        user.getTicketsBooked().add(newTicket);

        trainService.updateTrain(train);
        userDAO.updateUser(user);

        return true;
    }


}
