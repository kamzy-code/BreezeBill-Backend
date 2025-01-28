package kamzy.io.BreezeBill.controllers;

import kamzy.io.BreezeBill.model.Virtual_account;
import kamzy.io.BreezeBill.service.VANService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/api/van")
public class VANController {

    @Autowired
    VANService vanService;

    JSONObject json;

    @GetMapping("/get/{userID}")
    public ResponseEntity<Virtual_account> getVAN (@PathVariable int userID){
        Virtual_account VAN = vanService.getVANByUserID(userID);
        return new ResponseEntity<>(VAN, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVAN (@RequestParam int userID){
        json = new JSONObject();
        String status =vanService.generateVirtualAccount(userID);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @PostMapping("/account_name")
    public ResponseEntity<String> getAccountName (@RequestParam String account_number){
        json = new JSONObject();
        String accountName =vanService.getAccountNameByAccountNumber(account_number);
        json.put("accountName", accountName);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }



}
