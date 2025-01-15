package kamzy.io.BreezeBill.repository;

import kamzy.io.BreezeBill.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query ("SELECT w FROM Wallet w WHERE w.user_id = :userId")
    Wallet getWalletByUserId(String userId);
}
