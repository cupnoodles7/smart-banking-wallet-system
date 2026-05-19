package model;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import java.util.ArrayList;
import java.util.List;
import util.AmountValidator;

// Abstract base for all bank accounts. Holds the balance + transaction history
// and implements deposit / withdraw / transfer once so subclasses only
// customize the display format. Implements Cloneable for the deep-copy demo
public abstract class BankAccount implements Cloneable {

    protected String accountNumber;
    protected Customer customer;
    protected double balance;

    protected List<Transaction> transactions = new ArrayList<>();

    // Static so all accounts share one monotonically-increasing ID sequence.
    // Safe only because this app is single-threaded.
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

   

    public void deposit(double amount)
            throws InvalidAmountException {

        AmountValidator.requirePositive(amount, "Deposit");

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

    // =========================
    // WITHDRAW
    // =========================

    public void withdraw(double amount)
            throws InvalidAmountException,
                   InsufficientBalanceException {

        AmountValidator.requirePositive(amount, "Withdrawal");
        AmountValidator.requireSufficient(balance, amount);

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



    public void transfer(BankAccount receiver,
                         double amount)
            throws InvalidAmountException,
                   InsufficientBalanceException {

        // These two checks are transfer-specific (not amount-related), so
        // they stay inline rather than moving to AmountValidator.
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

        AmountValidator.requirePositive(amount, "Transfer");
        AmountValidator.requireSufficient(balance, amount);

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

        System.out.println("Transfer successful.");
    }



    public void viewTransactions() {

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        System.out.println("\nTransaction History:");

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

  
    @Override
    public BankAccount clone() throws CloneNotSupportedException {

        BankAccount cloned = (BankAccount) super.clone();
        cloned.customer = this.customer.clone();
        cloned.transactions = new ArrayList<>(this.transactions);
        return cloned;
    }

    // Each subclass renders its own header line (Savings vs Current).
    public abstract void displayDetails();
}
