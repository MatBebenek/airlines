package com.home365.airlines.controller;

import com.home365.airlines.dto.request.NewAirlineDto;
import com.home365.airlines.dto.request.NewLocationDto;
import com.home365.airlines.exception.InvalidArgumentException;
import com.home365.airlines.exception.ResourceNotFoundException;
import com.home365.airlines.entity.Airline;
import com.home365.airlines.entity.Destination;
import com.home365.airlines.entity.Location;
import com.home365.airlines.repository.AirlineRepository;
import com.home365.airlines.repository.DestinationRepository;
import com.home365.airlines.dto.response.AirlinesWithBudget;
import com.home365.airlines.dto.response.DestinationWithDistance;
import com.home365.airlines.service.airline.AirlineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.apache.lucene.spatial.util.GeoDistanceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @GetMapping(value = "/airlines")
    public List<AirlinesWithBudget> findAllAirlinesAndTheirBudget() {
        log.info("Receiving all airlines with their current balance");
        return airlineService.findAllAirlines();
    }

    @PostMapping(value = "/create-airline")
    public Airline createNewAirline(@RequestBody NewAirlineDto newAirlineDto) {
        log.debug("Creating new airline " + newAirlineDto.getAirlineName());
        return airlineService.createNewAirline(newAirlineDto);
    }
}
