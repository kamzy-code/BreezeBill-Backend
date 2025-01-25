package kamzy.io.BreezeBill.service;

import kamzy.io.BreezeBill.model.Virtual_account;
import kamzy.io.BreezeBill.repository.VANRepositrory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VANService {

    @Autowired
    VANRepositrory vanRepositrory;

    public String generateVirtualAccount(int userId) {
        String accountNumber = generateUniqueAccountNumber();
        Virtual_account account = new Virtual_account(userId, accountNumber);
        vanRepositrory.save(account);
        return "Successful";
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.valueOf(1000000000 + new Random().nextInt(900000000)); // 10-digit
        } while (vanRepositrory.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    public Virtual_account getVANByUserID(int userID) {
        return vanRepositrory.findByUserID(userID);
    }

    public String getAccountNameByAccountNumber(String accountNumber){
        String userName = vanRepositrory.findUserNameByAccountNumber(accountNumber);
        if (userName != null) {
            System.out.println(userName);
            return userName; // Concatenate firstName and lastName
        } else {
           return "Account not found";
        }
    }
}
