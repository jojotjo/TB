package ticket.Booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.Booking.enities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {
    private List<Train> trainList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRAIN_DB_PATH = "src/main/java/ticketBooking/localDB/trains.json";

    public  TrainService() throws IOException{
        loadTrains();
    }

    public List<Train> loadTrains() throws  IOException{
        File trains = new File(TRAIN_DB_PATH);
        return objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrains(String source,String destination){
        return trainList.stream().filter(train->vaildTrain(train,source,destination)).collect(Collectors.toList());
    }

}
