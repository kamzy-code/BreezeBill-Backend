package kamzy.io.BreezeBill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Groupss {

    @Id private int group_id;
    private String group_name;
    private int created_by;
    private String description;
    private int member_count;
    @Temporal(TemporalType.TIMESTAMP) private Date created_at;

}
