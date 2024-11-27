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


    public List<Family> searchFamilyByParents(String input) {
        if (input == null || input.trim().isEmpty()) {
            return List.of(); // Return an empty list if input is null or blank
        }
        return familyRepository.searchFamilyByParents(input.trim());
    }


    public List<Family> findFamilyByFullName(String fornavn, String etternavn) {
        if (fornavn == null || etternavn == null) {
            return List.of(); // Return an empty list if inputs are null
        }
        return familyRepository.findFamilyByFullName(fornavn.trim(), etternavn.trim());
    }


}
