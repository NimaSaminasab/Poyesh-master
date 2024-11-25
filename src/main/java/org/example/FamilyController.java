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
        System.out.println("elevid : " + elevId + "family id : " + familyId );
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
    @GetMapping("/findFamilyByFarFornavnAndEtternavn/{fornavn}/{etternavn}")
    @ResponseBody
    public List<Family> findFamilyByFarFornavnAndEtternavn(@PathVariable String fornavn, @PathVariable String etternavn){
      return familyService.findFamilyByFarFornavnAndEtternavn(fornavn,etternavn) ;
    }
    @GetMapping("/findFamilyByMorFornavnAndEtternavn/{fornavn}/{etternavn}")
    @ResponseBody
    public List<Family> findFamilyByMorFornavnAndEtternavn(@PathVariable String fornavn, @PathVariable String etternavn){
        return familyService.findFamilyByMorFornavnAndEtternavn(fornavn,etternavn) ;
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
