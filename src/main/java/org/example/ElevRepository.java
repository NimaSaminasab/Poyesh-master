package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ElevRepository extends CrudRepository<Elev,Long> {

    Elev findByPersonnummer(String personnummer);
    List<Elev> findByFornavnIgnoreCaseAndEtternavnIgnoreCase(String fornavn, String etternavn);
    List<Elev> findByCityIgnoreCase(String by);
    List<Elev> findByHarSupporterIsFalse() ;
}
