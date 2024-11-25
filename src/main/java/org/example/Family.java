package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Family {
    @Id
    @GeneratedValue
    long id ;
    private String farFornavn ;
    private String farEtternavn ;
    private String morFornavn ;
    private String morEtternavn ;
    private boolean isAktiv ;

    @OneToMany(mappedBy = "family")
    @JsonIgnore
    private List<Elev> elevList = new ArrayList<>();
    private double sumMotatt ;
    private double sumMotattToman ;

    public Family(String farFornavn, String farEtternavn, String morFornavn, String morEtternavn, int sumMotatt) {
        this.farFornavn = farFornavn;
        this.farEtternavn = farEtternavn;
        this.morFornavn = morFornavn;
        this.morEtternavn = morEtternavn;
        this.sumMotatt = sumMotatt;
    }
    public Family(){}

    public boolean addElevToFamily(Elev elev){
        if(elev.isAktiv()) {
            elevList.add(elev);


            return true;
        }
        else
            return false;
    }
    public void setAktive(boolean isAktiv){
        this.isAktiv = isAktiv ;
     }
}
