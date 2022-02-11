package org.catalysts.commengage.util;

public class GeolocationUtil {
    public static double round(String locationElement) {
        double aDouble = Double.parseDouble(locationElement);
        return round(aDouble);
    }

    public static double round(double locationElement) {
        return Math.round(locationElement * 100.0) / 100.0;
    }
}
