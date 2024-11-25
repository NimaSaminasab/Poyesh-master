package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter

public class BankInfo {
    @Id
    @GeneratedValue
    private long id ;
    private String bankNavn ;
    private String kontoHoldersNavn ;
    private String kontoNummer ;
    private String shebaNummer ;
    private String kortNummer ;
    @ManyToOne
    private Elev elev ;

    public BankInfo(String bankNavn, String kontoHoldersNavn,
                    String kontoNummer, String shebaNummer,
                    String kortNummer) {
        this.bankNavn = bankNavn;
        this.kontoHoldersNavn = kontoHoldersNavn;
        this.kontoNummer = kontoNummer;
        this.shebaNummer = shebaNummer;
        this.kortNummer = kortNummer;
    }
    public BankInfo(){}
}
