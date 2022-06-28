package com.home365.airlines.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
@FieldDefaults(level = PRIVATE)
public class DestinationDto {

    @Schema(description = "Destination name", example = "Łódź")
    @NotNull(message = "Name cannot be empty")
    String name;
    NewLocationDto newLocationDto;
}
