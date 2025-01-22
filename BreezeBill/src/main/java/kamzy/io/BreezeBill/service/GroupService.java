package kamzy.io.BreezeBill.service;

import kamzy.io.BreezeBill.Enums.GroupRoles;
import kamzy.io.BreezeBill.model.Groupss;
import kamzy.io.BreezeBill.model.User_groups;
import kamzy.io.BreezeBill.repository.GroupRepository;
import kamzy.io.BreezeBill.repository.User_groupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepo;
    @Autowired
    User_groupsRepository userGroupRepo;



    public String createGroup(Groupss g) {
        Groupss group = groupRepo.getGroupByGroupName(g.getGroup_name());
//        Check if group name already exist
        if (group != null) {
            return "Group name already exists";
        }
//        Create new group
        g.setCreated_at(new Date());
        groupRepo.save(g);

//        get created group
        Groupss createdGroup = groupRepo.getGroupByGroupName(g.getGroup_name());
        if (createdGroup != null) {
//            add creator to group
            joinGroup(createdGroup.getCreated_by(), createdGroup.getGroup_id());
            updateMemberCount(g.getGroup_name());}
        return createdGroup != null ? "Group created successfully" : "Error creating Group";
    }



    public Groupss getGroupDetails(int group_id) {
        return groupRepo.getGroupById(group_id);

//        Get Associated group members
//        Compile into response object

    }



    public String joinGroup(int user_id, int group_id) {
//        check if group exists
        Groupss g = groupRepo.getGroupById(group_id);
        if (g == null) return "Group not found";

//        check if user is already a member
        User_groups ug = userGroupRepo.getGroupMemberByUserAndGroupId(user_id, group_id);
        if (ug != null) return "You're already a group member";

        User_groups newUG = new User_groups();
        newUG.setUser_id(user_id);
        newUG.setGroup_id(group_id);
        if (g.getCreated_by() == user_id) newUG.setRole_in_group(GroupRoles.admin);
        else newUG.setRole_in_group(GroupRoles.member);
        newUG.setJoined_at(new Date());
        userGroupRepo.save(newUG);
        updateMemberCount(g.getGroup_name());
        return "You joined " + g.getGroup_name();
    }



    public String leaveGroup(int user_id, int group_id) {
//        check if group exists
        Groupss g = groupRepo.getGroupById(group_id);
        if (g == null) return "Group not found";

//        check if user is already a member
        User_groups ug = userGroupRepo.getGroupMemberByUserAndGroupId(user_id, group_id);
        if (ug == null) return "Error, User is not a group member";

        userGroupRepo.deleteById(ug.getUser_group_id());
        updateMemberCount(g.getGroup_name());
        return "You left " + g.getGroup_name();
    }


//  returns a list of groups a user belongs to
    public List<Groupss> getUserGroups(int user_id) {
        return groupRepo.getUserGroups(user_id);
    }


    public void updateMemberCount(String groupName) {
        Groupss group = groupRepo.getGroupByGroupName(groupName);
        if (group != null) {
            group.setMember_count(userGroupRepo.getMemberCount(group.getGroup_id()));
            groupRepo.save(group);
        }
    }

    public List<Groupss> getAllGroups() {
        return groupRepo.findAll();
    }
}
