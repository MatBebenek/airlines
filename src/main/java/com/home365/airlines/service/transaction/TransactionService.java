package com.home365.airlines.service.transaction;

import com.home365.airlines.dto.request.SaleTransactionDto;

import java.math.BigDecimal;

public interface TransactionService {
    BigDecimal sellAircraft(SaleTransactionDto saleTransactionDto);
}
