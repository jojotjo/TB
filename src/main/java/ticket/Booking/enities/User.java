package ticket.Booking.enities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User {
    private String userId;
    private String name;
    private String password;
    private String hashedPassword;
    private List<Ticket> ticketsBooked;
    public User(String name,String password,String hashedPassword,List<Ticket> ticketsBooked,String userId){
        this.name=name;
        this.password=password;
        this.hashedPassword=hashedPassword;
        this.ticketsBooked=ticketsBooked;
        this.userId=userId;
    }

    public User(){}

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getHashedPassword(){
        return hashedPassword;
    }

    public List<Ticket> getTicketsBooked(){
        return ticketsBooked;
    }

    public void printTickets(){
        for (Ticket ticket : ticketsBooked) {
            System.out.println(ticket.getTicketInfo());
        }
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId=userId;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setHashedPassword(String hashedPassword){
        this.hashedPassword=hashedPassword;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked){
        this.ticketsBooked=ticketsBooked;
    }
}
