package model;

import exception.InvalidAmountException;
import exception.InsufficientBalanceException;

import util.FileLogger;

import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount implements Cloneable {

    protected String accountNumber;
    protected Customer customer;
    protected double balance;

    protected List<Transaction> transactions =
            new ArrayList<>();

    private static int transactionCounter = 100;

    public BankAccount(String accountNumber,
                       Customer customer,
                       double balance) {

        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public Customer getCustomer()    { return customer; }
    public double getBalance()       { return balance; }

    private String generateTransactionId() {

        return "TXN" + (++transactionCounter);
    }

    
    // DEPOSIT
    public void deposit(double amount)
            throws InvalidAmountException {

        if (amount <= 0) {

            FileLogger.logError(
                    "Invalid deposit amount attempted"
            );

            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero"
            );
        }

        Transaction transaction =
                new Transaction(
                        generateTransactionId(),
                        amount,
                        "DEPOSIT"
                );

        balance += amount;
        transactions.add(transaction);

        System.out.println(
                "Deposit successful. Updated Balance: ₹"
                        + balance
        );
    }

    // WITHDRAW
    public void withdraw(double amount)
            throws InvalidAmountException,
            InsufficientBalanceException {

        if (amount <= 0) {

            FileLogger.logError(
                    "Invalid withdrawal amount attempted"
            );

            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero"
            );
        }

        if (amount > balance) {

            FileLogger.logError(
                    "Insufficient balance during withdrawal"
            );

            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        Transaction transaction =
                new Transaction(
                        generateTransactionId(),
                        amount,
                        "WITHDRAW"
                );

        balance -= amount;
        transactions.add(transaction);

        System.out.println(
                "Withdrawal successful. Remaining Balance: ₹"
                        + balance
        );
    }

   
    // TRANSFER

    public void transfer(BankAccount receiver,
                         double amount)
            throws InvalidAmountException,
            InsufficientBalanceException {

        if (receiver == null) {

            throw new NullPointerException(
                    "Receiver account cannot be null"
            );
        }

        if (this == receiver) {

            throw new IllegalArgumentException(
                    "Sender and receiver cannot be same account"
            );
        }

        if (amount <= 0) {

            throw new InvalidAmountException(
                    "Transfer amount must be greater than zero"
            );
        }

        if (amount > balance) {

            throw new InsufficientBalanceException(
                    "Insufficient balance for transfer"
            );
        }

        Transaction senderTransaction =
                new Transaction(
                        generateTransactionId(),
                        amount,
                        "TRANSFER SENT"
                );

        Transaction receiverTransaction =
                new Transaction(
                        generateTransactionId(),
                        amount,
                        "TRANSFER RECEIVED"
                );

        this.balance -= amount;
        receiver.balance += amount;

        transactions.add(senderTransaction);
        receiver.transactions.add(receiverTransaction);

        System.out.println(
                "Transfer successful."
        );
    }

  
    // VIEW TRANSACTIONS

    public void viewTransactions() {

        if (transactions.isEmpty()) {

            System.out.println(
                    "No transactions available."
            );

            return;
        }

        System.out.println(
                "\nTransaction History:"
        );

        for (Transaction transaction : transactions) {

            System.out.println(transaction);
        }
    }


    // CLONING
    // Deep copy: clone the mutable Customer reference and create a fresh
    // transactions list, otherwise modifying the clone leaks back to the original.
    @Override
    public BankAccount clone() throws CloneNotSupportedException {

        BankAccount cloned = (BankAccount) super.clone();
        cloned.customer = this.customer.clone();
        cloned.transactions = new ArrayList<>(this.transactions);
        return cloned;
    }

    // DISPLAY DETAILS
    public void displayDetails() {
        System.out.println();
        System.out.println("ACCOUNT DETAILS");
        System.out.println(getAccountType());
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: ₹" + balance);
}

    // Subclasses provide only the account type
    protected abstract String getAccountType();
}
