package com.home365.airlines.controller;

import com.home365.airlines.dto.AircraftDto;
import com.home365.airlines.dto.AirlineDto;
import com.home365.airlines.dto.TransactionDto;
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
    public ResponseEntity<List<Aircraft>> findAllAircraftsForAirline(@RequestParam Long airlineId) {
        LOG.info("Getting all aircrafts for given airline id: " + airlineId);
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(() -> new ResourceNotFoundException("Airline", "id", airlineId));
        return new ResponseEntity<>(airline.getAircraftList(), HttpStatus.OK);
    }

    @PostMapping(value = "/addAircraft")
    public ResponseEntity<Aircraft> addAircraftToAirline(@RequestBody AircraftDto aircraftDto) {
        Aircraft newAircraft = new Aircraft();
        Long airlineId = aircraftDto.getOwnerId();
        newAircraft.setName(aircraftDto.getName());
        newAircraft.setPrice(aircraftDto.getPrice());
        newAircraft.setMaxDistance(aircraftDto.getMaxDistance());
        Airline owner = airlineRepository.findById(airlineId).orElseThrow(() -> new ResourceNotFoundException("Airline", "id", airlineId));
        newAircraft.setOwner(owner);
        newAircraft.setCreatedAt(LocalDate.now());
        aircraftRepository.save(newAircraft);
        LOG.info("Added new aircraft " + newAircraft.getName() + " for airline " + owner.getName());
        return new ResponseEntity<Aircraft>(newAircraft, HttpStatus.CREATED);
    }

    @PutMapping(value = "/sellAircraft")
    public ResponseEntity<String> sellAircraft(@RequestBody TransactionDto transactionDto) {
        Long aircraftId = transactionDto.getAircraftId();
        Long buyerId = transactionDto.getBuyerId();
        Aircraft aircraft = aircraftRepository.findById(aircraftId).orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", aircraftId));
        Period monthsInUse = Period.between(aircraft.getCreatedAt(), LocalDate.now());
        Airline seller = aircraft.getOwner();
        Airline buyer = airlineRepository.findById(buyerId).orElseThrow(() -> new ResourceNotFoundException("Airline(Buyer)", "id", buyerId));
        Double price = aircraft.getPrice() - (1 - (monthsInUse.toTotalMonths() * 0.02));
        if (buyer.getBudget() >= price) {
            aircraft.removeOwner(seller);
            seller.increaseBudget(price);
            buyer.decreaseBudget(price);
            aircraft.setOwner(buyer);
            aircraftRepository.save(aircraft);
        } else {
            return new ResponseEntity<String>("Buyer cannot afford this airplane!", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<String>(seller.getName() + " have sold his " + aircraft.getName() + " to " + buyer.getName() + " for " + price, HttpStatus.ACCEPTED);
    }
}
