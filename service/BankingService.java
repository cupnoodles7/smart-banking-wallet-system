package service;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;

import model.BankAccount;
import model.CurrentAccount;
import model.Customer;
import model.SavingsAccount;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class BankingService {

    private final Map<String, BankAccount> accountRegistry = new LinkedHashMap<>();

    public BankAccount openAccount(String accountNumber,
                                   Customer customer,
                                   double openingBalance,
                                   String type) {

        if (customer == null) {
            throw new NullPointerException("Customer cannot be null");
        }

        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative");
        }

        BankAccount account;
        if ("SAVINGS".equalsIgnoreCase(type)) {
            account = new SavingsAccount(accountNumber, customer, openingBalance);
        } else if ("CURRENT".equalsIgnoreCase(type)) {
            account = new CurrentAccount(accountNumber, customer, openingBalance);
        } else {
            throw new IllegalArgumentException(
                    "Unknown account type: '" + type + "'. Use SAVINGS or CURRENT.");
        }

        accountRegistry.put(accountNumber, account);
        return account;
    }

    public BankAccount findByAccountNumber(String accountNumber) {
        BankAccount account = accountRegistry.get(accountNumber);
        if (account == null) {
            throw new NullPointerException("No account found with number: " + accountNumber);
        }
        return account;
    }

    public void deposit(String accountNumber, double amount)
            throws InvalidAmountException {

        findByAccountNumber(accountNumber).deposit(amount);
    }

    public void withdraw(String accountNumber, double amount)
            throws InvalidAmountException, InsufficientBalanceException {

        findByAccountNumber(accountNumber).withdraw(amount);
    }

    public void transfer(String senderAccount, String receiverAccount, double amount)
            throws InvalidAmountException, InsufficientBalanceException {

        BankAccount sender   = findByAccountNumber(senderAccount);
        BankAccount receiver = findByAccountNumber(receiverAccount);

        sender.transfer(receiver, amount);
    }

    public BankAccount cloneAccount(String accountNumber)
            throws CloneNotSupportedException {

        return findByAccountNumber(accountNumber).clone();
    }

    public void viewTransactions(String accountNumber) {
        findByAccountNumber(accountNumber).viewTransactions();
    }

    public Map<String, BankAccount> getAllAccounts() {
        return Collections.unmodifiableMap(accountRegistry);
    }
}
