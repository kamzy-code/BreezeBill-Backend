package kamzy.io.BreezeBill.model;

import kamzy.io.BreezeBill.Enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBillsDTO {
        private int user_bill_id;
        private  int bill_id;
        private BillStatus status;
        private double amount_paid;
        private String bill_name;
        private String description;
        private double unit_amount;
        private String payment_account;
        private Date due_date;
}
