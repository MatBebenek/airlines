package com.home365.airlines.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DestinationDistances {
    private String destinationName;
    private Double distanceInKilometeres;
}