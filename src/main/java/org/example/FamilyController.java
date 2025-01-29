package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class FamilyController {
    @Autowired
    FamilyService familyService;

    @Autowired
    ElevService elevService;

    @PostMapping("/createFamily")
    @ResponseBody
    public Family createFamily(@RequestBody Family family) throws Exception {
        if (family != null) {
            family.setAktiv(true);
            return familyService.createFamily(family);
        }
        return null;
    }

    @DeleteMapping("/deleteFamily/{id}")
    @ResponseBody
    public String deleteFamilyById(@PathVariable long id) throws Exception {
        if (id > 0) {
            Family family = familyService.findFamilyById(id);
            if (family != null) {
                for (Elev elev : family.getElevList()) {
                    elev.setFamily(null);
                    elevService.updateElev(elev, elev.getId());
                }
                return familyService.deleteFamily(family);
            } else
                return "Couldn´t find Costumer with id " + id;
        } else
            return "No valid id";
    }

    @GetMapping("/findAFamilyById/{id}")
    @ResponseBody
    public Family findAFamilyById(@PathVariable long id) {
        if (id > 0) {
            return familyService.findFamilyById(id);
        }
        return null;
    }

    @GetMapping("/findAllFamily")
    @ResponseBody
    public List<Family> findAllFamily() {
        return familyService.findAllFamily();
    }

    @PostMapping("/assignElevToFamily/{elevId}/{familyId}")
    @ResponseBody
    public String assignElevToFamily(@PathVariable long elevId,@PathVariable long familyId){
        Family family= familyService.findFamilyById(familyId);
        if(family== null)
            return "FamilyId " + familyId + " doesnt exist." ;
        Elev elev = elevService.findElevById(elevId) ;
        if(elev== null)
            return "ElevId " + elevId + " doesnt exist." ;
        if(elev.addFamilyToElev(family) ) {
            elevService.elevRepository.save(elev);
        }
        else {
            return "Family is inactive";
        }
        if(family.addElevToFamily(elev) ) {
            familyService.familyRepository.save(family);
            return "ok";
        }
        return "elev is inaktive" ;
    }
    @GetMapping("/findFamily/{input}")
    @ResponseBody
    public Object findFamily(@PathVariable String input) {
        String[] terms = input.split(" ");
        if (terms.length == 2) {
            // Full name provided: Assume the input is "fornavn etternavn"
            String fornavn = terms[0];
            String etternavn = terms[1];
            List<Family> families = familyService.findFamilyByFullName(fornavn, etternavn);
            return !families.isEmpty() ? families : "No families found with name: " + fornavn + " " + etternavn;
        } else if (input.matches("\\d+")) {
            // Numeric input: Treat as an ID
            long id = Long.parseLong(input);
            Family family = familyService.findFamilyById(id);
            return family != null ? family : "No family found with ID: " + id;
        } else {
            // Single input or other cases: Perform a flexible search
            List<Family> families = familyService.searchFamilyByParents(input);
            return !families.isEmpty() ? families : "No families found matching input: " + input;
        }
    }

    @GetMapping("/deactivateFamily/{id}")
    @ResponseBody
    public String deactiveFamily(@PathVariable long id){
        Family family = familyService.findFamilyById(id) ;
        boolean test ;
        if(family != null){
            family.setAktiv(false);
            familyService.familyRepository.save(family) ;
            return "Family with id " + id  + " is deactivated " ;
        }
        return "no family with id " + id ;
    }


    @GetMapping("/reactivateFamily/{id}")
    @ResponseBody
    public String reactiveFamily(@PathVariable long id){
        Family family = familyService.findFamilyById(id) ;
        if(family != null){
            family.setAktiv(true);
            familyService.familyRepository.save(family) ;
            return "Family with id " + id  + " is reactivated " ;
        }
        return "no family with id " + id ;
    }
}
