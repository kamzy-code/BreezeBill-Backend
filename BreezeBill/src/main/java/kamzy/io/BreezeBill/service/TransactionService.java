package kamzy.io.BreezeBill.service;

import kamzy.io.BreezeBill.Enums.BillStatus;
import kamzy.io.BreezeBill.model.Bills;
import kamzy.io.BreezeBill.model.Payment;
import kamzy.io.BreezeBill.Enums.TransactionStatus;
import kamzy.io.BreezeBill.model.Transactions;
import kamzy.io.BreezeBill.repository.BillRepository;
import kamzy.io.BreezeBill.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    BillRepository billRepo;
    @Autowired
    TransactionRepository transactRepo;

    public String createTransaction(Payment p) {
//        Get Bill
        Bills bill = billRepo.getBillByBillIdAndUserId(p.getBill_id(), p.getUser_id());
        if (bill == null){
            return "Bill not found";
        }
//        Verify Bill Status
        if (bill.getStatus().equals(BillStatus.paid)){
            return "Bill already paid";
        }
//        Verify Amount
        if (p.getAmount()<bill.getUnit_amount()){
            return "Insufficient Payment Amount";
        }

//        Create Transaction Object
        Transactions t = new Transactions();
        t.setSender_id(p.getUser_id());
        t.setAmount(p.getAmount());
        t.setDescription(p.getDescription());
        t.setRelated_bill_id(bill.getBill_id());
        t.setStatus(TransactionStatus.pending);
        t.setCreated_at(new Date());
//        Save Transaction
        transactRepo.save(t);
//        Update Bill Status
        bill.setStatus(BillStatus.paid);
        billRepo.save(bill);
        return "Transaction Created Successfully";
    }

    public List<Transactions> getUserTransactionsByUserId(int user_id) {
        return transactRepo.getTransactionsByUserId(user_id);
    }

    public Transactions getTransactionById(int transactionId) {
        return transactRepo.getTransactionByID(transactionId);
    }

    public String updateTransactionStatus(int transaction_id, TransactionStatus new_status) {
        Transactions t = getTransactionById(transaction_id);
        if (t == null){
            return "Transaction not found";
        }
        t.setStatus(new_status);
        transactRepo.save(t);
        return "Status Updated Successfully";
    }

    public List<Transactions> getTransactionHistory(int userId) {
        return transactRepo.getTransactionsByUserIdOrderByDate(userId, Sort.by("created_at").descending());
    }
}
