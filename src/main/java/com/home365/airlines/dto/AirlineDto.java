package com.home365.airlines.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineDto {
    private String name;
    private Double budget;
    private DestinationDto homeBase;
}
