package com.akhilesh.coffeemachine.model;

/**
 * Beverage counter
 */
public class BeverageCounters {
    private static int numberOfCounters=1;

    public static int getNumberOfCounters() {
        return numberOfCounters;
    }

    public static void setNumberOfCounters(int numberOfCounters) {
        if(numberOfCounters < 1){
            throw new IllegalArgumentException("Number of counters cannot be less than 1...");
        }
        BeverageCounters.numberOfCounters = numberOfCounters;
    }
}
