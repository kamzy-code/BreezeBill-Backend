package kamzy.io.BreezeBill.controllers;

import kamzy.io.BreezeBill.model.Wallet;
import kamzy.io.BreezeBill.service.WalletService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    JSONObject json;

    @PostMapping("/get_wallet")
    public ResponseEntity<Wallet> getWallet (@RequestParam String id_number){
        Wallet wallet = walletService.getWalletByIdNumber(id_number);
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @PostMapping("/save_code")
    public ResponseEntity<String> updateWalletCode (@RequestParam String id_number, @RequestParam String passcode ){
        json = new JSONObject();
        String status = walletService.setWalletCode(id_number, passcode);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

}
