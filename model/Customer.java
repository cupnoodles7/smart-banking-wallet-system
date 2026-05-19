package model;

public class Customer implements Cloneable {

    private final String customerId;
    private String name;
    private String email;
    private String phoneNumber;

    public Customer(String customerId, String name, String email, String phoneNumber) {
        this.customerId   = customerId;
        this.name         = name;
        this.email        = email;
        this.phoneNumber  = phoneNumber;
    }

    @Override
    public Customer clone() throws CloneNotSupportedException {
        // String fields are immutable, so field-by-field copy is safe.
        // If mutable fields (e.g. Address object) are added later,
        // clone them explicitly here — that is the deep-copy contract.
        return (Customer) super.clone();
    }

    //getters and setters
    public String getCustomerId()   { return customerId; }
    public String getName()         { return name; }
    public String getEmail()        { return email; }
    public String getPhoneNumber()  { return phoneNumber; }

    public void setName(String name)               { this.name        = name; }
    public void setEmail(String email)             { this.email       = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        return String.format("Customer[id=%s, name=%s, email=%s, phone=%s]",
                customerId, name, email, phoneNumber);
    }
}