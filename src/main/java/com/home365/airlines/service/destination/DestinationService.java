package com.home365.airlines.service.destination;

import com.home365.airlines.dto.response.DestinationWithDistance;

import java.util.List;

public interface DestinationService {
    List<DestinationWithDistance> computeDistancesForAllDestinations(Long airlineId);
    List<DestinationWithDistance> findAllReachableDestinations(Long airlineId);
}
