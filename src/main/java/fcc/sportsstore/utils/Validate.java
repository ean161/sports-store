package fcc.sportsstore.utils;

public class Validate {
    
    public boolean isValidFullName(String fullName) {
        fullName = fullName.trim();
        if (fullName.length() < 3 || fullName.length() > 35) {
            return false;
        }

        return fullName.matches("^[a-zA-Z ]+$");
    }

    public boolean isValidCollectionName(String collectionName) {
        collectionName = collectionName.trim();
        if (collectionName.length() < 3 || collectionName.length() > 35) {
            return false;
        }

        return collectionName.matches("^[a-zA-Z ]+$");
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        return phoneNumber.matches("^0\\d{9}$");
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean isValidUsername(String username) {
        int length = username.length();
        if (length < 6 || length > 30) {
            return false;
        }

        return username.matches("^[a-zA-Z0-9]+$");
    }

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

    public boolean isValidNote(String note) {
        if (note == null) return false;
        int length = note.trim().length();
        return length >= 2 && length <= 20;
    }

    public boolean isValidAddress(String address) {
        if (address == null) return false;
        address = address.trim();
        String regexAddress = "^[\\p{L}0-9 ,.\\-\\/]+$";
        return address.matches(regexAddress) && address.length() >= 5 && address.length() <= 200;
    }
}
