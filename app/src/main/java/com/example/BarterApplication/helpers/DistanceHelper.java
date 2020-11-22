package com.example.BarterApplication.helpers;

import android.graphics.Point;
import android.location.Location;

import com.example.BarterApplication.Item;
import com.example.BarterApplication.SimpleLocation;

public class DistanceHelper {
    private final static double DISTANCE_EPSILON = 1e-6d;

    public static double  getDistanceToItem(Item i){
        double distance = 0;
        Location myLocation = LocationHelper.getLocation();
//        SimpleLocation mySimpleLocation = new SimpleLocation(myLocation.getLongitude(), myLocation.getLatitude());
        //SimpleLocation itemLocation = i.getLocation();

        /* Account for representational rounding */
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


}
