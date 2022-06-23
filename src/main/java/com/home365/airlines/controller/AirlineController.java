package com.home365.airlines.controller;

import com.home365.airlines.dto.AirlineDto;
import com.home365.airlines.dto.DestinationDto;
import com.home365.airlines.model.Airline;
import com.home365.airlines.model.Destination;
import com.home365.airlines.model.Location;
import com.home365.airlines.repositories.AirlineRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AirlineController {

    private static final Logger LOG = LoggerFactory.getLogger(AirlineController.class);

    @Autowired
    AirlineRepository airlineRepository;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping(value = "/airlines")
    public List<Airline> findAllAirlines() {
        LOG.info("Receiving all airlines with their current balance");
        return airlineRepository.findAll();
    }

    @PostMapping(value = "/addAirline")
    public Airline addNewAirline(@RequestBody AirlineDto airlineDto) {
        AirlineDto newAirlineDto = new AirlineDto(airlineDto.getName(), airlineDto.getBudget(), airlineDto.getHomeBase());
        Airline newAirline = modelMapper.map(newAirlineDto, Airline.class);
        LOG.info("Added new airline: " + newAirline);
        return airlineRepository.save(newAirline);
    }
}
