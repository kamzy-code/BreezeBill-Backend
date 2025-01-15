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
    private double amount;
    private String payment_method;
    private String recipient;
    private String description;
}
