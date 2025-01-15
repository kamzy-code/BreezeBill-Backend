package kamzy.io.BreezeBill.Utility;

import org.springframework.stereotype.Component;

@Component
public class ValidationHelper {
    // Validate id_number format (e.g., alphanumeric and 6–11 characters)
    public boolean isValidIdNumber(String idNumber) {
        return idNumber != null && idNumber.matches("^[a-zA-Z0-9]{6,11}$");
    }

    // Validate password strength (e.g., at least 8 characters, including uppercase, lowercase, and a number)
    public boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
}
