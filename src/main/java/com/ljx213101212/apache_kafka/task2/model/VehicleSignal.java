package com.ljx213101212.apache_kafka.task2.model;

import lombok.Data;

@Data
public class VehicleSignal {
    private String vehicleId;

    public String getVehicleId() {
        return vehicleId;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    private Coordinate coordinates;
}
