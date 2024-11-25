package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Setter
@Getter

public class CurrencyExchange {
    @Id
    @GeneratedValue

    private long id ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    Date date ;
    double nok ;
    double toman ;
    double kurs ;
    double gebyr ;

    @OneToMany(mappedBy = "currencyExchange")
    @JsonIgnore
    private List<Betaling> betalingList = new ArrayList<>();


    public CurrencyExchange(Date date, double nok, double toman, double kurs, double gebyr) {
        this.date = date;
        this.nok = nok;
        this.toman = toman;
        this.kurs = kurs;
        this.gebyr = gebyr;
    }
    public CurrencyExchange(){}

    public double realityRate(){
        return toman/nok ;
    }
}
