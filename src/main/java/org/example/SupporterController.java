package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SupporterController {
    @Autowired
    SupporterService supporterService ;

    @PostMapping("/createSupporter")
    @ResponseBody
    public String createSupporter(@RequestBody Supporter supporter) throws Exception {

        if (supporter != null) {
            supporter.setAktiv(true);
            supporterService.createSupporter(supporter);
            return "ok";
        } else
            return "error";
    }

    @DeleteMapping("/deleteSupporter/{id}")
    @ResponseBody
    public String deleteSupporterById(@PathVariable long id) throws Exception {
         if (id > 0) {
            Supporter supporter = supporterService.findSupporterById(id);
            if (supporter != null) {
                return supporterService.deleteSupporter(supporter);
            } else
                return "Couldn´t find Costumer with id " + id;
        } else
            return "No valid id";
    }

    @GetMapping("/findASupporterById/{id}")
    @ResponseBody
    public Supporter findASupporterById(@PathVariable long id) {
        if (id > 0) {
            return supporterService.findSupporterById(id);
        }
        return null;
    }
    @GetMapping("/findAllSupporter")
    @ResponseBody
    public List<Supporter> findAllSupporter() {
        return supporterService.findAllElev();
    }
    @PutMapping("/updateSupporter/{supporterId}")
    @ResponseBody
    public Supporter updateElev(@RequestBody Supporter supporter,@PathVariable long supporterId) throws Exception {
        return supporterService.updateSupporter(supporter,supporterId) ;
    }
    @GetMapping("/findSupporterByFornavnAndEtternavn/{fornavn}/{etternavn}")
    @ResponseBody
    public List<Supporter> findSupporterByFornavnAndEtternavn(@PathVariable String fornavn,@PathVariable String etternavn){
        return supporterService.findSupporterByFornavnAndEtternavn(fornavn,etternavn) ;
    }
    @GetMapping("/findSupporterByFornavn/{fornavn}")
    @ResponseBody
    public List<Supporter> findSupporterByFornavn(@PathVariable String fornavn){
        return supporterService.findSupporterByFornavn(fornavn );
    }
    @GetMapping("/findSupporterByEtternavn/{etternavn}")
    @ResponseBody
    public List<Supporter> findSupporterByEtternavn(@PathVariable String etternavn){
        return supporterService.findSupporterByEtternavn(etternavn );
    }
    @GetMapping("/findSupporterByTelefon/{telefon}")
    @ResponseBody
    public Supporter findSupporterByTelefon(@PathVariable String telefon){
        return supporterService.findSupporterByTelefon(telefon );
    }

    @GetMapping("/deactivateSupporter/{id}")
    @ResponseBody
    public String deactiveSupporter(@PathVariable long id){
        Supporter supporter = supporterService.findSupporterById(id) ;
        if(supporter != null){
            supporter.setAktiv(false);
            supporterService.supporterRepository.save(supporter) ;
            return supporter.getFornavn() + " " + supporter.getEtternavn() + " is deactivated " ;
        }
        return "no supporter with id " + id ;
    }
    @GetMapping("/reactivateSupporter/{id}")
    @ResponseBody
    public String reactiveSupporter(@PathVariable long id){
        Supporter supporter = supporterService.findSupporterById(id) ;
        if(supporter != null){
            supporter.setAktiv(true);
            supporterService.supporterRepository.save(supporter) ;
            return supporter.getFornavn() + " " + supporter.getEtternavn() + " is deactivated " ;
        }
        return "no supporter with id " + id ;
    }

}
