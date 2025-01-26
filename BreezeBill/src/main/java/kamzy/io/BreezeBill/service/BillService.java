package kamzy.io.BreezeBill.service;

import kamzy.io.BreezeBill.Enums.*;
import kamzy.io.BreezeBill.Utility.InsufficientFundsException;
import kamzy.io.BreezeBill.Utility.ResourceNotFoundException;
import kamzy.io.BreezeBill.model.*;
import kamzy.io.BreezeBill.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    @Autowired
    WalletService walletService;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    VANService vanService;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserBillRepository userBillRepository;


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
                Bills savedBill = billRepo.findByBillTimestamp();
                System.out.println("Bill Id: "+savedBill.getBill_id());
                createUserBillRecords(savedBill.getBill_id(), savedBill.getGroup_id(), savedBill.getUnit_amount());

                return "Bill Created Successfully";
            } else {
                return "Can't create bill because you're not an admin";
            }

        } else {
            return "Couldn't create Bill";
        }
    }

//    get all bills for a user via userID
    public List<UserBillsDTO> getUserBills(int user_id) {
        //        verify user exists
        Optional<Users> session_user = userRepo.findById(user_id);
        if (session_user.isPresent()){
            return userBillRepository.findUserBillsWithBillDetails(user_id);
        }
        return null;
    }

//    pay bill
    public String payBill (int senderId, int billId, double amount, String description){

        // Step 1: Validate sender's wallet balance
        Wallet senderWallet = walletService.getWalletByUserId(senderId);
        if (senderWallet == null) {
            throw new ResourceNotFoundException("Payer's wallet not found.");
        }
        if (senderWallet.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds in wallet.");
        }

        // Step 2: Fetch bill and receiver details
        Bills bill = billRepo.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found."));

        int receiverId = vanService.getUserIdByAccountNumber(bill.getPayment_account());
        Wallet receiverWallet = walletService.getWalletByUserId(receiverId);
        if (receiverWallet == null) {
            throw new ResourceNotFoundException("Receiver's wallet not found.");
        }

        // Step 3: Deduct from sender and credit receiver
        senderWallet.setBalance(senderWallet.getBalance() - amount);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);


        // Step 4: Update UserBill record
        User_bill userBill = userBillRepository.findByUserIdAndBillId(senderId, billId)
                .orElseThrow(() -> new ResourceNotFoundException("UserBill record not found."));
        userBill.setStatus(BillStatus.paid);
        userBill.setAmount_paid(amount);
        userBill.setUpdated_at(new Date());
        userBillRepository.save(userBill);

        updateBillStatusIfComplete(billId);

        // Step 5: Create and save transaction record
        Transactions transaction = new Transactions();
        transaction.setSender_id(senderId);
        transaction.setReceiver_id(receiverId);
        transaction.setType(TransactionType.Bill_payment);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setPayment_method(PaymentMethod.Wallet);
        transaction.setRelated_bill_id(billId);
        transaction.setStatus(TransactionStatus.successful);
        transaction.setCreated_at(new Date());

        transactionRepository.save(transaction);

        return "successful";
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

    public void createUserBillRecords(int billId, int groupId, double unitAmount) {
        List<Integer> groupMemberIds = userGroupRepo.findUserIdsByGroupId(groupId);

        for (Integer userId : groupMemberIds) {
            User_bill userBill = new User_bill();
            userBill.setUser_id(userId);
            userBill.setBill_id(billId);
            userBill.setStatus(BillStatus.unpaid);
            userBill.setAmount(unitAmount);
            userBill.setAmount_paid(0.0);
            userBill.setCreated_at(new Date());
            userBill.setUpdated_at(new Date());
            userBillRepository.save(userBill);
        }
    }

    public void updateBillStatusIfComplete(int billId) {
        long unpaidCount = userBillRepository.countByBillIdAndStatus(billId, "Unpaid");
        if (unpaidCount == 0) {
            Bills bill = billRepo.findById(billId)
                    .orElseThrow(() -> new ResourceNotFoundException("Bill not found."));
            bill.setStatus(BillStatus.paid);
            billRepo.save(bill);
        }
    }
}
