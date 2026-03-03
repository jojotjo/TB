package ticket.Booking.enities;

public class StationSchedule {
    private String stationName;
    private String arrivalTime;
    private String departureTime;

    public StationSchedule() {

    }

    public StationSchedule(String stationName, String arrivalTime,String departureTime){
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName=stationName; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    @Override
    public String toString(){
        return "StationSchedule{" +
                "stationName= " + stationName +
                ", arrivalTime= " + arrivalTime +
                ", departureTime= " + departureTime +
                "}";
    }
}

