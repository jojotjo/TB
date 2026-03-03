package ticket.Booking.enities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public class Train {
    private int trainId;
    private String trainNo;
    private String trainName;

    private String source;
    private String destination;

    private List<Seat> seats;
    private List<StationSchedule> stationSchedules;


    public Train(int trainId, String trainNo, String trainName,String source, String destination, List<Seat> seats, List<StationSchedule> stationSchedules) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.stationSchedules = stationSchedules;
    }

    public Train() {
    }

    ;

    public int getTrainId() {
        return trainId;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public String getTrainName() {
        return trainName;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<StationSchedule> getStationSchedules() {
        return stationSchedules;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void setStationSchedules(List<StationSchedule> stationSchedules) {
        this.stationSchedules = stationSchedules;
    }


    @Override
    public String toString() {
        return "Train{" +
                "trainId= " + trainId +
                ", trainNo= " + trainNo +
                ", trainName= " + trainName +
                "}";
    }
}
