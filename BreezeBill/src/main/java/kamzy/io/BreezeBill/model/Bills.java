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
    private int user_id;
    private String bill_name;
    private double amount;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date due_date;
    @Enumerated(EnumType.STRING) private BillStatus status;
    @Enumerated(EnumType.STRING) private BillType bill_type;
    @Enumerated(EnumType.STRING) private BillCategory bill_category;
}

