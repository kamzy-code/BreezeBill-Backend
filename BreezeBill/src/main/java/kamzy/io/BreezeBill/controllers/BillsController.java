package kamzy.io.BreezeBill.controllers;


import kamzy.io.BreezeBill.Enums.BillStatus;
import kamzy.io.BreezeBill.model.Bills;
import kamzy.io.BreezeBill.service.BillService;
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
@RequestMapping("/api/bills")
public class BillsController {
    JSONObject json;
    @Autowired
    BillService billserv;

    @PostMapping("/create-bill")
    public ResponseEntity<String> createBill(@RequestBody Bills b){
        json = new JSONObject();
        String status = billserv.createBill(b);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.CREATED);
    }

    @GetMapping ("/get-bills/{user_id}")
    public ResponseEntity<List<Bills>> getUserBills(@PathVariable  int user_id){
        List<Bills> userBills = billserv.getUserBills(user_id);
        return new ResponseEntity<>(userBills, HttpStatus.OK);
    }

    @GetMapping ("/{user_id}/unpaid")
    public ResponseEntity<List<Bills>> getUnpaidBills(@PathVariable  int user_id){
        List<Bills> userBills = billserv.getUnpaidBills(user_id);
        return new ResponseEntity<>(userBills, HttpStatus.OK);
    }

    @GetMapping ("/{bill_id}")
    public ResponseEntity<Optional<Bills>> getBillDetails(@PathVariable  int bill_id){
        Optional<Bills> userBills = billserv.getBillDetails(bill_id);
        return new ResponseEntity<>(userBills, HttpStatus.OK);
    }

    @PutMapping ("/{bill_id}/update-status/{newStatus}")
    public ResponseEntity<String> updateBillStatus(@PathVariable  int bill_id, @PathVariable BillStatus newStatus){
        json = new JSONObject();
        String status = billserv.updateBillStatus(bill_id, newStatus);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

}
