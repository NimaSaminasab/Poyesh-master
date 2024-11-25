package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BetalingService {
    @Autowired
    BetalingRepository betalingRepository ;

    public Betaling createBetaling(Betaling betaling){
        return betalingRepository.save(betaling) ;
    }
    public String deleteBetaling(Betaling betaling){
        if(betaling!=null){
            long id = betaling.getId();
            betalingRepository.delete(betaling);
            return "Payment with id " + id + " is deleted" ;
        }
        return "Payment not found " ;
    }
    public Betaling findABetalingById(long id){
        return betalingRepository.findById(id).orElse(null) ;
    }
    public List<Betaling> findAllBetaling(){
        return (List<Betaling>) betalingRepository.findAll();
    }

    public List<Betaling> findABetalingByFakturanummer(String fakturaNummer) {
        return betalingRepository.findByfakturaNummer(fakturaNummer) ;
    }

    public List<Betaling> findABetalingByDate(Date dato) {
        return betalingRepository.findByDato(dato) ;
    }

    public List<Betaling> findABetalingById2(long id) {
        return (List<Betaling>) betalingRepository.findById(id).orElse(null);
        }

    }

