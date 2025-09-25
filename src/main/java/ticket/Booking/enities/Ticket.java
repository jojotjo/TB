package ticket.Booking.enities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.class)
public class Ticket {
    private String ticketId;
    private String trainId;
    private String source;
    private String destination;
    private String seatNo;
    private String bookingDate;
    private String userId;
    private Train train;

    public Ticket(String ticketId,String trainId,String source,String destination,String seatNo,String bookingDate,String userId,Train train) {
        this.ticketId=ticketId;
        this.trainId=trainId;
        this.source=source;
        this.destination=destination;
        this.seatNo=seatNo;
        this.bookingDate=bookingDate;
        this.userId=userId;
        this.train=train;
    }

    public Ticket() {}


    public  String getTicketInfo(){
        return String.format("Ticket ID: %s | Train ID: %s | %s -> %s | Seat: %s | Date: %s | User: %s",ticketId, trainId, source, destination, seatNo, bookingDate, userId);
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getTrainId() { return trainId; }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public  String getSeatNo() { return seatNo; }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getUserId() {
        return userId;
    }

    public Train getTrain() {
        return train;
    }



    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setTrainId(String trainId) { this.trainId = trainId; }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }

    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
