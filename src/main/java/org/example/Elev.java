package org.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Getter
@Setter
public class Elev {
    @Id
    @GeneratedValue
    private long id;

    private String fornavn;
    private String etternavn;
    private String personnummer;
    private String telefon1;
    private String telefon2;
    private String telefon3;
    private String city;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date fDato;

    private String skolenavn;
    private int behovSumPrManed;
    private double motattSumTilNa;
    private double motattSumTilNaToman;
    private boolean harSupporter;
    private boolean aktiv;
    private String bilde;
    private String film;

    @OneToMany(mappedBy = "elev", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Betaling> betalingList = new ArrayList<>();

    @OneToMany(mappedBy = "elev", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BankInfo> bankInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "elev", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ElevSupporter> elevSupporters = new ArrayList<>();

    @ManyToOne
    private Family family;

    public Elev(String fornavn, String etternavn, String personnummer, String telefon1, String telefon2,
                String telefon3, String city, Date fDato, String skolenavn,
                int behovSumPrManed, double motattSumTilNa, String bilde, String film) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.personnummer = personnummer;
        this.telefon1 = telefon1;
        this.telefon2 = telefon2;
        this.telefon3 = telefon3;
        this.city = city;
        this.fDato = fDato;
        this.skolenavn = skolenavn;
        this.behovSumPrManed = behovSumPrManed;
        this.motattSumTilNa = motattSumTilNa;
        this.bilde = bilde;
        this.film = film;
        this.aktiv = true;
    }

    public Elev() {}

    public boolean addFamilyToElev(Family family) {
        if (family.isAktiv()) { // Ensure the family is active before assigning
            this.family = family;
            return true;
        }
        return false; // Return false if the family is inactive
    }

    public String getFullName() {
        return fornavn + " " + etternavn;
    }
}
