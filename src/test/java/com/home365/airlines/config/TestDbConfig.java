package com.home365.airlines.config;

import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestDbConfig {

    static {
        // to avoid copying whole application.properties
        System.setProperty("spring.datasource.url", "jdbc:h2:mem:airlinedb");
    }

}
