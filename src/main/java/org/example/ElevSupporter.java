package org.example;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ElevSupporter {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JsonBackReference // Resolves circular reference for Elev
    private Elev elev;

    @ManyToOne
    @JsonBackReference // Resolves circular reference for Supporter
    private Supporter supporter;

    public ElevSupporter() {}
}
