package com.home365.airlines.service.transaction;

import com.home365.airlines.dto.request.SaleTransactionDto;

public interface TransactionService {
    String sellAircraft(SaleTransactionDto saleTransactionDto);
}
