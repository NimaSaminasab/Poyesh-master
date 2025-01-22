package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class BankInfoController {
    @Autowired
    BankInfoService bankInfoService;
    @Autowired
    ElevService elevService;

    @PostMapping("/createBankInfo/{elevid}")
    @ResponseBody
    public String createBankInfo(@RequestBody BankInfo bankInfo, @PathVariable long elevid) throws Exception {
        Elev elev = elevService.findElevById(elevid);
        if (!elev.isAktiv())
            return "Elev is inactive";
        if (bankInfo != null) {

            BankInfo bankinfo = bankInfoService.createBankInfo(bankInfo);
            bankinfo.setElev(elev);
            bankInfoService.bankInfoRepository.save(bankinfo) ;
            return "ok";
        } else
            return "error";
    }

    @DeleteMapping("/deleteBankInfo/{id}")
    @ResponseBody
    public String deleteBankInfoById(@PathVariable long id) throws Exception {
        if (id > 0) {
            BankInfo bankInfo = bankInfoService.findBankInfoById(id);
            if (bankInfo != null) {
                return bankInfoService.deleteBankInfo(bankInfo);
            } else
                return "Couldn´t find Costumer with id " + id;
        } else
            return "No valid id";
    }

    @GetMapping("/findABankInfoById/{id}")
    @ResponseBody
    public BankInfo findABankInfoById(@PathVariable long id) {
        if (id > 0) {
            return bankInfoService.findBankInfoById(id);
        }
        return null;
    }

    @GetMapping("/findBankInfo/{input}")
    @ResponseBody
    public List<BankInfo> findBankInfo(@PathVariable String input) {
        if (input == null || input.trim().isEmpty()) {
            return List.of(); // Return an empty list if input is null or empty
        }
        return bankInfoService.searchBankInfo(input.trim());
    }



    @GetMapping("/findAllBankInfo")
    @ResponseBody
    public List<BankInfo> findAllBankInfo() {
        return bankInfoService.findAllBankInfo();
    }


}
