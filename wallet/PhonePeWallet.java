package wallet;

import exception.*;

public class PhonePeWallet implements WalletOperations {

    private double balance;
    private final double MAX_LIMIT = 50000;

    public PhonePeWallet(double balance) {
        this.balance = balance;
    }

    @Override
    public void addMoney(double amount)
            throws WalletLimitExceededException,
                   InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Invalid amount");
        }

        if (balance + amount > MAX_LIMIT) {
            throw new WalletLimitExceededException(
                    "Wallet limit exceeded");
        }

        balance += amount;

        System.out.println("Money added");
    }

    @Override
    public void payBill(double amount)
            throws InsufficientBalanceException,
                   InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Invalid amount");
        }

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Insufficient balance");
        }

        balance -= amount;

        System.out.println("Bill payment successful");
    }

    @Override
    public void transferToWallet(double amount,
                                 WalletOperations wallet)
            throws InsufficientBalanceException,
                   WalletLimitExceededException,
                   InvalidAmountException {

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Low balance");
        }

        balance -= amount;

        wallet.addMoney(amount);

        System.out.println("Transfer successful");
    }

    public void displayBalance() {
        System.out.println("PhonePe Wallet Balance: ₹" + balance);
    }
}