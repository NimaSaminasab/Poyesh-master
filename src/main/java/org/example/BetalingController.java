package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import java.util.Date;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
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



    @GetMapping("/findBetaling/{searchword}")
    @ResponseBody
    public List<Map<String, Object>> findBetaling(@PathVariable String searchword) {
        // Check if it's a fakturaNummer
        List<Map<String, Object>> payments = betalingService.findABetalingByFakturanummer(searchword).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        if (!payments.isEmpty()) {
            return payments;
        }

        // Check if it's a date in YYYY-MM-DD format
        if (searchword.matches("\\d{4}-\\d{2}-\\d{2}")) {
            try {
                Date date = Date.valueOf(searchword); // Convert String to java.sql.Date
                return betalingService.findABetalingByDate(date).stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid date format: " + searchword);
            }
        }

        // Check if it's a numeric value (for belop)
        if (searchword.matches("\\d+(\\.\\d+)?")) {
            try {
                double belop = Double.parseDouble(searchword);
                return betalingService.findABetalingByBelop(belop).stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid numeric format: " + searchword);
            }
        }

        // Check for supporter name or lastname
        payments = betalingService.findABetalingBySupporterFullName(searchword).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        if (!payments.isEmpty()) {
            return payments;
        }

        // Check for elev name or lastname
        payments = betalingService.findABetalingByElevFullName(searchword).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        if (!payments.isEmpty()) {
            return payments;
        }

        // Return empty list if no matches are found
        return List.of();
    }






    private Map<String, Object> mapToResponse(Betaling betaling) {
        Map<String, Object> paymentMap = new HashMap<>();
        paymentMap.put("id", betaling.getId());
        paymentMap.put("fakturaNummer", betaling.getFakturaNummer());
        paymentMap.put("belop", betaling.getBelop());
        paymentMap.put("dato", betaling.getDato());
        paymentMap.put("supporterName", betaling.getSupporter().getFornavn() + " " + betaling.getSupporter().getEtternavn());
        paymentMap.put("elevName", betaling.getElev().getFornavn() + " " + betaling.getElev().getEtternavn());
        return paymentMap;
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

