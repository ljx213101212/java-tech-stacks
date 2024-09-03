package com.ljx213101212.apache_kafka.task2.model;

import lombok.Data;

@Data
public class Coordinate {
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private double latitude;
    private double longitude;
}
