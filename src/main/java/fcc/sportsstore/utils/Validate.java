package fcc.sportsstore.utils;
public class Validate {

    /**
     * Validate for password
     * @param email A email to validate
     * @return TRUE if valid, FALSE if invalid
     */
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    /**
     * Validate for valid password
     * @param password A password to validate
     * @return TRUE if valid, FALSE if invalid
     */
    public boolean isValidPassword(String password) {
        if (password.length() < 6 || password.length() > 30) {
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
