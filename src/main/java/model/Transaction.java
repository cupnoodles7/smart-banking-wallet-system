package model;

import exception.InvalidAmountException;
import java.time.LocalDateTime;
import util.AmountValidator;

public class Transaction {

    private String transactionId;
    private double amount;
    private String type;
    private LocalDateTime timestamp;

    public Transaction(String transactionId,
                       double amount,
                       String type)
            throws InvalidAmountException {

        AmountValidator.requirePositive(amount, "Transaction");

        this.transactionId = transactionId;
        this.amount = amount;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId +
                " | Type: " + type +
                " | Amount: ₹" + amount +
                " | Time: " + timestamp;
    }
}
