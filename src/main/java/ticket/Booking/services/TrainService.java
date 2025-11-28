package ticket.Booking.services;


import ticket.Booking.enities.StationSchedule;
import ticket.Booking.enities.Train;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {
    private static final TrainDAO trainDAO = new TrainDAO();

    public List<Train> loadTrains(){
        return trainDAO.getAllTrains();
    }

    public List<Train> searchTrains(String source,String destination){
        List<Train> trains = loadTrains();
        return trains.stream().filter(train -> isValidRoute(train,source,destination)).collect(Collectors.toList());
    }

    public void addTrain(Train newTrain){
        trainDAO.insertTrain(newTrain);
    }

    public void updateTrain(Train updatedTrain){
        trainDAO.updateTrain(updatedTrain);
    }

    private boolean isValidRoute(Train train,String source,String destination){
        List<StationSchedule> schedules = train.getStationSchedules();
        int sourceIndex = -1;
        int destinationIndex = -1;

        for(int i=0;i< schedules.size();i++){
            String stationName = schedules.get(i).getStationName().toLowerCase();

            if(stationName.equals(source.toLowerCase())) {
                sourceIndex = i;
            }

            if(stationName.equals(destination.toLowerCase())){
                destinationIndex = i;
            }
        }

        return sourceIndex!=-1 && destinationIndex!=-1 && sourceIndex < destinationIndex;
    }

    public void displayTrainDetails(List<Train> trains, String source, String destination) {
        if (trains.isEmpty()) {
            System.out.println("No trains available for this route.");
            return;
        }

        int index = 1;

        for (Train train : trains) {
            System.out.println("\n" + index + ". 🚆 Train No: " + train.getTrainNo());
            System.out.println("Train Name: " + train.getTrainName());
            System.out.println("Route: " + source + " → " + destination);

            String departureTime = null, arrivalTime = null;

            for (StationSchedule s : train.getStationSchedules()) {
                if (s.getStationName().equalsIgnoreCase(source)) {
                    departureTime = s.getDepartureTime();
                }
                if (s.getStationName().equalsIgnoreCase(destination)) {
                    arrivalTime = s.getArrivalTime();
                }
            }

            System.out.println("Departure Time: " + (departureTime != null ? departureTime : "N/A"));
            System.out.println("Arrival Time: " + (arrivalTime != null ? arrivalTime : "N/A"));
            index++;
        }
    }


}
