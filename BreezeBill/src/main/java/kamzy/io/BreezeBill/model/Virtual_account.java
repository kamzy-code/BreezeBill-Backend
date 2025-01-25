package kamzy.io.BreezeBill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Virtual_account {
    @Id
    private int van_id;
    private int user_id;
    private String virtual_account_number;

    public Virtual_account(int userId, String accountNumber) {
        this.user_id = userId;
        this.virtual_account_number = accountNumber;
    }

}


