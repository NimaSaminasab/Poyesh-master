package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class CurrencyExchangeController {
    @Autowired
    CurrencyExchangeService currencyExchangeService;
    @Autowired
    BetalingService betalingService;


    @PostMapping("/createCurrencyExchange")
    @ResponseBody
    public CurrencyExchange createCurrencyExchange(@RequestBody CurrencyExchange currencyExchange) {
         List<Betaling> betalingList = betalingService.findAllBetaling();
        for (int i = 0; i < betalingList.size(); i++) {
             if (betalingList.get(i).getToman() == 0.0) {
                betalingList.get(i).setToman(betalingList.get(i).getBelop() * currencyExchange.realityRate());
                betalingList.get(i).getElev().setMotattSumTilNaToman( (betalingList.get(i).getElev().getMotattSumTilNaToman() ) + betalingList.get(i).getBelop() * currencyExchange.realityRate());
                betalingList.get(i).getSupporter().setBetaltTilNaToman( (betalingList.get(i).getElev().getMotattSumTilNaToman() ) +betalingList.get(i).getBelop() * currencyExchange.realityRate());
                if(betalingList.get(i).getElev().getFamily()!=null) {
                    betalingList.get(i).getElev().getFamily().setSumMotattToman((betalingList.get(i).getElev().getMotattSumTilNaToman()) + betalingList.get(i).getBelop() * currencyExchange.realityRate());
                }
                betalingService.betalingRepository.save(betalingList.get(i)) ;
            }
        }
        return currencyExchangeService.createCurrencyExchange(currencyExchange);
    }

    @DeleteMapping("/deleteCurrencyExchange/{id}")
    @ResponseBody
    public String deleteCurrencyExchange(@PathVariable long id) {
        if(id>0) {
            CurrencyExchange currencyExchange = currencyExchangeService.findCurrencyExchangeById(id);
            if (currencyExchange == null)
                return "error";
            currencyExchangeService.deleteCurrencyExchange(currencyExchange);
            return "ok";
        }
        return "id not found" ;
    }

    @GetMapping("/findCurrencyExchangeById/{id}")
    @ResponseBody
    public CurrencyExchange findCurrencyById(@PathVariable long id) {
        return currencyExchangeService.findCurrencyExchangeById(id);
    }

    @GetMapping("/findCurrencyExchange/{date}")
    @ResponseBody
    public List<CurrencyExchange> findCurrencyByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
    ) {
        CurrencyExchange exchange = currencyExchangeService.findCurrencyExchangeByDate(date);
        return exchange != null ? List.of(exchange) : List.of();
    }

    @GetMapping("findAllCurrencyExchange")
    @ResponseBody
    public List<CurrencyExchange> findAllCurrencyExchange() {
        return currencyExchangeService.findAllCurrencyExchange();
    }

}
