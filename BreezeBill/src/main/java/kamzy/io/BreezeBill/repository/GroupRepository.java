package kamzy.io.BreezeBill.repository;

import kamzy.io.BreezeBill.model.Groupss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Groupss, Integer> {

    @Query("SELECT g FROM Groupss g WHERE g.group_name = :group_name")
    Groupss getGroupByGroupName(String group_name);

    @Query("SELECT g FROM Groupss g WHERE g.group_id = :group_id")
    Groupss getGroupById(int group_id);


    @Query ("SELECT g FROM Groupss g JOIN User_groups ug ON g.group_id = ug.group_id WHERE ug.user_id = :userId")
    List<Groupss> getUserGroups(int userId);
}
