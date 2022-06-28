package com.home365.airlines.service.destination;

import com.home365.airlines.dto.request.NewLocationDto;
import com.home365.airlines.dto.response.DestinationWithDistance;
import com.home365.airlines.entity.Airline;
import com.home365.airlines.entity.Destination;
import com.home365.airlines.entity.Location;
import com.home365.airlines.exception.InvalidArgumentException;
import com.home365.airlines.exception.ResourceNotFoundException;
import com.home365.airlines.repository.AirlineRepository;
import com.home365.airlines.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.apache.lucene.spatial.util.GeoDistanceUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public List<DestinationWithDistance> computeDistancesForAllDestinations(Long airlineId) {
        Airline airline = airlineRepository.getEntity(airlineId);
        return distanceCalculation(airline);
    }

    @Override
    public List<DestinationWithDistance> findAllReachableDestinations(Long airlineId) {
        Airline airline = airlineRepository.getEntity(airlineId);
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
}
