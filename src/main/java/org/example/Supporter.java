package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Supporter {
    @Id
    @GeneratedValue
    private long id ;
    private String fornavn ;
    private String etternavn ;
    private String telefon ;
    private String adresse ;
    private String postnummer ;
    private String poststed ;
    private double betaltTilNa ;
    private double betaltTilNaToman ;
    private boolean aktiv ;

    @OneToMany(mappedBy = "supporter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ElevSupporter> elevSupporters = new ArrayList<>();

    @OneToMany(mappedBy = "supporter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Betaling> betalingList = new ArrayList<>();


    public Supporter(String fornavn, String etternavn, int betaltTilNa , String telefon,
                     String adresse, String postnummer, String poststed) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.betaltTilNa = betaltTilNa;
        this.telefon = telefon ;
        this.adresse = adresse ;
        this.postnummer = postnummer ;
        this.poststed = poststed ;
    }
    public Supporter(){}



}
