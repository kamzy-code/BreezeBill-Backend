package kamzy.io.BreezeBill.model;

import jakarta.persistence.*;
import kamzy.io.BreezeBill.Enums.BillStatus;
import kamzy.io.BreezeBill.Enums.BillType;
import kamzy.io.BreezeBill.Enums.BillCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bills {
    @Id
    private int bill_id;
    private int created_by;    //    created by
    private int group_id;
    private String bill_name;
    private String description;
    private double unit_amount;
    private double total_amount;
    @Temporal(TemporalType.DATE) private Date due_date;
    private String payment_account;
    private String payment_bank;
    @Enumerated(EnumType.STRING) private BillStatus status;
    @Enumerated(EnumType.STRING) private BillType bill_type;
    private String recipient_group;
    @Enumerated(EnumType.STRING) private BillCategory bill_category;
}

