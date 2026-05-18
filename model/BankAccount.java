package model;

import exception.InvalidAmountException;
import exception.InsufficientBalanceException;

import util.FileLogger;

import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {

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

    private String generateTransactionId() {

        return "TXN" + (++transactionCounter);
    }

    // =========================
    // DEPOSIT
    // =========================

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

        balance += amount;

        try {

            Transaction transaction =
                    new Transaction(
                            generateTransactionId(),
                            amount,
                            "DEPOSIT"
                    );

            transactions.add(transaction);

        } catch (InvalidAmountException e) {

            FileLogger.logError(e.getMessage());
        }

        System.out.println(
                "Deposit successful. Updated Balance: ₹"
                        + balance
        );
    }

    // =========================
    // WITHDRAW
    // =========================

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

        balance -= amount;

        try {

            Transaction transaction =
                    new Transaction(
                            generateTransactionId(),
                            amount,
                            "WITHDRAW"
                    );

            transactions.add(transaction);

        } catch (InvalidAmountException e) {

            FileLogger.logError(e.getMessage());
        }

        System.out.println(
                "Withdrawal successful. Remaining Balance: ₹"
                        + balance
        );
    }

    // =========================
    // TRANSFER
    // =========================

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

        this.balance -= amount;

        receiver.balance += amount;

        try {

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

            transactions.add(senderTransaction);

            receiver.transactions.add(receiverTransaction);

        } catch (InvalidAmountException e) {

            FileLogger.logError(e.getMessage());
        }

        System.out.println(
                "Transfer successful."
        );
    }

    // =========================
    // VIEW TRANSACTIONS
    // =========================

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

    // =========================
    // DISPLAY DETAILS
    // =========================

    public abstract void displayDetails();
}