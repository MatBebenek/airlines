package com.home365.airlines.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
@FieldDefaults(level = PRIVATE)
public class NewLocationDto {

    @Schema(description = "Location latitude", example = "51.7833")
    @NotNull(message = "Latitude cannot be empty")
    BigDecimal latitude;

    @Schema(description = "Location longitude", example = "19.4667")
    @NotNull(message = "Longitude cannot be empty")
    BigDecimal longitude;
}
