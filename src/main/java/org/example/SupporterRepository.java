package org.example;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupporterRepository extends CrudRepository<Supporter,Long> {

    List<Supporter> findByFornavnIgnoreCaseAndEtternavnIgnoreCase(String fornavn, String etternavn) ;
    List<Supporter> findByFornavnIgnoreCase(String fornavn) ;
    List<Supporter> findByEtternavnIgnoreCase(String etternavn) ;
    @Query("SELECT s FROM Supporter s WHERE s.telefon = :input OR s.postnummer = :input")
    List<Supporter> findSupporterByTelefonOrPostnummer(@Param("input") String input);

    @Query("SELECT s FROM Supporter s WHERE " +
            "LOWER(s.adresse) = LOWER(:input) OR " + // Exact match first
            "LOWER(s.fornavn) LIKE LOWER(CONCAT('%', :input, '%')) OR " +
            "LOWER(s.etternavn) LIKE LOWER(CONCAT('%', :input, '%')) OR " +
            "LOWER(s.telefon) LIKE LOWER(CONCAT('%', :input, '%')) OR " +
            "LOWER(s.adresse) LIKE LOWER(CONCAT('%', :input, '%')) OR " +
            "LOWER(s.postnummer) LIKE LOWER(CONCAT('%', :input, '%')) OR " +
            "LOWER(s.poststed) LIKE LOWER(CONCAT('%', :input, '%'))")
    List<Supporter> searchSupportersByMultipleFields(@Param("input") String input);




    Supporter findByTelefon(String telefon);
}
