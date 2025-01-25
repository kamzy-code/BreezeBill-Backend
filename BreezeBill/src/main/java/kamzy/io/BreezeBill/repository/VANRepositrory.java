package kamzy.io.BreezeBill.repository;

import kamzy.io.BreezeBill.model.Virtual_account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VANRepositrory extends JpaRepository<Virtual_account, Integer> {

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN TRUE ELSE FALSE END FROM Virtual_account v WHERE v.virtual_account_number = :accountNumber")
    boolean existsByAccountNumber(String accountNumber);

    @Query("SELECT v FROM Virtual_account v WHERE v.user_id = :userID")
    Virtual_account findByUserID(int userID);

    @Query("SELECT CONCAT(u.last_name, ' ',u.first_name) FROM Users u JOIN Virtual_account v ON u.user_id = v.user_id WHERE v.virtual_account_number = :accountNumber")
    String findUserNameByAccountNumber(String accountNumber);
}
