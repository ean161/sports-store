package fcc.sportsstore.utils;

public class Validate {

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
