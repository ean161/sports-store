package fcc.sportsstore.utils;

public class Validate {
    
    public boolean isValidFullName(String fullName) {
        fullName = fullName.trim();
        if (fullName.length() < 3 || fullName.length() > 35) {
            return false;
        }

        return fullName.matches("^[a-zA-Z]+$");
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        return phoneNumber.matches("^0\\d{9,11}$");
    }

    /**
     * Validate for password
     * @param email Email to validate
     * @return TRUE if valid, FALSE if invalid
     */
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    /**
     * Validate for username
     * @param username Username to validate
     * @return TRUE if valid, FALSE if invalid
     */
    public boolean isValidUsername(String username) {
        int length = username.length();
        if (length < 6 || length > 30) {
            return false;
        }

        return username.matches("^[a-zA-Z0-9]+$");
    }

    /**
     * Validate for valid password
     * @param password Password to validate
     * @return TRUE if valid, FALSE if invalid
     */
    public boolean isValidPassword(String password) {
        int length = password.length();
        if (length < 6 || length > 30) {
            return false;
        }

        for (char c : password.toCharArray()){
            if (!Character.isLetterOrDigit(c) ){
                return true;
            }
        }

        return false;
    }
}
