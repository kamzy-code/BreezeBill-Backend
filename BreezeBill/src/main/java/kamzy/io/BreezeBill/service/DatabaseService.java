package kamzy.io.BreezeBill.service;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseService {

    public Connection getDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url, username, password;
        url = "jdbc:mysql://localhost:3306/breezebill?useSSL=false&serverTimezone=Africa/Lagos";
        username = "root";
        password = "admin";

        return DriverManager.getConnection(url, username, password);
    }
}
