package kamzy.io.BreezeBill.service;

import kamzy.io.BreezeBill.Utility.EncryptionHelper;
import kamzy.io.BreezeBill.Utility.ValidationHelper;
import kamzy.io.BreezeBill.model.Users;
import kamzy.io.BreezeBill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    EncryptionHelper encrypt;

    @Autowired
    ValidationHelper validate;

    @Autowired
    DatabaseService dbs;
    String query;
    PreparedStatement statement;

    public boolean loginService(String user_id, String password) {
        Users user = userRepo.findByIdNumber(user_id);
        if (user != null){
            return encrypt.verifyPassword(password, user.getPassword_hash());
        }
        return false;
    }

    public String signupService(Users u) {
//        validate id_number and password format
        if (!validate.isValidIdNumber(u.getId_number()) || !validate.isValidPassword(u.getPassword_hash())) {
            throw new IllegalArgumentException("Invalid id number or password format.");
        }
        Users verify_id_number = userRepo.findByIdNumber(u.getId_number());
        if (verify_id_number != null){
            return "User ID already exist";
        }

//        hash password and store to user object
        String hashPassword = encrypt.hashPassword(u.getPassword_hash());
        u.setPassword_hash(hashPassword);
//        save new user object to DB
        userRepo.save(u);
//        verify user is saved in DB
        Users newUser = userRepo.findByIdNumber(u.getId_number());
        return newUser!= null? "Signup Successful" : "Error signing you up";
    }

    public Users getUserProfile(String id_number) {
        return userRepo.findByIdNumber(id_number);
    }

    public String UpdateAccount(Users u) {
        //        validate id_number and password format
        if (!validate.isValidIdNumber(u.getId_number()) || !validate.isValidPassword(u.getPassword_hash())) {
            throw new IllegalArgumentException("Invalid id number or password format.");
        }

//        hash password and store to user object
        String hashPassword = encrypt.hashPassword(u.getPassword_hash());
        u.setPassword_hash(hashPassword);
//        save new user object to DB
        userRepo.save(u);
//        Validate update
        Users newUserInfo = userRepo.findByIdNumber(u.getId_number());
        return newUserInfo!=null ?"Update Successful":"Couldn't update your information";
    }

    public String deleteAccount(String id_number, String password) {
        Users user = userRepo.findByIdNumber(id_number);
        if (user != null){
            boolean verifyPasswordStatus = encrypt.verifyPassword(password, user.getPassword_hash());
            if (verifyPasswordStatus){
                userRepo.deleteById(user.getUser_id());
            }
        }
        Users u = userRepo.findByIdNumber(id_number);
        return u==null ?"Account deleted successfully" : "Invlid User ID or Password";
    }

    public boolean logoutService() {

        return false;
    }
}
