package com.home365.airlines.helper;

import com.home365.airlines.entity.Airline;
import com.home365.airlines.entity.Destination;

import java.math.BigDecimal;

public class AirlineHelper {

    public static final String AIRLINE_NAME = "KLM";
    public static final String OTHER_AIRLINE_NAME = "LOT";
    public static final BigDecimal AIRLINE_INITIAL_BUDGET = new BigDecimal("1000.5");

    public static Airline anAirline(Destination homeBase) {
        Airline airline = Airline.builder()
                .airlineName(AIRLINE_NAME)
                .budget(AIRLINE_INITIAL_BUDGET)
                .homeBase(homeBase)
                .build();
        return airline;
    }

    public static Airline anOtherAirline(Destination homeBase) {
        Airline airline = Airline.builder()
                .airlineName(OTHER_AIRLINE_NAME)
                .budget(AIRLINE_INITIAL_BUDGET)
                .homeBase(homeBase)
                .build();
        return airline;
    }

}
