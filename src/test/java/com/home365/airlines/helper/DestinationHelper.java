package com.home365.airlines.helper;

import com.home365.airlines.entity.Destination;
import com.home365.airlines.entity.Location;

import java.math.BigDecimal;

public class DestinationHelper {
    public static final String LOCATION_NAME = "Lublinek";
    public static final BigDecimal LOCATION_LATITUDE = new BigDecimal("51.721944");
    public static final BigDecimal LOCATION_LONGITUDE = new BigDecimal("19.398055");

    public static final String OTHER_LOCATION_NAME = "Warsaw";
    public static final BigDecimal OTHER_LOCATION_LATITUDE = new BigDecimal("52.229676");
    public static final BigDecimal OTHER_LOCATION_LONGITUDE = new BigDecimal("21.012229");

    public static Destination aDestination() {
        return Destination.builder()
                .destinationName(LOCATION_NAME)
                .location(Location.builder().latitude(LOCATION_LATITUDE).longitude(LOCATION_LONGITUDE).build())
                .build();
    }

    public static Destination anOtherDestination() {
        return Destination.builder()
                .destinationName(OTHER_LOCATION_NAME)
                .location(Location.builder().latitude(OTHER_LOCATION_LATITUDE).longitude(OTHER_LOCATION_LONGITUDE).build())
                .build();
    }
}
