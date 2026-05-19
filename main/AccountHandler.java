package main;

import model.BankAccount;
import model.Customer;
import service.BankingService;
import service.CustomerService;

// Handles account-lifecycle: opening new accounts and cloning existing ones
public class AccountHandler {

    private final BankingService bankingService;
    private final CustomerService customerService;
    private final InputHandler input;

    public AccountHandler(BankingService bankingService,
                          CustomerService customerService,
                          InputHandler input) {
        this.bankingService = bankingService;
        this.customerService = customerService;
        this.input = input;
    }

    public void openAccount() {
        String customerId    = input.readLine("Customer ID: ");
        Customer customer    = customerService.findById(customerId);

        String accountNumber = input.readLine("Account Number: ");
        double balance       = input.readDouble("Opening Balance: ");
        String type          = input.readLine("Type (SAVINGS/CURRENT): ");

        BankAccount account = bankingService.openAccount(accountNumber, customer, balance, type);
        account.displayDetails();
    }

    // Demonstrates Java's Cloneable
    public void cloneAccount() throws CloneNotSupportedException {
        String accountNumber  = input.readLine("Account Number to clone: ");
        BankAccount original  = bankingService.findByAccountNumber(accountNumber);
        BankAccount cloned    = bankingService.cloneAccount(accountNumber);

        System.out.println("Original customer: " + original.getCustomer());
        System.out.println("Cloned customer:   " + cloned.getCustomer());

        cloned.getCustomer().setName(original.getCustomer().getName() + " (clone)");
        System.out.println("After renaming clone:");
        System.out.println("  Original: " + original.getCustomer());
        System.out.println("  Cloned:   " + cloned.getCustomer());
    }
}
