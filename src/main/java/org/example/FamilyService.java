package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyService {
    @Autowired
    FamilyRepository familyRepository ;

    public Family createFamily(Family family) throws Exception {
        if (family == null) {
            throw new Exception("Family is null");
        }
        return familyRepository.save(family);
    }

    public String deleteFamily(Family family) throws Exception {
        if (family == null) {
            throw new Exception("family is null");
        }
        long id = family.getId() ;
        familyRepository.delete(family);
        return "Family with id " + id + " is deleted";
    }

    public Family findFamilyById(long id) {
        return familyRepository.findById(id).orElse(null);
    }

    public List<Family> findAllFamily() {
        return (List<Family>) familyRepository.findAll();
    }

    public List<Family> findFamilyByFarFornavnAndEtternavn(String fornavn,String etternavn){
        return familyRepository.findFamilyByFarFornavnIgnoreCaseAndFarEtternavnIgnoreCase(fornavn,etternavn) ;
    }
    public List<Family> findFamilyByMorFornavnAndEtternavn(String fornavn,String etternavn){
        return familyRepository.findFamilyByMorFornavnIgnoreCaseAndMorEtternavnIgnoreCase(fornavn,etternavn) ;
    }

}
