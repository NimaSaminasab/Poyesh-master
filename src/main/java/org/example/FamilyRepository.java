package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FamilyRepository extends CrudRepository<Family,Long> {
    List<Family> findFamilyByFarFornavnIgnoreCaseAndFarEtternavnIgnoreCase(String fornavn, String etternavn) ;
    List<Family> findFamilyByMorFornavnIgnoreCaseAndMorEtternavnIgnoreCase(String fornavn, String etternavn) ;
}
