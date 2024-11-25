package org.example;

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
    private long id ;
    private String fakturaNummer ;
    private double belop;
    private double toman ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date dato ;
    @ManyToOne
    @JoinColumn(name = "elev_id")
    private Elev elev ;
    @ManyToOne
    @JoinColumn(name = "supporter_id")
    private Supporter supporter ;
    @ManyToOne
    CurrencyExchange currencyExchange ;
    @Transient
    private Long supporterId;

    @Transient
    private Long elevId;
    public Betaling(String fakturaNummer, int belop, Date dato) {
        this.fakturaNummer = fakturaNummer;
        this.belop = belop;
        this.dato = dato;
    }

    public Betaling(){}
}
