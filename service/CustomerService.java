package service;

import exception.DuplicateCustomerException;
import exception.InvalidEmailException;
import exception.InvalidPhoneNumberException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import model.Customer;
import util.CustomerValidator;

public class CustomerService {

    private final Map<String, Customer> customerRegistry = new LinkedHashMap<>();

   
    public Customer createCustomer(String customerId,
                                   String name,
                                   String email,
                                   String phoneNumber)
            throws DuplicateCustomerException,
                   InvalidEmailException,
                   InvalidPhoneNumberException {

        if (customerRegistry.containsKey(customerId)) {
            throw new DuplicateCustomerException(customerId);
        }

        CustomerValidator.validateEmail(email);
        CustomerValidator.validatePhoneNumber(phoneNumber);

        Customer customer = new Customer(customerId, name, email, phoneNumber);
        customerRegistry.put(customerId, customer);
        return customer;
    }

    public Customer findById(String customerId) {
        Customer customer = customerRegistry.get(customerId);
        if (customer == null) {
            throw new NullPointerException("No customer found with ID: " + customerId);
        }
        return customer;
    }

    public Map<String, Customer> getAllCustomers() {
        return Collections.unmodifiableMap(customerRegistry);
    }

    public Customer cloneCustomer(String customerId) throws CloneNotSupportedException {
        Customer original = findById(customerId);   // throws NPE if missing
        return original.clone();
    }
}