package kamzy.io.BreezeBill.repository;

import kamzy.io.BreezeBill.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    @Query("SELECT p FROM Profile p WHERE p.id_number = :idNumber")
    Profile getProfileByIdNumber(String idNumber);
}
