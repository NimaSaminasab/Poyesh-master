package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/createBetaling")
public class BetalingController {

    @Autowired
    BetalingService betalingService;
    @Autowired
    ElevService elevService;
    @Autowired
    SupporterService supporterService;
    @Autowired
    FamilyService familyService;
    @Autowired
    CurrencyExchangeService currencyExchangeService;

    @PostMapping("/createBetaling")
    @ResponseBody
    public String createBetaling(@RequestBody Betaling betaling) throws Exception {
        System.out.println("Received betaling: " + betaling);

        // Validate elev
        if (betaling.getElevId() == null) {
            return "Elev ID is required.";
        }
        Elev elev = elevService.findElevById(betaling.getElevId());
        if (elev == null || !elev.isAktiv()) {
            return "Elev is inactive or not found.";
        }

        // Validate supporter
        if (betaling.getSupporterId() == null) {
            return "Supporter ID is required.";
        }
        Supporter supporter = supporterService.findSupporterById(betaling.getSupporterId());
        if (supporter == null || !supporter.isAktiv()) {
            return "Supporter is inactive or not found.";
        }

        // Attach elev and supporter to betaling
        betaling.setElev(elev);
        betaling.setSupporter(supporter);

        // Update elev and supporter with new payment
        elev.setMotattSumTilNa(elev.getMotattSumTilNa() + betaling.getBelop());
        supporter.setBetaltTilNa(supporter.getBetaltTilNa() + betaling.getBelop());

        // Save updated entities
        elevService.elevRepository.save(elev);
        supporterService.supporterRepository.save(supporter);

        // Save betaling
        betalingService.createBetaling(betaling);

        // Add betaling to lists
        elev.getBetalingList().add(betaling);
        supporter.getBetalingList().add(betaling);

        // Update family sum if exists
        Family family = elev.getFamily();
        if (family != null) {
            family.setSumMotatt(family.getSumMotatt() + betaling.getBelop());
            familyService.familyRepository.save(family);
        }

        System.out.println("Payment successfully created with resolved elev and supporter.");
        return "Payment successfully created!";
    }

    @DeleteMapping("/deleteBetaling/{id}")
    @ResponseBody
    public String deleteBetalingById(@PathVariable long id) throws Exception {
        if (id > 0) {
            Betaling betaling = betalingService.findABetalingById(id);
            if (betaling != null) {
                return betalingService.deleteBetaling(betaling);
            } else
                return "Couldn´t find Customer with id " + id;
        } else
            return "No valid id";
    }

    @GetMapping("/findABetalingById/{id}")
    @ResponseBody
    public Betaling findABetalingById(@PathVariable long id) {
        if (id > 0) {
            return betalingService.findABetalingById(id);
        }
        return null;
    }

    @GetMapping("/findABetaling/")
    @ResponseBody
    public List<Betaling> findABetaling(@RequestBody Betaling betaling) {
        if (betaling.getId() > 0) {
            return betalingService.findABetalingById2(betaling.getId());
        } else if (betaling.getFakturaNummer() != null) {
            return betalingService.findABetalingByFakturanummer(betaling.getFakturaNummer());
        } else if (betaling.getDato() != null) {
            return betalingService.findABetalingByDate(betaling.getDato());
        }
        return null;
    }

    @GetMapping("/findAllBetaling")
    @ResponseBody
    public List<Map<String, Object>> findAllBetaling() {
        return betalingService.findAllBetaling().stream().map(betaling -> {
            Map<String, Object> paymentMap = new HashMap<>();
            paymentMap.put("id", betaling.getId());
            paymentMap.put("fakturaNummer", betaling.getFakturaNummer());
            paymentMap.put("belop", betaling.getBelop());
            paymentMap.put("dato", betaling.getDato());

            // Include supporter and elev names
            paymentMap.put("supporterId", betaling.getSupporter().getId());
            paymentMap.put("supporterName", betaling.getSupporter().getFornavn() + " " + betaling.getSupporter().getEtternavn()); // Assuming `getNavn()` returns the name
            paymentMap.put("elevId", betaling.getElev().getId());
            paymentMap.put("elevName", betaling.getElev().getFornavn() + " " + betaling.getElev().getEtternavn() ) ; // Assuming `getNavn()` returns the name
            return paymentMap;
        }).collect(Collectors.toList());
    }
}

