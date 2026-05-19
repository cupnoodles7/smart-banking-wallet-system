package wallet;

import exception.*;

public class PaytmWallet implements WalletOperations {

    private double balance;
    private final double MAX_LIMIT = 50000;
    private final double DAILY_TRANSFER_LIMIT = 20000;

    public PaytmWallet(double balance) {
        this.balance = balance;
    }

    @Override
    public void addMoney(double amount)
            throws WalletLimitExceededException, InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Amount must be positive");
        }

        if (balance + amount > MAX_LIMIT) {
            throw new WalletLimitExceededException(
                    "Wallet limit exceeded");
        }

        balance += amount;

        System.out.println("Money added successfully");
    }

    @Override
    public void payBill(double amount)
            throws InsufficientBalanceException,
                   InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Invalid bill amount");
        }

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Not enough balance");
        }

        balance -= amount;

        System.out.println("Bill paid successfully");
    }

    @Override
    public void transferToWallet(double amount,
                                 WalletOperations wallet)
            throws InsufficientBalanceException,
                   WalletLimitExceededException,
                   InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Transfer amount must be positive");
        }

        if (amount > DAILY_TRANSFER_LIMIT) {
            throw new WalletLimitExceededException(
                    "Daily transfer limit exceeded");
        }

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Insufficient wallet balance");
        }

        balance -= amount;

        wallet.addMoney(amount);

        System.out.println("Transfer successful");
    }

    public void displayBalance() {
        System.out.println("Paytm Wallet Balance: ₹" + balance);
    }
}