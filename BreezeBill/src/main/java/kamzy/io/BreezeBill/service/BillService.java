package kamzy.io.BreezeBill.service;

import kamzy.io.BreezeBill.Enums.BillCategory;
import kamzy.io.BreezeBill.Enums.BillStatus;
import kamzy.io.BreezeBill.Enums.BillType;
import kamzy.io.BreezeBill.Enums.GroupRoles;
import kamzy.io.BreezeBill.model.Bills;
import kamzy.io.BreezeBill.model.Groupss;
import kamzy.io.BreezeBill.model.User_groups;
import kamzy.io.BreezeBill.model.Users;
import kamzy.io.BreezeBill.repository.BillRepository;
import kamzy.io.BreezeBill.repository.GroupRepository;
import kamzy.io.BreezeBill.repository.UserRepository;
import kamzy.io.BreezeBill.repository.User_groupsRepository;
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
    @Autowired
    User_groupsRepository userGroupRepo;
    @Autowired
    GroupRepository groupRepository;

    public String createBill(Bills b) {
//        verify user exists
        Optional<Users> session_user = userRepo.findById(b.getCreated_by());

//        Get Group
        Groupss group = groupRepository.getGroupByGroupName(b.getRecipient_group());

        if (session_user.isPresent() && group != null){
//            Check if user is in group and role is admin
            User_groups ug = userGroupRepo.getGroupMemberByUserAndGroupId(session_user.get().getUser_id(), group.getGroup_id());

            if (ug.getRole_in_group().equals(GroupRoles.admin)){
//            if role is approved, set status and create bill
                b.setStatus(BillStatus.unpaid);
                b.setBill_type(BillType.group);
                b.setGroup_id(group.getGroup_id());
                b.setTotal_amount(b.getUnit_amount()*group.getMember_count());
                if (b.getBill_category()==null){
                    b.setBill_category(BillCategory.Optional);
                }
                billRepo.save(b);
                return "Bill Created Successfully";
            } else {
                return "Can't create bill because you're not an admin";
            }

        } else {
            return "Couldn't create Bill";
        }
    }

//    get all bills for a user via userID
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
