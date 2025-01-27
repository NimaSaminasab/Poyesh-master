package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private long id;
    private String fornavn;
    private String etternavn;
    private String telefon;
    private String adresse;
    private String postnummer;
    private String poststed;
    private double betaltTilNa;
    private double betaltTilNaToman;
    private boolean aktiv;

    @OneToMany(mappedBy = "supporter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Resolves circular reference for Betaling
    private List<Betaling> betalingList = new ArrayList<>();

    @OneToMany(mappedBy = "supporter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Resolves circular reference for ElevSupporter
    private List<ElevSupporter> elevSupporters = new ArrayList<>();

    public Supporter() {}
}
