package kamzy.io.BreezeBill.model;

import jakarta.persistence.*;
import kamzy.io.BreezeBill.Enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User_bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_bill_id;
    private int user_id;
    private int bill_id;
    @Enumerated(EnumType.STRING) private BillStatus status;
    private double amount;
    private double amount_paid;
    @Temporal(TemporalType.DATE) private Date created_at;
    @Temporal(TemporalType.DATE) private Date updated_at;
}
