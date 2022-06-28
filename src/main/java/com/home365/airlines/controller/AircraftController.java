package com.home365.airlines.controller;

import com.home365.airlines.dto.request.CreateAircraftDto;
import com.home365.airlines.dto.request.SaleTransactionDto;
import com.home365.airlines.entity.Aircraft;
import com.home365.airlines.service.aircraft.AircraftService;
import com.home365.airlines.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;
    private final TransactionService transactionService;

    @GetMapping(value = "/{id}/aircraft")
    public List<Aircraft> findAllAircraft(@RequestParam Long airlineId) {
        log.debug("Getting all aircraft for airline with id: " + airlineId);
        return aircraftService.getAllAircrafts(airlineId);
    }

    @PostMapping(value = "/create-aircraft")
    public Aircraft createAircraftForAirline(@Valid @RequestBody CreateAircraftDto createAircraftDto) {
        log.debug("Create new aircraft for airline with id: " + createAircraftDto.getOwnerId());
        return aircraftService.createAircraft(createAircraftDto);
    }

    @PutMapping(value = "/sell-aircraft")
    public String sellAircraft(@Valid @RequestBody SaleTransactionDto saleTransactionDto) {
        log.debug("Initialised the selling transaction");
        return "Airplane with id: " + saleTransactionDto.getAircraftId() + " has been sold for " + transactionService.sellAircraft(saleTransactionDto);
    }
}
