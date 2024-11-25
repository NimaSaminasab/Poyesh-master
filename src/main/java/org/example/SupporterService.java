package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupporterService {
    @Autowired
    SupporterRepository supporterRepository ;

    public Supporter createSupporter(Supporter supporter) throws Exception {
        if (supporter == null) {
            throw new Exception("Supporter is null");
        }
        return supporterRepository.save(supporter);
    }

    public String deleteSupporter(Supporter supporter) throws Exception {
        if (supporter == null) {
            throw new Exception("Elev is null");
        }
        String elevFullNavn = supporter.getFornavn() +" " +  supporter.getEtternavn();
        supporterRepository.delete(supporter);
        return elevFullNavn + " is deleted";
    }

    public Supporter findSupporterById(long id) {
        return supporterRepository.findById(id).orElse(null);
    }

    public List<Supporter> findAllElev() {
        return (List<Supporter>) supporterRepository.findAll();
    }

    public Supporter updateSupporter(Supporter supporter, long supporterId) {
        Supporter supporterOld = supporterRepository.findById(supporterId).orElse(null) ;
        if(supporterOld !=null){
            if(supporter.getFornavn()!=null) {
                supporterOld.setFornavn(supporter.getFornavn());
            }
            if(supporter.getEtternavn()!=null) {
                supporterOld.setEtternavn(supporter.getEtternavn());
            }
            if(supporter.getBetaltTilNa()!=0) {
                supporterOld.setBetaltTilNa(supporter.getBetaltTilNa());
            }
            supporterRepository.save(supporterOld) ;
            return supporterOld ;
        }
        return null ;
    }
    public List<Supporter> findSupporterByFornavnAndEtternavn(String fornavn, String etternavn){
        return supporterRepository.findByFornavnIgnoreCaseAndEtternavnIgnoreCase(fornavn,etternavn) ;
    }
    public List<Supporter> findSupporterByFornavn(String fornavn){
        return supporterRepository.findByFornavnIgnoreCase(fornavn) ;
    }
    public List<Supporter> findSupporterByEtternavn(String etternavn){
        return supporterRepository.findByEtternavnIgnoreCase(etternavn) ;
    }

    public Supporter findSupporterByTelefon(String telefon) {
        return supporterRepository.findByTelefon(telefon) ;
    }
}
