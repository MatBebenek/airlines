package com.home365.airlines.service.aircraft;

import com.home365.airlines.dto.request.CreateAircraftDto;
import com.home365.airlines.entity.Aircraft;

import java.util.List;

public interface AircraftService {
    Aircraft createAircraft(CreateAircraftDto createAircraftDto);
    List<Aircraft> getAllAircrafts(Long airlineId);
}