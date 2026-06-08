package exception;

public class DuplicateCustomerException extends Exception {
    
    public DuplicateCustomerException(String customerId) {
        super("Customer with ID '" + customerId + "' already exists.");
    }
}
