package ticket.Booking.services;


import ticket.Booking.enities.Train;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {
    private static final TrainDAO trainDAO = new TrainDAO();

    public List<Train> loadTrains(){
        return trainDAO.getAllTrains();
    }

    public List<Train> searchTrains(String source,String destination){
        List<Train> trains = loadTrains();
        return trains.stream().filter(train -> validTrain(train,source,destination)).collect(Collectors.toList());
    }

    public void addTrain(Train newTrain){
        trainDAO.insertTrain(newTrain);
    }

    public void updateTrain(Train updatedTrain){
        trainDAO.updateTrain(updatedTrain);
    }

    private boolean validTrain(Train train,String source,String destination){
        List<String> stationOrder = train.getStations();
        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());
        return sourceIndex!=-1 && destinationIndex!=-1 && sourceIndex < destinationIndex;
    }



}
