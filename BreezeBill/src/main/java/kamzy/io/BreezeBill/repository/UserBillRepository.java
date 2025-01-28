package kamzy.io.BreezeBill.repository;

import kamzy.io.BreezeBill.Enums.BillStatus;
import kamzy.io.BreezeBill.model.UserBillsDTO;
import kamzy.io.BreezeBill.model.User_bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBillRepository extends JpaRepository<User_bill, Integer> {

    @Query("SELECT ub FROM User_bill ub WHERE ub.user_id = :userId AND ub.bill_id = :billId")
    Optional<User_bill> findByUserIdAndBillId( int userId, int billId);

    @Query("SELECT COUNT(ub) FROM User_bill ub WHERE ub.bill_id = :bill_id AND ub.status = :status")
    long countByBillIdAndStatus(int bill_id, BillStatus status);

    @Query("SELECT new kamzy.io.BreezeBill.model.UserBillsDTO(ub.user_bill_id, b.bill_id, ub.status, ub.amount_paid, " +
            "b.bill_name, b.description, b.unit_amount, b.payment_account, b.due_date) " +
            "FROM User_bill ub JOIN Bills b ON ub.bill_id = b.bill_id " +
            "WHERE ub.user_id = :userId")
    List<UserBillsDTO> findUserBillsWithBillDetails(int userId);

}
