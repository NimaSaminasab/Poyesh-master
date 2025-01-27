package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Family {
    @Id
    @GeneratedValue
    private long id;
    private String farFornavn;
    private String farEtternavn;
    private String morFornavn;
    private String morEtternavn;
    private boolean isAktiv;

    private double sumMotatt;
    private double sumMotattToman;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Elev> elevList = new ArrayList<>();

    // Method to add an Elev to the family
    public boolean addElevToFamily(Elev elev) {
        if (elev.isAktiv()) { // Ensure the Elev is active
            elevList.add(elev); // Add Elev to the family list
            return true; // Return true if successfully added
        }
        return false; // Return false if Elev is inactive
    }

    // Constructor with parameters
    public Family(String farFornavn, String farEtternavn, String morFornavn, String morEtternavn, int sumMotatt) {
        this.farFornavn = farFornavn;
        this.farEtternavn = farEtternavn;
        this.morFornavn = morFornavn;
        this.morEtternavn = morEtternavn;
        this.sumMotatt = sumMotatt;
    }

    // Default constructor
    public Family() {}

    // Method to set the family as active or inactive
    public void setAktiv(boolean isAktiv) {
        this.isAktiv = isAktiv;
    }
}
