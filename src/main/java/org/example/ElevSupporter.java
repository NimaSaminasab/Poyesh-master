package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ElevSupporter {
    @Id
    @GeneratedValue
    long id ;
    @ManyToOne
    //@JsonIgnore
    private Elev elev;

    @ManyToOne
    //@JsonIgnore
    private Supporter supporter;

    public ElevSupporter(Elev elev, Supporter supporter) {
        this.elev = elev;
        this.supporter = supporter;

    }
    public ElevSupporter(){}
}
