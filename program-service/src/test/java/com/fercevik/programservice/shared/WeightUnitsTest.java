package com.fercevik.programservice.shared;

import org.junit.jupiter.api.Test;

public class WeightUnitsTest {

    private static final double epsilon = 0.00001;

    @Test
    public void testConvertKgFromLb() {
        var units = WeightUnits.POUNDS;
        assert Math.abs(units.convertToOther(10) - 4.53592) < epsilon;
    }

    @Test
    public void testConvertLbFromKg() {
        var units = WeightUnits.KILOGRAMS;
        assert Math.abs(units.convertToOther(10) - 22.0462) < epsilon;
    }
}
