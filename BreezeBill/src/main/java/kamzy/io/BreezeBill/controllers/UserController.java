package kamzy.io.BreezeBill.controllers;

import kamzy.io.BreezeBill.service.UserService;
import kamzy.io.BreezeBill.model.Users;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping ("/api/users")
public class UserController {
    JSONObject json;

    @Autowired
    UserService authserv;

//    User CRUD Functionalities
//    C - Signup, R - login, U - Update Password, D - Delete Account

    @PostMapping("/signup")
    public ResponseEntity<JSONObject> signUp(@RequestBody Users u) {
        System.out.println(u);
        json = new JSONObject();
        String status = authserv.signupService(u);
        json.put("status", status);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping ("/login")
    public ResponseEntity<JSONObject> login(@RequestBody Users u) {
        System.out.println(u.getId_number()+" -- "+u.getPassword_hash());
        json = new JSONObject();
        boolean status = authserv.loginService(u.getId_number(),u.getPassword_hash());
        json.put("status", status);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping ("/logout")
    public ResponseEntity<String> logout(@RequestBody Users u) {
        json = new JSONObject();
        boolean status = authserv.logoutService();
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @PutMapping("/update-account")
    public ResponseEntity<String> updateAccount(@RequestBody Users u){
        System.out.println(u);
        json = new JSONObject();
        String status = authserv.UpdateAccount(u);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody Users u){
        json = new JSONObject();
        String status = authserv.deleteAccount(u.getId_number(), u.getPassword_hash());
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @GetMapping ("/profile/{id_number}")
    public ResponseEntity<Users> getUserProfile(@PathVariable String id_number) {
        Users profile = authserv.getUserProfile(id_number);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }


}
