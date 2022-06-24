package com.home365.airlines.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineDto {
    private String airlineName;
    private Double budget;
    private DestinationDto homeBase;
}
