package ticket.Booking;

import ticket.Booking.util.DatabaseSetup;
import ticket.Booking.enities.Train;
import ticket.Booking.enities.User;
import ticket.Booking.services.UserBookingService;
import ticket.Booking.util.UserServiceUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class App {
    public static void main(String[] args) throws SQLException {
        System.out.println("Running Train Booking System");
        Scanner scanner = new Scanner(System.in);
        DatabaseSetup db = new DatabaseSetup();
        try {
            db.initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UserBookingService userBookingService;
        try {
            userBookingService = new UserBookingService();
        }catch (IOException e){
            System.out.println("There is something wrong");
            return;
        }
        while (true){
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            int option = scanner.nextInt();
            Train trainSelectedForBooking = new Train();
            switch (option){
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = scanner.next();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = scanner.next();
                    User userToSignUp = new User(nameToSignUp,passwordToSignUp, UserServiceUtil.hashPassword(passwordToSignUp),new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userToSignUp);
                    break;
                case 2:
                    System.out.println("Enter the username to Login");
                    String nameToLogin = scanner.next();
                    System.out.println("Enter the password to Login");
                    String passwordToLogin = scanner.next();
                    User userToLogin = new User(nameToLogin,passwordToLogin,UserServiceUtil.hashPassword(passwordToLogin),new ArrayList<>(),UUID.randomUUID().toString());
                    try{
                        userBookingService = new UserBookingService(userToLogin);
                    }catch (IOException e){
                        return;
                    }
                    break;
                case 3:
                    System.out.println("Fetching your bookings");
                    userBookingService.fetchBooking();
                    break;
                case 4:
                    System.out.println("Type your source station");
                    String source = scanner.next();
                    System.out.println("Type your destination station");
                    String destination = scanner.next();
                    List<Train> trains = userBookingService.getTrains(source,destination);
                    int index = 1;
                    for(Train t:trains){
                        System.out.println(index + "Train Id: " + t.getTrainId());
                        for(Map.Entry<String,String> entry : t.getStationTimes().entrySet()){
                            System.out.println("station" + entry.getKey() + " time: " + entry.getValue());
                        }
                    }
                    System.out.println("Select a train by typing 1,2,3...");
                    trainSelectedForBooking = trains.get(scanner.nextInt());
                    break;
                case 5:
                    System.out.println("Enter a seat out of these seats");
                    List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);
                    for(List<Integer> row : seats){
                        for(Integer val : row){
                            System.out.println(val + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("Select the seat by typing the row and column");
                    System.out.println("Enter the row");
                    int row = scanner.nextInt();
                    System.out.println("Enter the column");
                    int col = scanner.nextInt();
                    System.out.println("Booking your seat....");
                    Boolean booked = userBookingService.bookTrainSeat(trainSelectedForBooking,row,col);
                    if(booked.equals(Boolean.TRUE)){
                        System.out.println("Booked! Enjoy your journey");
                    }else{
                        System.out.println("Can't book this seat");
                    }
                    break;
                case 6:
                    System.out.println("Enter the ticket Id");
                    String ticketId = scanner.next();
                    userBookingService.cancelBooking(ticketId);
                    break;
                case 7:
                    System.exit(0);
                default:
                    break;

            }
        }
    }
}
