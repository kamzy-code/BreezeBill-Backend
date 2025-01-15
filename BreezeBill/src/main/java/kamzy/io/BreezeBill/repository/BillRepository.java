package kamzy.io.BreezeBill.repository;

import kamzy.io.BreezeBill.model.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bills, Integer> {

    @Query("SELECT b FROM Bills b WHERE b.created_by = :user_id")
    List<Bills> getUserBillsByUserId(int user_id);

    @Query ("SELECT b FROM Bills b WHERE b.created_by = :user_id AND b.status = 'unpaid'")
    List<Bills> getUnpaidBills(int user_id);

    @Query("SELECT b FROM Bills b WHERE b.bill_id = :bill_id")
    Bills getBillByID(int bill_id);

    @Query("SELECT b FROM Bills b WHERE b.bill_id = :bill_id AND b.created_by = :user_id")
    Bills getBillByBillIdAndUserId(int bill_id, int user_id);
}
