package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ElevController {
    @Autowired
    ElevService elevService;


    @PostMapping("/createElev")
    @ResponseBody
    public String createElev(@RequestBody Elev elev) throws Exception {
        if (elev != null) {
             if (elevService.findElevByPersonnummer(elev.getPersonnummer()) == null) {
                elev.setAktiv(true);
                elevService.createElev(elev);
                return "ok";
            } else
                return "Personnummer " + elev.getPersonnummer() + " eksisterer allerede ";

        } else
            return "error";
    }

    @DeleteMapping("/deleteElev/{id}")
    @ResponseBody
    public String deleteElevById(@PathVariable long id) throws Exception {
        if (id > 0) {
            Elev elev = elevService.findElevById(id);
            if (elev != null) {
                return elevService.deleteElev(elev);
            } else
                return "Couldn´t find Costumer with id " + id;
        } else
            return "No valid id";
    }

    @GetMapping("/findAElevById/{id}")
    @ResponseBody
    public Elev findAElevById(@PathVariable long id) {
        if (id > 0) {
            return elevService.findElevById(id);
        }
        return null;
    }
    @GetMapping("/findAElev/{input}")
    @ResponseBody
    public Object findAElev(@PathVariable String input) {
        // Check if the input is numeric
        if (input.matches("\\d+")) { // If numeric, treat it as an ID, personnummer, or telefon
            long id = Long.parseLong(input);
            if (id > 0) {
                Elev elev = elevService.findElevById(id);
                if (elev != null) {
                    return elev;
                } else {
                    // Check personnummer
                    Elev elevByPersonnummer = elevService.findElevByPersonnummer(input);
                    if (elevByPersonnummer != null) {
                        return elevByPersonnummer;
                    }

                    // Check telefon
                    List<Elev> elevsByTelefon = elevService.findElevByTelefon(input);
                    return !elevsByTelefon.isEmpty() ? elevsByTelefon : "No Elevs found with telefon: " + input;
                }
            }
        } else {
            // Split the input by spaces to check if it's one or two words
            String[] parts = input.split("\\s+"); // Split by whitespace

            if (parts.length == 1) { // Single word
                // Search by fornavn, etternavn, city, or skolenavn containing the word
                List<Elev> elevs = elevService.findElevByFornavnOrEtternavnOrCityOrSkolenavn(parts[0]);
                return !elevs.isEmpty() ? elevs : "No Elevs found matching input: " + input;
            } else if (parts.length == 2) { // Two words
                // Search by exact fornavn and etternavn
                List<Elev> elevs = elevService.findElevByFornavnAndEtternavn(parts[0], parts[1]);
                return !elevs.isEmpty() ? elevs : "No Elevs found with name: " + parts[0] + " " + parts[1];
            }
        }
        return "Invalid input.";
    }






    @GetMapping("/findAllElev")
    @ResponseBody
    public List<Elev> findAllElev() {
        return elevService.findAllElev();
    }

    @PutMapping("/updateElev/{elevId}")
    @ResponseBody
    public Elev updateElev(@RequestBody Elev elev, @PathVariable long elevId) throws Exception {
        return elevService.updateElev(elev, elevId);
    }

    @PostMapping("/addFamily/{elevId}/{familyId}")
    @ResponseBody
    public Elev addFamily(@PathVariable long elevId, @PathVariable long familyId) throws Exception {
        return elevService.addFamily(elevId, familyId);
    }

    @GetMapping("findElevByPersonnummer/{personnummer}")
    @ResponseBody
    public Elev findElevByPersonnummer(@PathVariable String personnummer) {
        return elevService.findElevByPersonnummer(personnummer);
    }

    @GetMapping("findElevByFornavnAndEtternavn/{fornavn}/{etternavn}")
    @ResponseBody
    public List<Elev> findElevByFornavnAndEtternavn(@PathVariable String fornavn, @PathVariable String etternavn) {
        return (List<Elev>) elevService.findElevByFornavnAndEtternavn(fornavn, etternavn);
    }

    @GetMapping("findElevByby/{by}")
    @ResponseBody
    public List<Elev> findElevByCity(@PathVariable String by) {
        return (List<Elev>) elevService.findElevByCity(by);
    }

    @GetMapping("/findElevByHasSupportIsFalse")
    @ResponseBody
    public List<Elev> findElevWhoHasNoSupporter() {

        return (List<Elev>) elevService.findElevWhoHasNoSupporter();
    }

    @GetMapping("/deactivateElev/{id}")
    @ResponseBody
    public String deactiveElev(@PathVariable long id) {
        Elev elev = elevService.findElevById(id);
        if (elev != null) {
            elev.setAktiv(false);
            elevService.elevRepository.save(elev) ;
            return elev.getFornavn() + " " + elev.getEtternavn() + " is deactivated ";
        }
        return "no elev with id " + id;
    }
    @GetMapping("/reactivateElev/{id}")
    @ResponseBody
    public String reactiveElev(@PathVariable long id) {
        Elev elev = elevService.findElevById(id);
        if (elev != null) {
            elev.setAktiv(true);
            elevService.elevRepository.save(elev) ;
            return elev.getFornavn() + " " + elev.getEtternavn() + " is reactivated ";
        }
        return "no elev with id " + id;
    }
    @GetMapping("/findBankInfoForElevByElevId/{id}")
    @ResponseBody
    public List<BankInfo> findBankInfoForElevByElevId(@PathVariable long id) {
        Elev elev = elevService.findElevById(id);
        if (elev != null) {
            return elev.getBankInfoList();
        }
        return null ;
    }

}
