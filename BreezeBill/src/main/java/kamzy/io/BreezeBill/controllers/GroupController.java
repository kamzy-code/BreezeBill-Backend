package kamzy.io.BreezeBill.controllers;

import kamzy.io.BreezeBill.model.Groupss;
import kamzy.io.BreezeBill.service.GroupService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Component
@RequestMapping("api/groups")
public class GroupController {

    JSONObject json;
    @Autowired
    GroupService groupServ;

    @PostMapping("/create")
    public ResponseEntity<String> createGroup (@RequestBody Groupss group){
        json = new JSONObject();
        String status = groupServ.createGroup(group);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @GetMapping("/{group_id}")
    public ResponseEntity<Groupss> getGroupDetails (@PathVariable int group_id){
        Groupss groupDetails = groupServ.getGroupDetails(group_id);
        return new ResponseEntity<>(groupDetails, HttpStatus.OK);
    }


    @PostMapping("/{group_id}/add-member")
    public ResponseEntity<String> addGroupMember (@RequestParam int user_id, @PathVariable int group_id){
        json = new JSONObject();
        String status = groupServ.joinGroup(user_id, group_id);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @DeleteMapping("/{group_id}/remove-member")
    public ResponseEntity<String> removeGroupMember (@RequestParam int user_id, @PathVariable int group_id){
        json = new JSONObject();
        String status = groupServ.leaveGroup(user_id, group_id);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @GetMapping("/{user_id}/user-groups")
    public ResponseEntity<List<Groupss>> getUserGroups (@PathVariable int user_id){
        List<Groupss> userGroups = groupServ.getUserGroups(user_id);
        return new ResponseEntity<>(userGroups, HttpStatus.OK);
//        json.put("User Groups", Objects.requireNonNullElse(userGroups, "Couldn't find any group"));
    }
}
