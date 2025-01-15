package kamzy.io.BreezeBill.model;

import jakarta.persistence.*;
import kamzy.io.BreezeBill.Enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class Transactions {
    @Id
    private int transaction_id;
    private int user_id;
    private double amount;
    private String description;
    private int related_bill_id;
    @Temporal(TemporalType.TIMESTAMP) private Date created_at;
    @Enumerated(EnumType.STRING) private TransactionStatus status;
}

