package org.example;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankInfoRepository extends CrudRepository<BankInfo,Long> {

    BankInfo findBykontoNummer(String kontoNummer);
    BankInfo findByShebaNummer(String shebaNummer) ;

    BankInfo findByKortNummer(String kortNummer);

    BankInfo findByKontoHoldersNavn(String kortHoldersNavn);


    @Query("SELECT b FROM BankInfo b WHERE " +
            "(LOWER(b.bankNavn) LIKE LOWER(CONCAT('%', :input, '%')) OR " +
            "LOWER(b.kontoHoldersNavn) LIKE LOWER(CONCAT('%', :input, '%'))) " +
            "OR (:input IS NOT NULL AND (:input = b.kontoNummer OR :input = b.shebaNummer OR :input = b.kortNummer))")
    List<BankInfo> searchBankInfoByFields(@Param("input") String input);

    @Query("SELECT b FROM BankInfo b JOIN FETCH b.elev")
    List<BankInfo> findAllWithElev();



}
