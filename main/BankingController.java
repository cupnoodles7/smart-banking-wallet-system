package main;

import exception.DuplicateCustomerException;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import exception.InvalidEmailException;
import exception.InvalidPhoneNumberException;
import exception.WalletLimitExceededException;

import model.BankAccount;
import model.Customer;

import service.BankingService;
import service.CustomerService;
import service.WalletService;

import wallet.WalletOperations;

public class BankingController {

    private final CustomerService customerService = new CustomerService();
    private final BankingService bankingService   = new BankingService();
    private final WalletService walletService     = new WalletService();

    private final MenuController menuController = new MenuController();
    private final InputHandler input            = new InputHandler();

    public void run() {

        boolean running = true;

        while (running) {
            menuController.printMenu();

            try {
                int choice = input.readInt("Enter choice: ");

                switch (choice) {
                    case 1 -> createCustomer();
                    case 2 -> openAccount();
                    case 3 -> deposit();
                    case 4 -> withdraw();
                    case 5 -> transfer();
                    case 6 -> walletOperations();
                    case 7 -> cloneAccount();
                    case 8 -> viewTransactions();
                    case 9 -> running = false;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                GlobalExceptionHandler.handle(e);
            }
        }
    }

    public void shutdown() {
        input.close();
    }

    private void createCustomer()
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

    private void openAccount() {
        String customerId    = input.readLine("Customer ID: ");
        Customer customer    = customerService.findById(customerId);

        String accountNumber = input.readLine("Account Number: ");
        double balance       = input.readDouble("Opening Balance: ");
        String type          = input.readLine("Type (SAVINGS/CURRENT): ");

        BankAccount account = bankingService.openAccount(accountNumber, customer, balance, type);
        account.displayDetails();
    }

    private void deposit() throws InvalidAmountException {
        String accountNumber = input.readLine("Account Number: ");
        double amount        = input.readDouble("Amount: ");
        bankingService.deposit(accountNumber, amount);
    }

    private void withdraw()
            throws InvalidAmountException, InsufficientBalanceException {

        String accountNumber = input.readLine("Account Number: ");
        double amount        = input.readDouble("Amount: ");
        bankingService.withdraw(accountNumber, amount);
    }

    private void transfer()
            throws InvalidAmountException, InsufficientBalanceException {

        String sender   = input.readLine("Sender Account: ");
        String receiver = input.readLine("Receiver Account: ");
        double amount   = input.readDouble("Amount: ");
        bankingService.transfer(sender, receiver, amount);
    }

    private void walletOperations()
            throws InvalidAmountException,
                   InsufficientBalanceException,
                   WalletLimitExceededException {

        String customerId = input.readLine("Customer ID: ");
        customerService.findById(customerId);

        String type = input.readLine("Wallet type (PAYTM/PHONEPE): ");
        WalletOperations wallet = walletService.linkWallet(customerId, type);

        System.out.println("1. Add Money  2. Pay Bill  3. Transfer To Wallet");
        int op = input.readInt("Choose operation: ");

        switch (op) {
            case 1 -> {
                double amount = input.readDouble("Amount: ");
                wallet.addMoney(amount);
            }
            case 2 -> {
                double amount = input.readDouble("Amount: ");
                wallet.payBill(amount);
            }
            case 3 -> {
                String destCustomerId = input.readLine("Destination customer ID: ");
                customerService.findById(destCustomerId);
                String destType = input.readLine("Destination wallet type (PAYTM/PHONEPE): ");
                WalletOperations destWallet = walletService.findWallet(destCustomerId, destType);
                double amount = input.readDouble("Amount: ");
                wallet.transferToWallet(amount, destWallet);
            }
            default -> System.out.println("Invalid wallet operation.");
        }
    }

    private void cloneAccount() throws CloneNotSupportedException {
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

    private void viewTransactions() {
        String accountNumber = input.readLine("Account Number: ");
        bankingService.viewTransactions(accountNumber);
    }
}
