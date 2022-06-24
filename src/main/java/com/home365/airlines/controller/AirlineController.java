package com.home365.airlines.controller;

import com.home365.airlines.dto.AirlineDto;
import com.home365.airlines.exceptions.ResourceNotFoundException;
import com.home365.airlines.model.Aircraft;
import com.home365.airlines.model.Airline;
import com.home365.airlines.model.Destination;
import com.home365.airlines.model.Location;
import com.home365.airlines.repositories.AirlineRepository;
import com.home365.airlines.repositories.DestinationRepository;
import com.home365.airlines.responses.AirlinesBudget;
import com.home365.airlines.responses.DestinationDistances;
import org.apache.commons.math3.util.Precision;
import org.apache.lucene.spatial.util.GeoDistanceUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class AirlineController {

    private static final Logger LOG = LoggerFactory.getLogger(AirlineController.class);
    @Autowired
    AirlineRepository airlineRepository;

    @Autowired
    DestinationRepository destinationRepository;
    ModelMapper modelMapper = new ModelMapper();

    @GetMapping(value = "/airlines")
    public ResponseEntity<List<AirlinesBudget>> findAllAirlines() {
        LOG.info("Receiving all airlines with their current balance");
        List<Airline> airlines = airlineRepository.findAll();
        if (airlines.isEmpty()) {
            LOG.debug("No airline was found. Empty list.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<AirlinesBudget> result = airlines.stream().map(airline -> new AirlinesBudget(airline.getAirlineName(), airline.getBudget())).collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/create-airline")
    public ResponseEntity<Airline> createNewAirline(@RequestBody AirlineDto airlineDto) {
        Location newLocation = modelMapper.map(airlineDto.getHomeBase().getLocationDto(), Location.class);
        Destination newHomeBase = new Destination(airlineDto.getHomeBase().getName(), newLocation);
        Airline newAirline = new Airline(airlineDto.getAirlineName(), airlineDto.getBudget(), newHomeBase);
        airlineRepository.save(newAirline);
        LOG.info("Added new airline: " + newAirline);
        return new ResponseEntity<Airline>(newAirline, HttpStatus.CREATED);
    }

    @GetMapping(value = "/distances")
    public ResponseEntity<List<DestinationDistances>> computeDistancesForAllDestinations(@RequestParam Long airlineId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(() -> new ResourceNotFoundException("Airline", "id", airlineId));
        List<DestinationDistances> distances = distanceCalculation(airline);
        return new ResponseEntity<>(distances, HttpStatus.OK);
    }

    @GetMapping(value = "/destinations")
    public ResponseEntity<List<DestinationDistances>> findAllReachableDestinations(@RequestParam Long airlineId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(() -> new ResourceNotFoundException("Airline", "id", airlineId));
        Double maxDistance = airline.getAircraftList().stream().max((Comparator.comparingDouble(Aircraft::getMaxDistance))).orElseThrow(NoSuchElementException::new).getMaxDistance();
        List<DestinationDistances> distances = distanceCalculation(airline).stream().filter(distance -> distance.getDistanceInKilometers() <= maxDistance).collect(Collectors.toList());
        return new ResponseEntity<>(distances, HttpStatus.OK);
    }

    public List<DestinationDistances> distanceCalculation(Airline airline) {
        List<Destination> allDestinations = destinationRepository.findAll();
        List<DestinationDistances> distances = allDestinations.stream().map(destination ->
                new DestinationDistances(destination.getDestinationName(),
                        Precision.round(GeoDistanceUtils.haversin(airline.getHomeBase().getLocation().getLatitude(),
                                airline.getHomeBase().getLocation().getLongitude(),
                                destination.getLocation().getLatitude(),
                                destination.getLocation().getLongitude()) / 1000, 2)
                )).collect(Collectors.toList());
        return distances;
    }
}
