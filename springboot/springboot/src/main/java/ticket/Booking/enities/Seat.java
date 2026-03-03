package ticket.Booking.enities;

public class Seat {
    private String seatNumber;
    private boolean isBooked;

    public Seat(String seatNumber,boolean isBooked){
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
    }

    public Seat() {

    }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public boolean isBooked() { return isBooked; }
    public void setBooked(boolean booked) { this.isBooked=booked; }

    @Override
    public String toString(){
        return "Seat{" +
                "seatNumber= " + seatNumber +
                ", isBooked= " + isBooked +
                "}";
    }
}
