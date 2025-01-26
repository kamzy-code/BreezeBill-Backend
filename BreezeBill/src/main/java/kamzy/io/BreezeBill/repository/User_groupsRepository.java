package kamzy.io.BreezeBill.repository;

import kamzy.io.BreezeBill.model.User_groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User_groupsRepository extends JpaRepository<User_groups, Integer> {

    @Query("SELECT ug from User_groups ug WHERE ug.user_id = :user_id AND ug.group_id = :group_id")
    User_groups getGroupMemberByUserAndGroupId(int user_id, int group_id);

    @Query("SELECT COUNT(ug.user_id) FROM User_groups ug WHERE ug.group_id = :group_id")
    int getMemberCount(int group_id);

    @Query("SELECT ug.user_id FROM User_groups ug WHERE ug.group_id = :groupId")
    List<Integer> findUserIdsByGroupId(int groupId);

}
