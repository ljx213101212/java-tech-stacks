package com.ljx213101212.apache_kafka.task2.util;

public class LocationUtil {

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Radius of the earth in meters
        final int R = 6371000;

        // Convert degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Differences in coordinates
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine formula
        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate distance
        return R * c / 1000;
    }

    public static String formatDistance(double distanceInKilometers) {
        if (distanceInKilometers >= 1000) {
            return String.format("%.1f kilometers", distanceInKilometers);
        } else {
            double distanceInMeters = distanceInKilometers * 1000;
            return String.format("%.1f meters", distanceInMeters);
        }
    }
}
