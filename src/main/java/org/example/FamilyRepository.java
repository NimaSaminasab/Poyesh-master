package org.example;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FamilyRepository extends CrudRepository<Family,Long> {
    List<Family> findFamilyByFarFornavnIgnoreCaseAndFarEtternavnIgnoreCase(String fornavn, String etternavn) ;
    List<Family> findFamilyByMorFornavnIgnoreCaseAndMorEtternavnIgnoreCase(String fornavn, String etternavn) ;
    @Query("SELECT f FROM Family f WHERE " +
            "LOWER(f.farFornavn) LIKE LOWER(CONCAT('%', :input, '%')) OR " +
            "LOWER(f.farEtternavn) LIKE LOWER(CONCAT('%', :input, '%')) OR " +
            "LOWER(f.morFornavn) LIKE LOWER(CONCAT('%', :input, '%')) OR " +
            "LOWER(f.morEtternavn) LIKE LOWER(CONCAT('%', :input, '%'))")
    List<Family> searchFamilyByParents(@Param("input") String input);
    @Query("SELECT f FROM Family f WHERE " +
            "(LOWER(f.farFornavn) = LOWER(:fornavn) AND LOWER(f.farEtternavn) = LOWER(:etternavn)) OR " +
            "(LOWER(f.morFornavn) = LOWER(:fornavn) AND LOWER(f.morEtternavn) = LOWER(:etternavn))")
    List<Family> findFamilyByFullName(@Param("fornavn") String fornavn, @Param("etternavn") String etternavn);


}
