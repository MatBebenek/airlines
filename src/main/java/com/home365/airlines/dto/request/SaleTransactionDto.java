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
public class SaleTransactionDto {

    @Schema(description = "Aircraft to be sold", example = "1")
    @NotNull(message = "Aircraft's id cannot be empty")
    Long aircraftId;

    @Schema(description = "Buyer's id", example = "1")
    @NotNull(message = "Buyer's id cannot be empty")
    Long buyerId;
}
