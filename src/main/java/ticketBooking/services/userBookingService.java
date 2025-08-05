package ticketBooking.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ticketBooking.enities.User;

public class userBookingService {
    private User user;
    private ObjectMapper objectMapper = new ObjectMapper();
    public  userBookingService(User user){
        this.user = user;
    }
}
