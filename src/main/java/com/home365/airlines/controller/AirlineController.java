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

@RestController
public class AirlineController {

    private static final Logger LOG = LoggerFactory.getLogger(AirlineController.class);
    @Autowired
    AirlineRepository airlineRepository;

    @Autowired
    DestinationRepository destinationRepository;

    @GetMapping(value = "/airlines")
    public List<AirlinesWithBudget> findAllAirlinesAndTheirBudget() {
        LOG.info("Receiving all airlines with their current balance");
        List<Airline> airlines = airlineRepository.findAll();
        if (airlines.isEmpty()) {
            throw new ResourceNotFoundException("Any airline");
        } else {
            List<AirlinesWithBudget> result = airlines.stream().map(airline -> new AirlinesWithBudget(airline.getAirlineName(), airline.getBudget())).collect(Collectors.toList());
            return result;
        }
    }

    @PostMapping(value = "/create-airline")
    public Airline createNewAirline(@RequestBody NewAirlineDto newAirlineDto) {
        Location newLocation = validatedLocation(newAirlineDto.getHomeBase().getNewLocationDto());
        LOG.debug("New location created: " + newLocation);
        Destination newHomeBase = new Destination(newAirlineDto.getHomeBase().getName(), newLocation);
        LOG.debug("New destination created: " + newHomeBase);
        Airline newAirline = new Airline(newAirlineDto.getAirlineName(), newAirlineDto.getBudget(), newHomeBase);
        airlineRepository.save(newAirline);
        LOG.info("Created new airline: " + newAirline);
        return newAirline;
    }

    @GetMapping(value = "/distances")
    public List<DestinationWithDistance> computeDistancesForAllDestinations(@RequestParam Long airlineId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(() -> new ResourceNotFoundException("Airline", "id", airlineId));
        List<DestinationWithDistance> distances = distanceCalculation(airline);
        return distances;
    }

    @GetMapping(value = "/destinations")
    public List<DestinationWithDistance> findAllReachableDestinations(@RequestParam Long airlineId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(() -> new ResourceNotFoundException("Airline", "id", airlineId));
        Optional<BigDecimal> maxDistance = airline.getAircraftList().stream()
                .map(a -> a.getMaxDistance())
                .max(Comparator.naturalOrder());
        if (maxDistance.isEmpty()) {
            throw new NoSuchElementException("Maximum distance for airline not found");
        } else {
            List<DestinationWithDistance> distances = distanceCalculation(airline).stream().filter(distance -> BigDecimal.valueOf(distance.getDistanceInKilometers()).compareTo(maxDistance.get()) < 0).collect(Collectors.toList());
            return distances;
        }
    }

    public List<DestinationWithDistance> distanceCalculation(Airline airline) {
        List<Destination> allDestinations = destinationRepository.findAll();
        if (allDestinations.isEmpty()) {
            throw new ResourceNotFoundException("Any destination");
        } else {
            List<DestinationWithDistance> distances = allDestinations.stream().map(destination ->
                    new DestinationWithDistance(destination.getDestinationName(),
                            Precision.round(GeoDistanceUtils.haversin(airline.getHomeBase().getLocation().getLatitude().doubleValue(),
                                    airline.getHomeBase().getLocation().getLongitude().doubleValue(),
                                    destination.getLocation().getLatitude().doubleValue(),
                                    destination.getLocation().getLongitude().doubleValue()) / 1000, 2)
                    )).collect(Collectors.toList());
            return distances;
        }
    }

    public Location validatedLocation(NewLocationDto newLocationDto) {
        if (isBetween(newLocationDto.getLatitude(), BigDecimal.valueOf(-90), BigDecimal.valueOf(90))) {
            if (isBetween(newLocationDto.getLongitude(), BigDecimal.valueOf(-180), BigDecimal.valueOf(180))) {
                return new Location(newLocationDto.getLatitude(), newLocationDto.getLongitude());
            } else {
                throw new InvalidArgumentException("Location", "longitude", newLocationDto.getLongitude());
            }
        } else {
            throw new InvalidArgumentException("Location", "latitude", newLocationDto.getLatitude());
        }
    }

    public static <T extends Comparable<T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }
}
