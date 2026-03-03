package com.jojotjo.ticketbooking.controller;

import com.jojotjo.ticketbooking.dto.TrainSearchRequest;
import com.jojotjo.ticketbooking.model.Train;
import com.jojotjo.ticketbooking.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
public class TrainController {
    private final TrainService trainService;

    public TrainController(TrainService trainService){
        this.trainService = trainService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<Train>> searchTrains(
            @Valid @RequestBody TrainSearchRequest request
    ) {
        List<Train> trains = trainService.searchTrains(request.getSource(), request.getDestination());
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/{trainNo}")
    public ResponseEntity<Train> getTrainByNumber(@PathVariable String trainNo) {
        Train train = trainService.getTrainByNumber(trainNo);
        return ResponseEntity.ok(train);
    }


    @GetMapping
    public ResponseEntity<List<Train>> getAllTrains() {
        return ResponseEntity.ok(trainService.getAllTrains());
    }



}
