package exception;

public class InvalidEmailException extends Exception {

    public InvalidEmailException(String email) {
        super("Invalid email address: '" + email + "'. Must contain '@'.");
    }
}