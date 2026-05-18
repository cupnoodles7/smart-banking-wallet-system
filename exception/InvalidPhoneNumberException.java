package exception;

public class InvalidPhoneNumberException extends Exception {

    public InvalidPhoneNumberException(String phone) {
        super("Invalid phone number: '" + phone + "'. Must be exactly 10 digits.");
    }
    
}
