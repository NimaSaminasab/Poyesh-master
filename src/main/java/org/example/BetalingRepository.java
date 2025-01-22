package org.example;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BetalingRepository extends CrudRepository<Betaling,Long> {
    List<Betaling> findByfakturaNummer(String fakturaNummer);

  //  List<Betaling> findByDato(Date dato);
    @Query("SELECT b FROM Betaling b WHERE DATE(b.dato) = :dato")
    List<Betaling> findByDato(@Param("dato") Date dato);

    List<Betaling> findByBelop(double belop);

    List<Betaling> findBySupporterFornavnAndSupporterEtternavn(String namePart, String namePart1);

    List<Betaling> findBySupporterFornavnContainingIgnoreCaseOrSupporterEtternavnContainingIgnoreCase(String fullName, String fullName1);

    List<Betaling> findByElevFornavnAndElevEtternavn(String namePart, String namePart1);

    List<Betaling> findByElevFornavnContainingIgnoreCaseOrElevEtternavnContainingIgnoreCase(String fullName, String fullName1);
}
