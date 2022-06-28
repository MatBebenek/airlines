package com.home365.airlines.service.airline;

import com.home365.airlines.dto.request.NewAirlineDto;
import com.home365.airlines.dto.request.NewLocationDto;
import com.home365.airlines.dto.response.AirlinesWithBudget;
import com.home365.airlines.entity.Airline;
import com.home365.airlines.entity.Destination;
import com.home365.airlines.entity.Location;
import com.home365.airlines.exception.InvalidArgumentException;
import com.home365.airlines.exception.ResourceNotFoundException;
import com.home365.airlines.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    public List<AirlinesWithBudget> findAllAirlines() {
        List<Airline> airlines = airlineRepository.findAll();
        if (airlines.isEmpty()) {
            throw new ResourceNotFoundException("Any airline");
        } else {
            List<AirlinesWithBudget> result = airlines.stream().map(airline -> new AirlinesWithBudget(airline.getAirlineName(), airline.getBudget())).collect(Collectors.toList());
            return result;
        }
    }

    @Override
    public Airline createNewAirline(NewAirlineDto newAirlineDto) {
        Location newLocation = validateLocation(newAirlineDto.getHomeBase().getNewLocationDto());
        Destination newHomeBase = new Destination(newAirlineDto.getHomeBase().getName(), newLocation);
        Airline newAirline = new Airline(newAirlineDto.getAirlineName(), newAirlineDto.getBudget(), newHomeBase);
        airlineRepository.save(newAirline);
        return newAirline;
    }

    public Location validateLocation(NewLocationDto newLocationDto) {
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
