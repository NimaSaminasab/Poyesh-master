package org.example;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange,Long> {
    @Query("SELECT c FROM CurrencyExchange c WHERE DATE(c.date) = DATE(:date)")
    CurrencyExchange findByDate(@Param("date") Date date);

}
