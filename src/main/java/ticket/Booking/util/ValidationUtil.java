package ticket.Booking.util;

public class ValidationUtil {
    public static boolean isValidUsername(String username) {
        return username != null && username.matches("^[a-zA-z0-9_]{3,}$");
    }

    public static boolean isValidPassword(String password) {
        if(password == null) return false;
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(passwordRegex);
    }
}
