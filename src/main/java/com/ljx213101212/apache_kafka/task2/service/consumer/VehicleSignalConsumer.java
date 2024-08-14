package com.ljx213101212.apache_kafka.task2.service.consumer;

import com.ljx213101212.apache_kafka.task2.model.Coordinate;
import com.ljx213101212.apache_kafka.task2.model.VehicleRecord;
import com.ljx213101212.apache_kafka.task2.model.VehicleSignal;
import com.ljx213101212.apache_kafka.task2.service.producer.VehicleDistanceProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.ljx213101212.apache_kafka.task2.config.Constants.*;
import static com.ljx213101212.apache_kafka.task2.util.LocationUtil.calculateDistance;

@Service
public class VehicleSignalConsumer {

    @Autowired
    private VehicleDistanceProducer vehicleDistanceProducer;

    private static final Logger log = LoggerFactory
            .getLogger(VehicleSignalConsumer.class);

    private final Map<String, VehicleRecord> vehicleDistanceMap = new ConcurrentHashMap<>();

    @Transactional("transactionManager")
    @KafkaListener(topics = INPUT_TOPIC, groupId = DISTANCE_CAL_GROUP_ID)
    public void listen(VehicleSignal signal) throws InterruptedException {
        VehicleRecord record = calculateVehicleRecord(signal);
        vehicleDistanceMap.put(record.getVehicleId(), record);
        vehicleDistanceProducer.produceDistance(record);
        log.info("[INPUT_TOPIC] - Consumer" + INPUT_TOPIC + " " + record.getVehicleId()+ " " + record.getTotalDistance());
    }

    private VehicleRecord calculateVehicleRecord(VehicleSignal signal) {
        // Retrieve the last known coordinates and distance for this vehicle
        VehicleRecord record = vehicleDistanceMap.getOrDefault(signal.getVehicleId(), new VehicleRecord(signal.getVehicleId(), Optional.empty(), 0.0));
        Optional<Coordinate> lastCoordinates = record.getLastKnownCoordinate();
        double currentDistance = record.getTotalDistance();
        // Calculate new distance if last coordinates are available
        if (lastCoordinates.isPresent()) {
            double newDistance = calculateDistance(signal.getCoordinates().getLatitude(), signal.getCoordinates().getLongitude(), lastCoordinates.get().getLatitude(), lastCoordinates.get().getLongitude());
            currentDistance += newDistance;
        }
        record.setTotalDistance(currentDistance);
        record.setLastKnownCoordinate(Optional.ofNullable(signal.getCoordinates()));
        return record;
    }
}
