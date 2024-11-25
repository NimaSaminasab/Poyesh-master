package org.example;

import org.springframework.data.repository.CrudRepository;

public interface BankInfoRepository extends CrudRepository<BankInfo,Long> {

    BankInfo findBykontoNummer(String kontoNummer);
    BankInfo findByShebaNummer(String shebaNummer) ;

    BankInfo findByKortNummer(String kortNummer);

    BankInfo findByKontoHoldersNavn(String kortHoldersNavn);
}
