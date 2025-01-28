package kamzy.io.BreezeBill.model;

import jakarta.persistence.*;
import kamzy.io.BreezeBill.Enums.PaymentMethod;
import kamzy.io.BreezeBill.Enums.TransactionStatus;
import kamzy.io.BreezeBill.Enums.TransactionType;
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
    private int sender_id;
    private int receiver_id;
    private String sender_name;
    private String receiver_name;
    @Enumerated(EnumType.STRING) private TransactionType type;
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING) private PaymentMethod payment_method;
    private int related_bill_id;
    @Enumerated(EnumType.STRING) private TransactionStatus status;
    @Temporal(TemporalType.TIMESTAMP) private Date created_at;
}

