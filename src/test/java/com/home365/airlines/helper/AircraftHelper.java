package com.home365.airlines.helper;


import com.home365.airlines.entity.Aircraft;
import com.home365.airlines.entity.Airline;

import java.math.BigDecimal;

public class AircraftHelper {
    public static final BigDecimal AIRCRAFT_INITIAL_PRICE = new BigDecimal("21.37");
    public static final BigDecimal AIRCRAFT_AMX_DISTANCE_KM = new BigDecimal("700");

    public static Aircraft anAircraft(Airline owner) {
        return Aircraft.builder()
                .owner(owner)
                .price(AIRCRAFT_INITIAL_PRICE)
                .maxDistance(AIRCRAFT_AMX_DISTANCE_KM)
                .build();
    }

}
