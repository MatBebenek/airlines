package com.home365.airlines.service.aircraft;

import com.home365.airlines.dto.request.CreateAircraftDto;
import com.home365.airlines.entity.Aircraft;
import com.home365.airlines.entity.Airline;
import com.home365.airlines.exception.InvalidArgumentException;
import com.home365.airlines.exception.ResourceNotFoundException;
import com.home365.airlines.repository.AircraftRepository;
import com.home365.airlines.repository.AirlineRepository;
import com.home365.airlines.service.time.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AircraftServiceImpl implements AircraftService {
    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;

    @Override
    public Aircraft createAircraft(CreateAircraftDto createAircraftDto) {
        Aircraft newAircraft = new Aircraft();
        newAircraft.setAircraftName(createAircraftDto.getAircraftName());
        Airline owner = airlineRepository.getEntity(createAircraftDto.getOwnerId());
        newAircraft.setPrice(createAircraftDto.getPrice());
        newAircraft.setMaxDistance(createAircraftDto.getMaxDistance());
        newAircraft.setOwner(owner);
        aircraftRepository.save(newAircraft);
        return newAircraft;
    }

    @Override
    public List<Aircraft> getAllAircrafts(Long airlineId) {
        Airline airline = airlineRepository.getEntity(airlineId);
        return airline.getAircraftList();
    }
}
