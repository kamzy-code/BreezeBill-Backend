package kamzy.io.BreezeBill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import kamzy.io.BreezeBill.Enums.WalletStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Wallet {
    @Id
    private int wallet_id;
    private String user_id;
    private double balance;
    @Enumerated(EnumType.STRING) private WalletStatus wallet_status;
}
