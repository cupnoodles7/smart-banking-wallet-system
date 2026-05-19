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

import util.FileLogger;

import wallet.WalletOperations;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final CustomerService customerService = new CustomerService();
    private static final BankingService bankingService   = new BankingService();
    private static final WalletService walletService     = new WalletService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Smart Banking System Started");

        boolean running = true;

        try {
            while (running) {
                printMenu();
                int choice = readInt("Enter choice: ");

                try {
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
                } catch (InvalidAmountException
                        | InsufficientBalanceException
                        | WalletLimitExceededException
                        | DuplicateCustomerException
                        | InvalidEmailException
                        | InvalidPhoneNumberException
                        | CloneNotSupportedException e) {

                    System.out.println("ERROR: " + e.getMessage());
                    FileLogger.logError(e.getClass().getSimpleName() + " - " + e.getMessage());

                } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {

                    System.out.println("ERROR: " + e.getMessage());
                    FileLogger.logError(e.getClass().getSimpleName() + " - " + e.getMessage());
                }
            }
        } finally {
            scanner.close();
            System.out.println("Smart Banking System Closed");
        }
    }

    private static void printMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1. Create Customer");
        System.out.println("2. Open Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer");
        System.out.println("6. Wallet Operations");
        System.out.println("7. Clone Account");
        System.out.println("8. View Transactions");
        System.out.println("9. Exit");
    }

    private static void createCustomer()
            throws DuplicateCustomerException,
                   InvalidEmailException,
                   InvalidPhoneNumberException {

        String id    = readLine("Customer ID: ");
        String name  = readLine("Name: ");
        String email = readLine("Email: ");
        String phone = readLine("Phone (10 digits): ");

        Customer c = customerService.createCustomer(id, name, email, phone);
        System.out.println("Created: " + c);
    }

    private static void openAccount() {
        String customerId    = readLine("Customer ID: ");
        Customer customer    = customerService.findById(customerId);

        String accountNumber = readLine("Account Number: ");
        double balance       = readDouble("Opening Balance: ");
        String type          = readLine("Type (SAVINGS/CURRENT): ");

        BankAccount account = bankingService.openAccount(accountNumber, customer, balance, type);
        account.displayDetails();
    }

    private static void deposit() throws InvalidAmountException {
        String accountNumber = readLine("Account Number: ");
        double amount        = readDouble("Amount: ");
        bankingService.deposit(accountNumber, amount);
    }

    private static void withdraw()
            throws InvalidAmountException, InsufficientBalanceException {

        String accountNumber = readLine("Account Number: ");
        double amount        = readDouble("Amount: ");
        bankingService.withdraw(accountNumber, amount);
    }

    private static void transfer()
            throws InvalidAmountException, InsufficientBalanceException {

        String sender   = readLine("Sender Account: ");
        String receiver = readLine("Receiver Account: ");
        double amount   = readDouble("Amount: ");
        bankingService.transfer(sender, receiver, amount);
    }

    private static void walletOperations()
            throws InvalidAmountException,
                   InsufficientBalanceException,
                   WalletLimitExceededException {

        String customerId = readLine("Customer ID: ");
        customerService.findById(customerId);  // ensures customer exists

        String type = readLine("Wallet type (PAYTM/PHONEPE): ");

        WalletOperations wallet = walletService.linkWallet(customerId, type);

        System.out.println("1. Add Money  2. Pay Bill  3. Transfer To Wallet");
        int op = readInt("Choose operation: ");

        switch (op) {
            case 1 -> {
                double amount = readDouble("Amount: ");
                wallet.addMoney(amount);
            }
            case 2 -> {
                double amount = readDouble("Amount: ");
                wallet.payBill(amount);
            }
            case 3 -> {
                String destCustomerId = readLine("Destination customer ID: ");
                customerService.findById(destCustomerId);
                String destType = readLine("Destination wallet type (PAYTM/PHONEPE): ");
                WalletOperations destWallet = walletService.findWallet(destCustomerId, destType);
                double amount = readDouble("Amount: ");
                wallet.transferToWallet(amount, destWallet);
            }
            default -> System.out.println("Invalid wallet operation.");
        }
    }

    private static void cloneAccount() throws CloneNotSupportedException {
        String accountNumber  = readLine("Account Number to clone: ");
        BankAccount original  = bankingService.findByAccountNumber(accountNumber);
        BankAccount cloned    = bankingService.cloneAccount(accountNumber);

        System.out.println("Original customer: " + original.getCustomer());
        System.out.println("Cloned customer:   " + cloned.getCustomer());

        // Demonstrate deep copy: mutating clone's customer must not affect original.
        cloned.getCustomer().setName(original.getCustomer().getName() + " (clone)");
        System.out.println("After renaming clone:");
        System.out.println("  Original: " + original.getCustomer());
        System.out.println("  Cloned:   " + cloned.getCustomer());
    }

    private static void viewTransactions() {
        String accountNumber = readLine("Account Number: ");
        bankingService.viewTransactions(accountNumber);
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            int v = scanner.nextInt();
            scanner.nextLine();
            return v;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            throw new IllegalArgumentException("Expected a whole number");
        }
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        try {
            double v = scanner.nextDouble();
            scanner.nextLine();
            return v;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            throw new IllegalArgumentException("Expected a numeric amount");
        }
    }
}
