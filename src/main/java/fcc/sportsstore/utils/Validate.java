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
        if (collectionName.length() > 35) {
            return false;
        }

        return collectionName.matches("^[a-zA-Z0-9 ]+$");
    }

    public boolean isValidProductTypeName(String typeName) {
        typeName = typeName.trim();
        if (typeName.length() > 25) {
            return false;
        }

        return typeName.matches("^[a-zA-Z0-9 ]+$");
    }

    public boolean isValidProductPropertyFieldName(String fieldName) {
        fieldName = fieldName.trim();
        if (fieldName.length() > 20) {
            return false;
        }

        return fieldName.matches("^[a-zA-Z0-9 ]+$");
    }

    public boolean isValidProductTitle(String productTitle) {
        productTitle = productTitle.trim();
        if (productTitle.length() > 35) {
            return false;
        }

        return productTitle.matches("^[a-zA-Z0-9 ]+$");
    }

    public boolean isValidProductDescription(String description) {
        description = description.trim();
        if (description.length() > 250) {
            return false;
        }

        return description.matches("^[a-zA-Z0-9 ]+$");
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

    // Code dị coi được?
    public boolean isValidNote(String note) {
       note = note.trim();
       if(note.length() > 150){
           return false;
       }
        return note.matches("^[a-zA-Z0-9 ]+$");
    }

    // Code dị coi được?
    public boolean isValidAddress(String address) {
        address = address.trim();
        if(address.length() <5){
            return false;
        }
        return address.matches("^[\\p{L}0-9 ,.\\-\\/]+$") ;
    }

    public boolean isValidCode(String code){
        code = code.trim();
        if(code.length() < 8|| code.length() >35){
            return false;
        }
        return code.matches("^[a-zA-Z0-9]+$");

    }

    public boolean isValidStatus(String status){
        status = status.trim();
        return status.equals("ACTIVE") || status.equals("DISABLED");
    }
}
