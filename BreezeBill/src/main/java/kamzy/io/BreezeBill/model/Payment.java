package kamzy.io.BreezeBill.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private int bill_id;
    private int user_id;
    private int wallet_id;
    private double amount;
    private String payment_method;
    private String recipient;
    private String description;
}
