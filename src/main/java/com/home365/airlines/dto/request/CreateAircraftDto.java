package com.home365.airlines.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
@FieldDefaults(level = PRIVATE)
public class CreateAircraftDto {

    @Schema(description = "Aicraft owner's id.", example = "1")
    @NotNull(message = "Owner's id cannot be empty")
    Long ownerId;

    @Schema(description = "Aircraft's name", example = "Boeing")
    @NotNull(message = "Aircraft cannot be empty")
    String aircraftName;

    @Schema(description = "Aircraft's default price", example = "250.00")
    @NotNull(message = "Default price cannot be empty")
    @Positive(message = "Default price cannot be negative or zero")
    BigDecimal price;

    @Schema(description = "Aircraft's max distance", example = "525.00")
    @NotNull(message = "Max distance cannot be empty")
    @Positive(message = "Max distance cannot be negative or zero")
    BigDecimal maxDistance;
}
