package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ElevService {
    @Autowired
    ElevRepository elevRepository;
    @Autowired
    FamilyRepository familyRepository ;


    public Elev createElev(Elev elev) throws Exception {
        if (elev == null) {
            throw new Exception("Elev is null");
        }
        return elevRepository.save(elev);
    }

    public String deleteElev(Elev elev) throws Exception {
        if (elev == null) {
            throw new Exception("Elev is null");
        }
        String elevFullNavn = elev.getFornavn() +" " +  elev.getEtternavn();
        elevRepository.delete(elev);
        return elevFullNavn + " is deleted";
    }

    public Elev findElevById(long id) {
        return elevRepository.findById(id).orElse(null);
    }

    public List<Elev> findAllElev() {
        return (List<Elev>) elevRepository.findAll();
    }

    public Elev updateElev(Elev elev, long elevId) {
        Elev elevOld = elevRepository.findById(elevId).orElse(null);
        if (elevOld != null) {
            if (elev.getFornavn() != null) {
                elevOld.setFornavn(elev.getFornavn());
            }
            if (elev.getEtternavn() != null) {
                elevOld.setEtternavn(elev.getEtternavn());
            }
            if (elev.getPersonnummer() != null) {
                elevOld.setPersonnummer(elev.getPersonnummer());
            }
            if (elev.getFDato() != null) {
                elevOld.setFDato(elev.getFDato());
            }
            if (elev.getSkolenavn() != null) {
                elevOld.setSkolenavn(elev.getSkolenavn());
            }

            if (elev.getBehovSumPrManed() != 0) {
                elevOld.setBehovSumPrManed(elev.getBehovSumPrManed());
            }
            if (elev.getMotattSumTilNa() != 0) {
                elevOld.setMotattSumTilNa(elev.getMotattSumTilNa());
            }
            if (elev.getBilde() != null) {
                elevOld.setBilde(elev.getBilde());
            }
            if (elev.getFilm() != null) {
                elevOld.setFilm(elev.getFilm());
            }
            elevRepository.save(elevOld);
            return elevOld;
        }
        else {
             return null ;
        }
    }
    public Elev addFamily(@PathVariable long elevId, @PathVariable long familyId) throws Exception {
        Elev elev = elevRepository.findById(elevId).orElse(null) ;
        Family family = familyRepository.findById(familyId).orElse(null) ;
        if(elev!= null && family !=null){
            elev.setFamily(family);
            elevRepository.save(elev) ;
            return  elev ;
        }
        return null ;
    }
    public Elev findElevByPersonnummer(String personnummber){
        return elevRepository.findByPersonnummer(personnummber) ;
    }
    public List<Elev> findElevByFornavnAndEtternavn(String fornavn, String etternavn){
        return elevRepository.findByFornavnIgnoreCaseAndEtternavnIgnoreCase(fornavn,etternavn) ;
    }
    public List<Elev> findElevByCity(String city){

        return elevRepository.findByCityIgnoreCase(city) ;
    }
    public List<Elev> findElevByFornavnOrEtternavn(String fornavn, String etternavn){
        return elevRepository.findByFornavnIgnoreCaseOrEtternavnIgnoreCase(fornavn, etternavn) ;
    }

    public List<Elev> findElevWhoHasNoSupporter(){
        return elevRepository.findByHarSupporterIsFalse();
    }

    public List<Elev> findElevByTelefon(String telefon) {
        return elevRepository.findByTelefon1OrTelefon2OrTelefon3(telefon, telefon, telefon);
    }
    public List<Elev> findElevByFornavnOrEtternavnOrCityOrSkolenavn(String input) {
        return elevRepository.findByFornavnContainingIgnoreCaseOrEtternavnContainingIgnoreCaseOrCityContainingIgnoreCaseOrSkolenavnContainingIgnoreCase(input, input, input, input);
    }


}
