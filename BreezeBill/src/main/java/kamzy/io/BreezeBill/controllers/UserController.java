package kamzy.io.BreezeBill.controllers;

import kamzy.io.BreezeBill.model.Profile;
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
    public ResponseEntity<String> signUp(@RequestBody Users u) {
        System.out.println(u);
        json = new JSONObject();
        String status = authserv.signupService(u);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @PostMapping ("/login")
    public ResponseEntity<String> login(@RequestParam String id_number, @RequestParam String password) {
        System.out.println(id_number+" -- "+password);
        json = new JSONObject();
        String token = authserv.loginService(id_number,password);
        json.put("token", token);
        if (token.equals("Invalid credentials")){
            return ResponseEntity.status(401).body(json.toString());
        } else {
            return ResponseEntity.ok(json.toString());
        }
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

    @PostMapping ("/get-user")
    public ResponseEntity<Users> getUserDetails(@RequestParam String id_number) {
        Users user = authserv.getUserDetails(id_number);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/create_profile")
    public ResponseEntity<String> createProfile(@RequestBody Profile p) {
        System.out.println(p);
        json = new JSONObject();
        String status = authserv.createProfile(p);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateProfile(@RequestBody Profile p){
        json = new JSONObject();
        String status = authserv.UpdateProfile(p);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @PostMapping ("/get-profile")
    public ResponseEntity<Profile> getUserProfile(@RequestParam String id_number) {
        Profile profile = authserv.getUserProfile(id_number);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }


}
