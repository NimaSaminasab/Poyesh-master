package org.example;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BankInfo {
    @Id
    @GeneratedValue
    private long id;
    private String bankNavn;
    private String kontoHoldersNavn;
    private String kontoNummer;
    private String shebaNummer;
    private String kortNummer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"betalingList", "bankInfoList", "elevSupporters", "family"})
    private Elev elev;


    public BankInfo() {}
}
