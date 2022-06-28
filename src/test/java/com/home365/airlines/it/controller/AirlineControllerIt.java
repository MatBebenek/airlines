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

    @Test
    void createAirline() throws Exception {
        // given
        Destination destination = destinationRepository.saveAndFlush(aDestination());
        Airline airlineToAdd = anAirline(destination);

        // when
        this.mockMvc.perform(
                post("/create-airline")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(Map.of(
                                "airlineName", airlineToAdd.getAirlineName(),
                                "budget", airlineToAdd.getBudget(),
                                "homeBase", airlineToAdd.getHomeBase()
                        ))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", not(blankString())))
                .andExpect(jsonPath("$.name", equalTo(airlineToAdd.getAirlineName())))
                .andExpect(jsonPath("$.budget").value(is(airlineToAdd.getBudget()), BigDecimal.class))
                .andExpect(jsonPath("$.homeBase").value(is(airlineToAdd.getHomeBase()), BigDecimal.class))
                .andExpect(jsonPath("$.createdAt", not(blankString())))
                .andDo(print());

        // and then
        List<Airline> airlines = Lists.newArrayList(airlineRepository.findAll());
        assertThat(airlines).hasSize(1);
        assertThat(airlines).allSatisfy(airline -> {
            assertThat(airline.getId()).isGreaterThan(0);
            assertThat(airline.getAirlineName()).isEqualTo(airlineToAdd.getAirlineName());
            assertThat(airline.getBudget()).isEqualTo(airlineToAdd.getBudget());
            assertThat(airline.getHomeBase()).isEqualTo(airlineToAdd.getHomeBase());
            assertThat(airline.getCreatedAt()).isNotNull();
        });
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
