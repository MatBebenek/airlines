package com.home365.airlines.it;

import com.home365.airlines.config.TestDbConfig;
import com.home365.airlines.repository.AircraftRepository;
import com.home365.airlines.repository.AirlineRepository;
import com.home365.airlines.repository.DestinationRepository;
import com.home365.airlines.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestDbConfig.class)
public class IntegrationTestsCommonBehavior {

    @Autowired
    protected LocationRepository locationRepository;

    @Autowired
    protected AirlineRepository airlineRepository;

    @Autowired
    protected AircraftRepository aircraftRepository;

    @Autowired
    protected DestinationRepository destinationRepository;

    // todo: consider changing this to truncate / truncate cascade
    public void deleteAllData() {
        deleteAllAircrafts();
        deleteAllDestinations();
        deleteAllAirlines();
        deleteAllLocations();
    }

    public void deleteAllLocations() {
        locationRepository.deleteAll();
        locationRepository.flush();
    }

    public void deleteAllAirlines() {
        airlineRepository.deleteAll();
        airlineRepository.flush();
    }

    public void deleteAllDestinations() {
        destinationRepository.deleteAll();
        destinationRepository.flush();
    }

    public void deleteAllAircrafts() {
        aircraftRepository.deleteAll();
        aircraftRepository.flush();
    }
}
