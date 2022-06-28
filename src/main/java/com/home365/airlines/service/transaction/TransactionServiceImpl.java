package com.home365.airlines.service.transaction;

import com.home365.airlines.dto.request.SaleTransactionDto;
import com.home365.airlines.entity.Aircraft;
import com.home365.airlines.entity.Airline;
import com.home365.airlines.exception.NotEnoughResourcesException;
import com.home365.airlines.repository.AircraftRepository;
import com.home365.airlines.repository.AirlineRepository;
import com.home365.airlines.service.transaction.time.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;
    private final TimeService timeService;

    @Override
    public String sellAircraft(SaleTransactionDto saleTransactionDto) {
        Aircraft aircraft = aircraftRepository.getEntity(saleTransactionDto.getAircraftId());
        Airline buyer = airlineRepository.getEntity(saleTransactionDto.getBuyerId());
        Period monthsInUse = Period.between(aircraft.getCreatedAt(), timeService.getCurrentTime());
        Airline seller = aircraft.getOwner();
        BigDecimal price = aircraft.getPrice().subtract(BigDecimal.ONE.subtract(BigDecimal.valueOf(monthsInUse.toTotalMonths() * 0.02)));
        if (buyer.getBudget().compareTo(price) >= 0) {
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
