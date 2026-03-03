package com.jojotjo.ticketbooking.service;

import com.jojotjo.ticketbooking.model.Train;
import com.jojotjo.ticketbooking.repository.TrainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional  // ✅ ADDED
public class TrainService {

    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Transactional(readOnly = true)  // ✅ ADDED
    public List<Train> searchTrains(String source, String destination) {
        String src = source.toLowerCase().trim();
        String dest = destination.toLowerCase().trim();

        System.out.println("\n🔍 Searching trains:");
        System.out.println("  From: " + src);
        System.out.println("  To: " + dest);

        List<Train> allTrains = trainRepository.findAll();
        System.out.println("  Total trains in DB: " + allTrains.size());

        List<Train> results = allTrains.stream()
                .filter(train -> train.passesThrough(src, dest))
                .toList();

        System.out.println("  Trains found: " + results.size());
        System.out.println("✅ Search complete\n");

        return results;
    }

    public Train getTrainByNumber(String trainNumber) {
        return trainRepository.findByTrainNumber(trainNumber)
                .orElseThrow(() -> new RuntimeException("Train not found with number: " + trainNumber));
    }

    public Train getTrainById(Long trainId) {
        return trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found with ID: " + trainId));
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }
}