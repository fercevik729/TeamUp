package com.fercevik.programservice.shared;

public enum WeightUnits {
    KILOGRAMS, POUNDS;

    /**
     * Given a WeightUnits instance it converts a value
     * @param weight double representing a weight to be converted from the current units
     * @return converted weight in new units
     */
    public double convertToOther(double weight) {
        double rate = 2.20462;
        return this == KILOGRAMS ? weight * rate: weight * 1/rate;
    }
}
