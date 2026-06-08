package main;

import exception.DuplicateCustomerException;
import exception.InvalidEmailException;
import exception.InvalidPhoneNumberException;
import model.Customer;
import service.CustomerService;

// Owns the "Create Customer" menu flow
public class CustomerHandler {

    private final CustomerService customerService;
    private final InputHandler input;

    public CustomerHandler(CustomerService customerService, InputHandler input) {
        this.customerService = customerService;
        this.input = input;
    }

    public void createCustomer()
            throws DuplicateCustomerException,
                   InvalidEmailException,
                   InvalidPhoneNumberException {

        String id    = input.readLine("Customer ID: ");
        String name  = input.readLine("Name: ");
        String email = input.readLine("Email: ");
        String phone = input.readLine("Phone (10 digits): ");

        Customer c = customerService.createCustomer(id, name, email, phone);
        System.out.println("Created: " + c);
    }
}
