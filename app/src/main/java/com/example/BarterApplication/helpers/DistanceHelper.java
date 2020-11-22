package com.example.BarterApplication.helpers;

import android.graphics.Point;
import android.location.Location;

public class DistanceHelper {
    private final static double DISTANCE_EPSILON = 1e-6d;

    /**
     * @brief Euclidean distance
     * @param l1 first location
     * @param l2 second location
     * @return the euclidean distance ALONG THE SURFACE OF EARTH
     *
     * @note DOES NOT ACCOUNT FOR ALTITUDE
     * @todo THIS FUNCTION NEEDS A BETTER NAME
     */
    private static double euclidianDistanceBetweenLocations(Location l1, Location l2){
        double lat1 = l1.getLatitude();
        double lat2 = l2.getLatitude();
        double long1 = l1.getLongitude();
        double long2 = l2.getLongitude();
        double deltaLat = lat1 - lat2;
        double deltaLong = long1 - long2;
        return Math.hypot(deltaLat, deltaLong);
    }


    /**
     * @brief Epsilon comparison for floating point numbers
     * @param d1 first floating point number to compare
     * @param d2 second floating point number to compare
     * @return TRUE IF EQUAL WITHIN EPSILON, ELSE FALSE
     *
     * @note [WARNING] : If you think you can just do num1 == num2 you need to work with doubles a LOT more.
     *                   please do not remove this during some kind of refactor (carl)
     */
    private boolean doubleCmp(double d1, double d2){
        if(d1 - d2 < DISTANCE_EPSILON){
            return true;
        }
        else if(d2 - d1 < DISTANCE_EPSILON){
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * @brief Epsilon comparison against zero for floating point numbers
     * @param d1 the floating point number to compare against 0
     * @return true if within 0 +- EPSILON, otherwise false
     */
    private boolean doubleIsZero(double d1){
        return doubleCmp(d1, 0.0d);
    }

    /**
     * @brief Epsilon comparison for GPS coordinates of 2 locations
     * @param l1 first location
     * @param l2 second location
     * @return true if locations are equal within DISTANCE_EPSILON, else false
     */
    public boolean locationsAreEqual(Location l1, Location l2){
        double distance = euclidianDistanceBetweenLocations(l1, l2);
        if(doubleIsZero(distance)){
            return true;
        }
        else {
            return false;
        }
    }
}
