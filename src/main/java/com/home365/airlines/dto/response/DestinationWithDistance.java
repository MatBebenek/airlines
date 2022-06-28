package com.home365.airlines.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
@FieldDefaults(level = PRIVATE)
public class DestinationWithDistance {
    String destinationName;
    Double distanceInKilometers;
}
