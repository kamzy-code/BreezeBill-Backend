package kamzy.io.BreezeBill.repository;

import kamzy.io.BreezeBill.model.Transactions;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

    @Query("SELECT t FROM Transactions t WHERE t.transaction_id = :transaction_id")
    public Transactions getTransactionByID (int transaction_id);

    @Query("SELECT t FROM Transactions t WHERE t.user_id = :user_id")
    List<Transactions> getTransactionsByUserId(int user_id);

    @Query("SELECT t FROM Transactions t WHERE t.user_id = :user_id")
    List<Transactions> getTransactionsByUserIdOrderByDate(int user_id, Sort sort);
}
