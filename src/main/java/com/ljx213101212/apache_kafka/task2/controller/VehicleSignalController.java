package com.ljx213101212.apache_kafka.task2.controller;

import com.ljx213101212.apache_kafka.task2.model.VehicleSignal;
import com.ljx213101212.apache_kafka.task2.service.producer.VehicleSignalProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signal")
public class VehicleSignalController {

    @Autowired
    private VehicleSignalProducer vehicleSignalProducer;

    @PostMapping
    public ResponseEntity<?> sendSignal(@RequestBody VehicleSignal signal) throws InterruptedException {
        if (signal.getVehicleId() == null || signal.getCoordinates() == null) {
            return ResponseEntity.badRequest().body("Invalid signal data");
        }
        //Entry
        vehicleSignalProducer.produceSignal(signal);
        return ResponseEntity.ok().build();
    }

}
