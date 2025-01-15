package kamzy.io.BreezeBill.repository;

import kamzy.io.BreezeBill.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users u WHERE u.id_number = :id_number")
    Users findByIdNumber(String id_number);

//    @Query("SELECT u FROM Users u WHERE u.user_id = :user_id")
//    Users findByUserId(int user_id);

}
