package com.home365.airlines.service.transaction.time;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TimeServiceImpl implements TimeService{

    @Override
    public LocalDate getCurrentTime() {
        return LocalDate.now();
    }
}
