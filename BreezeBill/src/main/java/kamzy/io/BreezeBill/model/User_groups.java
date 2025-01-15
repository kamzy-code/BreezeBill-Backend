package kamzy.io.BreezeBill.model;

import jakarta.persistence.*;
import kamzy.io.BreezeBill.Enums.GroupRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class User_groups {
    @Id private int user_group_id;
    private int user_id;
    private int group_id;
    @Enumerated(EnumType.STRING) private GroupRoles role_in_group;
    @Temporal(TemporalType.TIMESTAMP) private Date joined_at;
}
