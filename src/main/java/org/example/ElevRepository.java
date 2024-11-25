package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ElevRepository extends CrudRepository<Elev,Long> {

    Elev findByPersonnummer(String personnummer);
    List<Elev> findByFornavnIgnoreCaseAndEtternavnIgnoreCase(String fornavn, String etternavn);
    List<Elev> findByCityIgnoreCase(String by);
    List<Elev> findByHarSupporterIsFalse() ;
    List<Elev> findByFornavnIgnoreCaseOrEtternavnIgnoreCase(String fornavn, String etternavn) ;
    List<Elev> findByTelefon1OrTelefon2OrTelefon3(String telefon1, String telefon2, String telefon3);
    List<Elev> findByFornavnContainingIgnoreCaseOrEtternavnContainingIgnoreCaseOrCityContainingIgnoreCaseOrSkolenavnContainingIgnoreCase(String fornavn, String etternavn, String city, String skolenavn);

}
