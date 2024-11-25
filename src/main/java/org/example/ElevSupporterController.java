package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ElevSupporterController {
    @Autowired
    ElevSupporterService elevSupporterService ;
    @Autowired
    ElevService elevService;
    @Autowired
    SupporterService supporterService;


    @PostMapping("/createElevSupporter")
    @ResponseBody
    public String createElevSupporter(@RequestBody ElevSupporter elevSupporter) throws Exception {


            if (elevSupporter != null) {
                Elev elev = elevService.findElevById(elevSupporter.getElev().getId());
                Supporter supporter = supporterService.findSupporterById(elevSupporter.getSupporter().getId());
                if(elevSupporterService.isThisSupporterConnectedToThisElev(supporter,elev)){
                    return "elev " + elev.getFornavn() +" " + elev.getEtternavn() + " is already supported by " +
                            supporter.getFornavn() + " " + supporter.getEtternavn() ;
                }
                if(!elev.isAktiv())
                    return "elev is inactive" ;
                if(!supporter.isAktiv())
                    return "supporter is inactive" ;

                if (elev != null && supporter != null) {
                    elev.setHarSupporter(true);
                    elevSupporter.setElev(elev);
                    elevSupporter.setSupporter(supporter);
                    elevSupporterService.createElevSupporter(elevSupporter);

                    elev.getElevSupporters().add(elevSupporter);
                    supporter.getElevSupporters().add(elevSupporter);
                    elevService.elevRepository.save(elev);
                    return "ok";
                } else {
                    return "Elev or Supporter not found with provided ids";
                }
            } else {
                return "error";
            }

    }

    @DeleteMapping("/deleteElevSupporter/{id}")
    @ResponseBody
    public String deleteElevSupporterById(@PathVariable long id) throws Exception {
        if (id > 0) {
            ElevSupporter elevSupporter = elevSupporterService.findElevSupporterById(id);
            if (elevSupporter != null) {
                return elevSupporterService.deleteElevSupporter(elevSupporter);
            } else
                return "Couldn´t find Costumer with id " + id;
        } else
            return "No valid id";
    }

    @GetMapping("/findAElevSupporterById/{id}")
    @ResponseBody
    public ElevSupporter findAElevSupporterById(@PathVariable long id) {
        if (id > 0) {
            return elevSupporterService.findElevSupporterById(id);
        }
        return null;
    }
    @GetMapping("/findAllElevSupporter")
    @ResponseBody
    public List<ElevSupporter> findAllElevSupporter() {
        return elevSupporterService.findAllElevSupporter();
    }
   /* @PutMapping("/updateSupporter/{supporterId}")
    @ResponseBody
    public Supporter updateElev(@RequestBody Supporter supporter,@PathVariable long supporterId) throws Exception {
        return supporterService.updateSupporter(supporter,supporterId) ;
    }*/
}
