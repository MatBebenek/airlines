package com.home365.airlines.service.airline;

import com.home365.airlines.dto.request.NewAirlineDto;
import com.home365.airlines.dto.response.AirlinesWithBudget;
import com.home365.airlines.entity.Airline;

import java.util.List;

public interface AirlineService {
    List<AirlinesWithBudget> findAllAirlines();
    Airline createNewAirline(NewAirlineDto newAirlineDto);
}
