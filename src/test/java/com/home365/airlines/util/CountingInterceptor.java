package com.home365.airlines.util;

import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CountingInterceptor extends EmptyInterceptor {
    // although this is a Component, hibernate will create another instance of this class
    private static final AtomicInteger counter = new AtomicInteger();
    private static String lastQuery;

    @Override
    public String onPrepareStatement(String sql) {
        counter.incrementAndGet();
        lastQuery = sql;
        return super.onPrepareStatement(sql);
    }

    public void clearCounter() {
        counter.set(0);
    }

    public int getCounter() {
        return counter.get();
    }


    public String getLastQuery() {
        return lastQuery;
    }
}