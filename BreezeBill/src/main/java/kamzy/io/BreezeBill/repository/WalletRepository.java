package kamzy.io.BreezeBill.repository;

import kamzy.io.BreezeBill.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query ("SELECT w FROM Wallet w WHERE w.id_number = :id_number")
    Wallet getWalletByIdNumber(String id_number);
}
