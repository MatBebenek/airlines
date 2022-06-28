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
public class NewAirlineDto {

    @Schema(description = "Airline's name", example = "Reymont")
    @NotNull(message = "Name cannot be empty")
    private String airlineName;

    @Schema(description = "Airline's initial budget", example = "525000.00")
    @NotNull(message = "Budget cannot be empty")
    @Positive(message = "Budget cannot be negative or zero")
    private BigDecimal budget;

    private DestinationDto homeBase;
}
