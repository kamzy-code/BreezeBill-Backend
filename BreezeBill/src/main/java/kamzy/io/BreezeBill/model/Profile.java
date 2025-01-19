package kamzy.io.BreezeBill.model;

import jakarta.persistence.*;
import kamzy.io.BreezeBill.Enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profile {
    @Id
    private int profile_id;
    private String id_number;
    private String email;
    private String phone_number;
    @Temporal(TemporalType.DATE)
    private Date date_of_birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String faculty;
    private String department;
    private String class_year;
}
