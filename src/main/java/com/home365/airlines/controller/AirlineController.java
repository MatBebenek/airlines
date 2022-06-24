package com.home365.airlines.controller;

import com.home365.airlines.dto.AirlineDto;
import com.home365.airlines.model.Airline;
import com.home365.airlines.model.Destination;
import com.home365.airlines.model.Location;
import com.home365.airlines.repositories.AirlineRepository;
import com.home365.airlines.responses.AirlinesBudget;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AirlineController {

    private static final Logger LOG = LoggerFactory.getLogger(AirlineController.class);
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    AirlineRepository airlineRepository;

    @GetMapping(value = "/airlines")
    public ResponseEntity<List<AirlinesBudget>> findAllAirlines() {
        LOG.info("Receiving all airlines with their current balance");
        List<Airline> airlines = airlineRepository.findAll();
        List<AirlinesBudget> result = airlines.stream().map(airline -> new AirlinesBudget(airline.getName(), airline.getBudget())).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/addAirline")
    public ResponseEntity<Airline> addNewAirline(@RequestBody AirlineDto airlineDto) {
        Location newLocation = modelMapper.map(airlineDto.getHomeBase().getLocationDto(), Location.class);
        Destination newHomeBase = new Destination(airlineDto.getHomeBase().getName(), newLocation);
        Airline newAirline = new Airline(airlineDto.getName(), airlineDto.getBudget(), newHomeBase);
        airlineRepository.save(newAirline);
        LOG.info("Added new airline: " + newAirline);
        return new ResponseEntity<Airline>(newAirline, HttpStatus.CREATED);
    }
}
