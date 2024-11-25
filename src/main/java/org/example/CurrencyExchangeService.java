package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CurrencyExchangeService {
    @Autowired
    CurrencyExchangeRepository currencyExchangeRepository ;

    public CurrencyExchange createCurrencyExchange(CurrencyExchange currencyExchange){
        return currencyExchangeRepository.save(currencyExchange) ;
    }
    public void deleteCurrencyExchange(CurrencyExchange currencyExchange){
        currencyExchangeRepository.delete(currencyExchange);
    }
    public CurrencyExchange findCurrencyExchangeById(long id){
        return currencyExchangeRepository.findById(id).orElse(null) ;
    }
    public List<CurrencyExchange> findAllCurrencyExchange(){
        return (List<CurrencyExchange>) currencyExchangeRepository.findAll();
    }

    public CurrencyExchange findCurrencyExchangeByDate(Date date) {
        return currencyExchangeRepository.findByDate(date) ;
    }
}
