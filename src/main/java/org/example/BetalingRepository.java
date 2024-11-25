package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface BetalingRepository extends CrudRepository<Betaling,Long> {
    List<Betaling> findByfakturaNummer(String fakturaNummer);

    List<Betaling> findByDato(Date dato);
}
