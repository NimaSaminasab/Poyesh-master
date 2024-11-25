package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange,Long> {
    CurrencyExchange findByDate(Date date);
}
