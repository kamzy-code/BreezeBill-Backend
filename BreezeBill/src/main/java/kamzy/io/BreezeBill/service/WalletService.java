package kamzy.io.BreezeBill.service;

import kamzy.io.BreezeBill.Enums.WalletStatus;
import kamzy.io.BreezeBill.Utility.InsufficientFundsException;
import kamzy.io.BreezeBill.model.Transactions;
import kamzy.io.BreezeBill.model.Wallet;
import kamzy.io.BreezeBill.repository.TransactionRepository;
import kamzy.io.BreezeBill.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    public void createWalletForUser(String userId) {
        Wallet wallet = new Wallet();
        wallet.setId_number(userId);
        wallet.setBalance(0.00); // Initial balance
        wallet.setWallet_status(WalletStatus.active);
        walletRepository.save(wallet);
    }

    public Wallet getWalletByIdNumber(String id_number) {
        return walletRepository.getWalletByIdNumber(id_number);
    }

    public String setWalletCode(String IdNumber, String code){
        Wallet wallet = getWalletByIdNumber(IdNumber);
        wallet.setCode(code);
        walletRepository.save(wallet);
        return "Passcode Saved";
    }

    @Transactional
    public double addFunds(String id_number, double amount) {
        Wallet wallet = getWalletByIdNumber(id_number);
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
        return wallet.getBalance();
    }

    @Transactional
    public double deductFunds(String userId, double amount) {
        Wallet wallet = getWalletByIdNumber(userId);
        if (wallet.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds in wallet.");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);
        return wallet.getBalance();
    }

    @Transactional
    public String transferFunds(String senderUserId, String recipientUserId, double amount) {
        deductFunds(senderUserId, amount);
        addFunds(recipientUserId, amount);
        return "Transfer successful!";
    }

    public double getWalletBalance(String userId) {
        Wallet wallet = getWalletByIdNumber(userId);
        return wallet.getBalance();
    }

    @Transactional
    public String deactivateWallet(String userId) {
        Wallet wallet = getWalletByIdNumber(userId);
        wallet.setWallet_status(WalletStatus.blocked);
        walletRepository.save(wallet);
        return "Wallet deactivated successfully.";
    }

    @Transactional
    public String reactivateWallet(String  userId) {
        Wallet wallet = getWalletByIdNumber(userId);
        wallet.setWallet_status(WalletStatus.active);
        walletRepository.save(wallet);
        return "Wallet reactivated successfully.";
    }

    public List<Transactions> getTransactionHistory(int userId) {
        return transactionRepository.getTransactionsByUserId(userId);
    }


}
