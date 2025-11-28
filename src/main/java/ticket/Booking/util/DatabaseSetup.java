package ticket.Booking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    private static final String URL = "jdbc:sqlite:/D:/IntellIJ_IDEA_projects/TicketBooking/ticketbooking.db";

    public static void initializeDatabase() throws SQLException {
        try(Connection conn = DriverManager.getConnection(URL)){
            if(conn!=null) {
                System.out.println("Connected to SQLite database");
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("DROP TABLE IF EXISTS bookings");
                    stmt.execute("DROP TABLE IF EXISTS tickets");
                    stmt.execute("DROP TABLE IF EXISTS seats");
                    stmt.execute("DROP TABLE IF EXISTS trains");
                    stmt.execute("DROP TABLE IF EXISTS users");

                    String createUsersTable = """
                            CREATE TABLE IF NOT EXISTS users(
                                user_id TEXT UNIQUE NOT NULL,
                                name TEXT UNIQUE NOT NULL,
                                hashed_password TEXT NOT NULL
                            );
                            """;

                    String createTicketsTable = """
                            CREATE TABLE IF NOT EXISTS tickets (
                                ticket_id TEXT PRIMARY KEY,
                                user_id TEXT NOT NULL,
                                train_id TEXT NOT NULL,
                                source TEXT NOT NULL,
                                destination TEXT NOT NULL,
                                seat_no TEXT NOT NULL,
                                booking_date TIMESTAMP NOT NULL,
                                FOREIGN KEY (user_id) REFERENCES users(user_id),
                                FOREIGN KEY (train_id) REFERENCES trains(train_id)
                            );
                            """;



                    String createTrainsTable = """
                            CREATE TABLE IF NOT EXISTS trains(
                                train_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                train_no TEXT UNIQUE NOT NULL,
                                train_name TEXT NOT NULL,
                                source TEXT NOT NULL,
                                destination TEXT NOT NULL,
                                total_seats INT DEFAULT 0,
                                booked_seats INT DEFAULT 0,
                                waiting_seats INT DEFAULT 0
                            );
                            """;

                    String createBookingsTable = """
                            CREATE TABLE IF NOT EXISTS bookings(
                                booking_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                user_id INT,
                                train_id INT,
                                seat_id INT,
                                booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (user_id) REFERENCES users(user_id),
                                FOREIGN KEY (train_id) REFERENCES trains(train_id),
                                FOREIGN KEY (seat_id) REFERENCES seats(seat_id)
                            );
                            """;

                    String createSeatsTable = """
                            CREATE TABLE IF NOT EXISTS seats(
                                seat_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                train_id INT,
                                seat_number VARCHAR(10),
                                is_booked BOOLEAN DEFAULT FALSE,
                                FOREIGN KEY (train_id) REFERENCES trains(train_id)
                            );
                            """;

                    String createStationScheduleTable = """
                            CREATE TABLE IF NOT EXISTS station_schedules (
                                schedule_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                train_id INTEGER,
                                station_order INTEGER,
                                station_name TEXT NOT NULL,
                                arrival_time TEXT,
                                departure_time TEXT,
                                FOREIGN KEY (train_id) REFERENCES trains(train_id)
                            );
                            """;


                    stmt.execute(createUsersTable);
                    stmt.execute(createTrainsTable);
                    stmt.execute(createTicketsTable);
                    stmt.execute(createBookingsTable);
                    stmt.execute(createSeatsTable);
                    stmt.execute(createStationScheduleTable);


                    System.out.println("All tables created (if not already).");
                }
            }
        }catch (SQLException e){
            System.err.println("Database initialization failed.");
            e.printStackTrace();
        }
    }
}
