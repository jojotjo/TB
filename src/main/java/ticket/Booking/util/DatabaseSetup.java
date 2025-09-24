package ticket.Booking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    private static final String URL = "jdbc:sqlite:ticketbooking.db";

    public static void initializeDatabase() throws SQLException {
        try(Connection conn = DriverManager.getConnection(URL)){
            if(conn!=null) {
                System.out.println("Connected to SQLite database");
                try (Statement stmt = conn.createStatement()) {
                    String createUsersTable = """
                            CREATE TABLE IF NOT EXITS users(
                                id INTEGER PRIMARY KEY AUTOINCREMENT,
                                user_is TEXT UNIQUE NOT NULL,
                                username TEXT UNIQUE NOT NULL,
                                password_hash TEXT NOT NULL,
                            );
                            """;

                    String createTrainsTable = """
                            CREATE TABLE IF NOT EXISTS trains(
                                id Integer PRIMARY KEY AUTOINCREMENT,
                                train_id TEXT UNIQUE NOT NULL,
                                train_name TEXT UNIQUE NOT NULL,
                                source TEXT NOT NULL,
                                destination TEXT NOT NULL,
                            );
                            """;

                    String createBookingsTable = """
                            CREATE TABLE IF NOY EXISTS bookings(
                                id INTEGER PRIMARY KEY AUTOINCREMENT,
                                booking_id TEXT UNIQUE NOT NULL,
                                user_id TEXT NOT NULL,
                                train_id TEXT NOT NULL,
                                seat_row INTEGER NOT NULL,
                                seat_col INTEGER NOT NULL,
                                booking_time TEXT DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (user_id) REFERENCES users(user_id),
                                FOREIGN KEY (train_id) REFERENCES trains(train_id)
                            );
                            """;

                    String createSeatsTable = """
                            CREATE TABLE IF NOT EXISTS seats(
                                id INTEGER PRIMARY KEY AUTOINCREMENT,
                                train_id TEXT NOT NULL,
                                row INTEGER NOT NULL,
                                col INTEGER NOT NULL,
                                is_booked INTEGER DEFAULT 0,
                                FOREIGN KEY (train_id) REFERENCES trains(train_id)
                            );
                            """;

                    stmt.execute(createUsersTable);
                    stmt.execute(createTrainsTable);
                    stmt.execute(createBookingsTable);
                    stmt.execute(createSeatsTable);

                    System.out.println("All tables created (if not already).");
                }
            }
        }catch (SQLException e){
            System.err.println("Database initialization failed.");
            e.printStackTrace();
        }
    }
}
