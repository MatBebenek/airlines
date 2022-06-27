package com.home365.airlines.controller;

import com.home365.airlines.dto.AircraftDto;
import com.home365.airlines.dto.TransactionDto;
import com.home365.airlines.exceptions.InvalidArgumentException;
import com.home365.airlines.exceptions.NotEnoughResourcesException;
import com.home365.airlines.exceptions.ResourceNotFoundException;
import com.home365.airlines.model.Aircraft;
import com.home365.airlines.model.Airline;
import com.home365.airlines.repositories.AircraftRepository;
import com.home365.airlines.repositories.AirlineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
public class AircraftController {
    private static final Logger LOG = LoggerFactory.getLogger(AircraftController.class);

    @Autowired
    AircraftRepository aircraftRepository;

    @Autowired
    AirlineRepository airlineRepository;

    @GetMapping(value = "/{id}/aircrafts")
    public List<Aircraft> findAllAircraftsForAirlineId(@RequestParam Long airlineId) {
        LOG.info("Getting all aircrafts for airline with id: " + airlineId);
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(() -> new ResourceNotFoundException("Airline", "id", airlineId));
        return airline.getAircraftList();
    }

    @PostMapping(value = "/create-aircraft")
    public Aircraft createAircraftForAirline(@RequestBody AircraftDto aircraftDto) {
        Aircraft newAircraft = new Aircraft();
        Long airlineId = aircraftDto.getOwnerId();
        newAircraft.setAircraftName(aircraftDto.getAircraftName());
        if (aircraftDto.getPrice() < 0) {
            throw new InvalidArgumentException("Aircraft", "price", aircraftDto.getPrice());
        } else {
            newAircraft.setPrice(aircraftDto.getPrice());
        }
        if (aircraftDto.getMaxDistance() <= 0) {
            throw new InvalidArgumentException("Aircraft", "max distance", aircraftDto.getMaxDistance());
        } else {
            newAircraft.setMaxDistance(aircraftDto.getMaxDistance());
        }
        Airline owner = airlineRepository.findById(airlineId).orElseThrow(() -> new ResourceNotFoundException("Airline", "id", airlineId));
        newAircraft.setOwner(owner);
        newAircraft.setCreatedAt(LocalDate.now());
        aircraftRepository.save(newAircraft);
        LOG.info("Added new aircraft " + newAircraft.getAircraftName() + " for airline named " + owner.getAirlineName());
        return newAircraft;
    }

    @PutMapping(value = "/sellAircraft")
    public String sellAircraft(@RequestBody TransactionDto transactionDto) {
        Long aircraftId = transactionDto.getAircraftId();
        Long buyerId = transactionDto.getBuyerId();
        Aircraft aircraft = aircraftRepository.findById(aircraftId).orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", aircraftId));
        LOG.debug("Found aircraft: " + aircraft.toString());
        Period monthsInUse = Period.between(aircraft.getCreatedAt(), LocalDate.now());
        Airline seller = aircraft.getOwner();
        Airline buyer = airlineRepository.findById(buyerId).orElseThrow(() -> new ResourceNotFoundException("Airline(Buyer)", "id", buyerId));
        LOG.debug("Found buyer airline: " + buyer.toString());
        Double price = aircraft.getPrice() - (1 - (monthsInUse.toTotalMonths() * 0.02));
        if (buyer.getBudget() >= price) {
            LOG.debug("Transaction in progress!");
            aircraft.removeOwner(seller);
            seller.increaseBudget(price);
            buyer.decreaseBudget(price);
            aircraft.setOwner(buyer);
            aircraftRepository.save(aircraft);
        } else {
            throw new NotEnoughResourcesException(buyer.getAirlineName(), "budget", buyer.getBudget());
        }
        return seller.getAirlineName() + " have sold his " + aircraft.getAircraftName() + " to " + buyer.getAirlineName() + " for " + price;
    }
}
