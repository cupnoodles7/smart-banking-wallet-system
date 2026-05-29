package service;

import exception.DuplicateCustomerException;
import exception.InvalidEmailException;
import exception.InvalidPhoneNumberException;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Customer;

import util.CustomerValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// In-memory customer store and related operations
public class CustomerService {

    private final Map<String, Customer> customerRegistry =
            new LinkedHashMap<>();

    // Logger object
    private static final Logger logger =
            LoggerFactory.getLogger(CustomerService.class);

    public Customer createCustomer(String customerId,
                                   String name,
                                   String email,
                                   String phoneNumber)
            throws DuplicateCustomerException,
                   InvalidEmailException,
                   InvalidPhoneNumberException {

        logger.info("Customer registration started");

        // Duplicate customer check
        if (customerRegistry.containsKey(customerId)) {

            logger.error(
                    "Duplicate customer ID detected: {}",
                    customerId
            );

            throw new DuplicateCustomerException(customerId);
        }

        // Email validation
        logger.info("Validating email");

        CustomerValidator.validateEmail(email);

        // Phone validation
        logger.info("Validating phone number");

        CustomerValidator.validatePhoneNumber(phoneNumber);

        // Customer creation
        Customer customer = new Customer(
                customerId,
                name,
                email,
                phoneNumber
        );

        customerRegistry.put(customerId, customer);

        logger.info(
                "Customer created successfully: {}",
                customerId
        );

        return customer;
    }

    public Customer findById(String customerId) {

        logger.info(
                "Searching customer with ID: {}",
                customerId
        );

        Customer customer = customerRegistry.get(customerId);

        if (customer == null) {

            logger.error(
                    "No customer found with ID: {}",
                    customerId
            );

            throw new NullPointerException(
                    "No customer found with ID: " +
                    customerId
            );
        }

        logger.info("Customer found successfully");

        return customer;
    }

    public Map<String, Customer> getAllCustomers() {

        logger.info("Fetching all customers");

        return Collections.unmodifiableMap(customerRegistry);
    }

    public Customer cloneCustomer(String customerId)
            throws CloneNotSupportedException {

        logger.info(
                "Cloning customer with ID: {}",
                customerId
        );

        Customer original = findById(customerId);

        logger.info("Customer clone created successfully");

        return original.clone();
    }
}