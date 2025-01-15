package kamzy.io.BreezeBill.controllers;

import kamzy.io.BreezeBill.Enums.TransactionStatus;
import kamzy.io.BreezeBill.model.Payment;
import kamzy.io.BreezeBill.model.Transactions;
import kamzy.io.BreezeBill.service.TransactionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Component
@RestController
@RequestMapping("api/transaction")
public class TransactionController {
    JSONObject json;

    @Autowired
    TransactionService transactServ;

    @PostMapping("/create")
    public ResponseEntity<String> initiateTransaction(@RequestParam int bill_id, @RequestParam int user_id, @RequestBody Payment p){
        json = new JSONObject();
        String status = transactServ.createTransaction(user_id, bill_id, p);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @GetMapping("/get-transactions/{user_id}")
    public ResponseEntity<List<Transactions>> getUserTransactions(@PathVariable int user_id){
        List<Transactions> transactionList = transactServ.getUserTransactionsByUserId(user_id);
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }


    @GetMapping("/get-transaction/{transaction_id}")
    public ResponseEntity<Transactions> fetchTransactionDetails(@PathVariable int transaction_id){
        Transactions transaction = transactServ.getTransactionById(transaction_id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PutMapping("/status")
    public ResponseEntity<String> updateTransactionStatus(@RequestParam int transaction_id, @RequestParam TransactionStatus new_status){
        json = new JSONObject();
        String status = transactServ.updateTransactionStatus(transaction_id, new_status);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}/history")
    public ResponseEntity<List<Transactions>> getUserTransactionHistory(@PathVariable int user_id){
        List<Transactions> transactionList = transactServ.getTransactionHistory(user_id);
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }


}
