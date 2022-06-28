package com.home365.airlines.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home365.airlines.entity.Airline;
import com.home365.airlines.entity.Destination;
import com.home365.airlines.entity.Location;
import com.home365.airlines.it.IntegrationTestsCommonBehavior;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.home365.airlines.helper.AirlineHelper.anAirline;
import static com.home365.airlines.helper.DestinationHelper.aDestination;
import static com.home365.airlines.util.MapperHelper.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AirlineControllerIt extends IntegrationTestsCommonBehavior {

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        deleteAllData();
    }


    // todo: add tests for
    // create location without name, latitude or longitude
    // create location with existing name
    // create location with name not matching pattern
    // create location with existing name but in upper case
    // create location with latitude out of range
    // create location with longitude out of range
    // create location without longitude and  latitude
    // get list of locations with empty database
    // get list of locations with one item in database
    // get list of locations with multiple items in database
}
