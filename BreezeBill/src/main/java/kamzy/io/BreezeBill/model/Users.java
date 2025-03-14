package kamzy.io.BreezeBill.model;

import jakarta.persistence.*;
import kamzy.io.BreezeBill.Enums.Gender;
import kamzy.io.BreezeBill.Enums.UserRole;
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
public class Users {

    @Id
    private int user_id;
    private String first_name;
    private String last_name;
    private String id_number;
    private String password_hash;
    @Enumerated(EnumType.STRING) private UserRole role;

}

