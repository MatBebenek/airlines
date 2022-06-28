package com.home365.airlines.controller;

import com.home365.airlines.dto.response.DestinationWithDistance;
import com.home365.airlines.service.destination.DestinationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping(value = "/distances")
    public List<DestinationWithDistance> computeDistances(@RequestParam Long airlineId) {
        log.debug("Computing distances from home location to all destinations.");
        return destinationService.computeDistancesForAllDestinations(airlineId);
    }

    @GetMapping(value = "/destinations")
    public List<DestinationWithDistance> findAllReachableDestinations(@RequestParam Long airlineId) {
        log.debug("Getting all destinations that are reachable for airline with id: " + airlineId);
        return destinationService.findAllReachableDestinations(airlineId);
    }
}
