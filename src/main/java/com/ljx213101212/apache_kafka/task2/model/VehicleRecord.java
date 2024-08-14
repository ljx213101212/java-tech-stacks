package com.ljx213101212.apache_kafka.task2.model;

import lombok.Data;

import java.util.Optional;

@Data
public class VehicleRecord {


    private String vehicleId;
    private Optional<Coordinate> lastKnownCoordinate;
    private double totalDistance;

    public VehicleRecord(String vehicleId, Optional<Coordinate> lastKnownCoordinate, double totalDistance) {
        this.vehicleId = vehicleId;
        this.lastKnownCoordinate = lastKnownCoordinate;
        this.totalDistance = totalDistance;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public Optional<Coordinate> getLastKnownCoordinate() {
        return lastKnownCoordinate;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setLastKnownCoordinate(Optional<Coordinate> lastKnownCoordinate) {
        this.lastKnownCoordinate = lastKnownCoordinate;
    }

}
