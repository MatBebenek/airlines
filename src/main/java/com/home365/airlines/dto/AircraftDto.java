package com.home365.airlines.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDto {
    private Long ownerId;
    private String name;
    private Double price;
    private Double maxDistance;
}
