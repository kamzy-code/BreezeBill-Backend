package kamzy.io.BreezeBill.service;

import kamzy.io.BreezeBill.Enums.BillCategory;
import kamzy.io.BreezeBill.Enums.BillStatus;
import kamzy.io.BreezeBill.model.Bills;
import kamzy.io.BreezeBill.model.Users;
import kamzy.io.BreezeBill.repository.BillRepository;
import kamzy.io.BreezeBill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    BillRepository billRepo;
    @Autowired
    UserRepository userRepo;

    public String createBill(Bills b) {
//        verify user exists
        Optional<Users> session_user = userRepo.findById(b.getUser_id());
        if (session_user.isPresent()){
//            Check role

//            if role is approved, set status and create bill
            b.setStatus(BillStatus.unpaid);
            if (b.getBill_category()==null){
                b.setBill_category(BillCategory.Optional);
            }
            billRepo.save(b);
            return "Bill Created Successfully";
        } else {
            return "Couldn't create Bill";
        }
    }

    public List<Bills> getUserBills(int user_id) {
        //        verify user exists
        Optional<Users> session_user = userRepo.findById(user_id);
        if (session_user.isPresent()){
            return billRepo.getUserBillsByUserId(user_id);
        }
        return null;
    }

    public List<Bills> getUnpaidBills(int user_id) {
        //        verify user exists
        Optional<Users> session_user = userRepo.findById(user_id);
        if (session_user.isPresent()){
            return billRepo.getUnpaidBills(user_id);
        }
        return null;
    }

    public Optional<Bills> getBillDetails(int bill_Id) {
        return billRepo.findById(bill_Id);
    }

    public String updateBillStatus(int bill_id, BillStatus newStatus) {
        Bills bill = billRepo.getBillByID(bill_id);
        if (bill != null){
            bill.setStatus(newStatus);
            billRepo.save(bill);
            return "Status Updated";
        }
        return "Error Updating Status";
    }
}
