package org.example;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Betaling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fakturaNummer;
    private double belop;
    private double toman;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date dato;

    @ManyToOne
    @JoinColumn(name = "elev_id")
    @JsonBackReference // Resolves circular reference for Elev
    private Elev elev;

    @ManyToOne
    @JoinColumn(name = "supporter_id")
    @JsonBackReference // Resolves circular reference for Supporter
    private Supporter supporter;

    @ManyToOne
    @JoinColumn(name = "currency_exchange_id")
    @JsonBackReference
    private CurrencyExchange currencyExchange; // Property referenced in CurrencyExchange

    public Betaling() {}

    @JsonProperty("elevName")
    public String getElevName() {
        return elev != null ? elev.getFullName() : null;
    }
}
