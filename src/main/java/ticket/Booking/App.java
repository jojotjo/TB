package ticket.Booking;

import ticket.Booking.enities.*;
import ticket.Booking.services.SessionManager;
import ticket.Booking.services.TrainDAO;
import ticket.Booking.services.TrainService;
import ticket.Booking.util.DatabaseSeeder;
import ticket.Booking.util.DatabaseSetup;
import ticket.Booking.services.UserBookingService;
import ticket.Booking.util.UserServiceUtil;
import java.sql.SQLException;
import java.util.*;

public class App {
    public static void main(String[] args) throws SQLException {

        DatabaseSetup.initializeDatabase();
        DatabaseSeeder.seedData();


        System.out.println("Running Train Booking System");
        Scanner scanner = new Scanner(System.in);

        UserBookingService userBookingService = new UserBookingService();
        Train trainSelectedForBooking = new Train();

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
            switch (option) {
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = scanner.next();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = scanner.next();
                    boolean signUpResult = userBookingService.signUp(nameToSignUp, passwordToSignUp);
                    if (signUpResult) {
                        System.out.println("User signed up successfully");
                    } else {
                        System.out.println("Signed up failed. Please try again.");
                    }
                    break;

                case 2:
                    System.out.println("Enter the username to Login");
                    String nameToLogin = scanner.next();
                    if (SessionManager.isBlocked(nameToLogin)) {
                        System.out.println("Account locked due to too many failed attempts. Please try later.");
                        break;
                    }
                    System.out.println("Enter the password to Login");
                    String passwordToLogin = scanner.next();
                    boolean loginResult = userBookingService.loginUser(nameToLogin, passwordToLogin);
                    if (!loginResult) {
                        System.out.println("Login failed.");
                    }
                    break;

                case 3:
                    User currentUser = SessionManager.getCurrentUser();
                    if (currentUser == null) {
                        System.out.println("Please login first!");
                        break;
                    }

                    System.out.println("\n=== Your Bookings ===");
                    List<Ticket> userTickets = currentUser.getTicketsBooked();

                    if (userTickets == null || userTickets.isEmpty()) {
                        System.out.println("No bookings found.");
                    } else {
                        for (int i = 0; i < userTickets.size(); i++) {
                            System.out.println("\nBooking #" + (i + 1));
                            System.out.println(userTickets.get(i).getTicketInfo());
                        }
                    }
                    break;

                case 4:
                    System.out.println("Enter your source station:");
                    String source = scanner.next().trim().toLowerCase();

                    System.out.println("Enter your destination station:");
                    String destination = scanner.next().trim().toLowerCase();

                    System.out.println("DEBUG: Searching for '" + source + "' -> '" + destination + "'");

                    List<Train> allTrains = TrainDAO.getAllTrains();
                    System.out.println("DEBUG: Total trains in DB: " + allTrains.size());

                    if (allTrains.size() > 0) {
                        System.out.println("DEBUG: First train stations:");
                        for (StationSchedule s : allTrains.get(0).getStationSchedules()) {
                            System.out.println("  - '" + s.getStationName() + "'");
                        }
                    }

                    List<Train> trains = userBookingService.getTrains(source, destination);

                    if (trains.isEmpty()) {
                        System.out.println("No trains available for this route.");
                        break;
                    }

                    System.out.println("Available trains for " + source + " -> " + destination + ":");
                    for (int i = 0; i < trains.size(); i++) {
                        Train t = trains.get(i);
                        System.out.println("\nTrain #" + (i + 1));
                        System.out.println("Train No: " + t.getTrainNo());
                        System.out.println("Train Name: " + t.getTrainName());
                        System.out.print("Route: ");
                        for (StationSchedule s : t.getStationSchedules()) {
                            System.out.print(s.getStationName() + " -> ");
                        }
                        System.out.println("END");
                    }
                    break;

                case 5: {
                    currentUser = SessionManager.getCurrentUser();
                    if (currentUser == null) {
                        System.out.println("Please login first!");
                        break;
                    }

                    System.out.println("Enter Train Number to book:");
                    String trainNo = scanner.next();

                    Train trainToBook = TrainDAO.getTrainByNumber(trainNo);
                    if (trainToBook == null) {
                        System.out.println("Train not found!");
                        break;
                    }

                    System.out.println("Available seats for " + trainToBook.getTrainName() + ":");
                    List<Seat> seats = trainToBook.getSeats();
                    for (int i = 0; i < seats.size(); i++) {
                        Seat seat = seats.get(i);
                        if (i % 10 == 0) System.out.println();
                        System.out.print(seat.getSeatNumber() + (seat.isBooked() ? "[X] " : "[O] "));
                    }

                    System.out.println("\n\nEnter seat number to book:");
                    String seatNumber = scanner.next();

                    System.out.println("Enter source station:");
                    source = scanner.next();

                    System.out.println("Enter destination station:");
                    destination = scanner.next();

                    boolean booked = userBookingService.bookSeat(trainToBook, seatNumber, currentUser, source, destination);
                    if (booked) {
                        System.out.println("Seat booked successfully! Enjoy your journey!");
                    } else {
                        System.out.println("Booking failed. Seat may already be booked or invalid.");
                    }
                    break;
                }



                case 6:
                    User currentUserForCancel = SessionManager.getCurrentUser();
                    if (currentUserForCancel == null) {
                        System.out.println("Please login first!");
                        break;
                    }

                    System.out.println("Enter the ticket ID to cancel:");
                    String ticketId = scanner.next();

                    boolean cancelled = userBookingService.cancelBooking(ticketId, currentUserForCancel);
                    if (cancelled) {
                        System.out.println("Booking cancelled successfully!");
                    }
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option, please try again");
                    break;

            }
        }
    }
}
