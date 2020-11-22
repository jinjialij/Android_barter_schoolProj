package com.example.BarterApplication.helpers;

import android.graphics.Point;
import android.location.Location;

import com.example.BarterApplication.Item;
import com.example.BarterApplication.SimpleLocation;

public class DistanceHelper {
    private final static double DISTANCE_EPSILON = 1e-6d;

    public static double  getDistanceToItem(Item i){
        Location myLocation = LocationHelper.getLocation();
        SimpleLocation mySimpleLocation = new SimpleLocation(myLocation.getLongitude(), myLocation.getLatitude());
        SimpleLocation itemLocation = i.getLocation();
        double distance = distanceInMeters(itemLocation, mySimpleLocation);

        /* Account for epsilon rounding */
        if(doubleIsZero(distance)){
            distance = 0.0d;
        }
        return distance;
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
    private static boolean doubleCmp(double d1, double d2){
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
    private static boolean doubleIsZero(double d1){
        return doubleCmp(d1, 0.0d);
    }

    /**
     * @brief compute the distance in meters along the
     * curvature of the earth between two Simplelocations
     *
     * @param l1 first SimpleLocation
     * @param l2 second SimpleLocation
     * @return distance in meters between the two locations (along curvature of earth)
     */
    private static double distanceInMeters(SimpleLocation l1, SimpleLocation l2){
        final int earthRadiusMetres = 6371;

        /* TRIG STUFF */
        double latDistance = Math.toRadians(l1.Latitude - l2.Latitude);
        double lonDistance = Math.toRadians(l1.Longitude - l2.Longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(l1.Latitude)) * Math.cos(Math.toRadians(l2.Latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        /* convert to meters */
        double distance = earthRadiusMetres * c * 1000;
        return distance;
    }

}
