package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupporterRepository extends CrudRepository<Supporter,Long> {

    List<Supporter> findByFornavnIgnoreCaseAndEtternavnIgnoreCase(String fornavn, String etternavn) ;
    List<Supporter> findByFornavnIgnoreCase(String fornavn) ;
    List<Supporter> findByEtternavnIgnoreCase(String etternavn) ;

    Supporter findByTelefon(String telefon);
}
