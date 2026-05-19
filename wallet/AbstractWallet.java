package wallet;

import exception.*;

public abstract class AbstractWallet
        implements WalletOperations {

    protected double balance;

    protected final double MAX_LIMIT = 50000;

    protected final double DAILY_TRANSFER_LIMIT = 20000;

    public AbstractWallet(double balance) {
        this.balance = balance;
    }

    @Override
    public void addMoney(double amount)
            throws WalletLimitExceededException,
                   InvalidAmountException {

        if (amount <= 0) {

            throw new InvalidAmountException(
                    "Amount must be positive"
            );
        }

        if (balance + amount > MAX_LIMIT) {

            throw new WalletLimitExceededException(
                    "Wallet limit exceeded"
            );
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
                    "Invalid amount"
            );
        }

        if (amount > balance) {

            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        balance -= amount;

        System.out.println("Bill payment successful");
    }

    @Override
    public void transferToWallet(
            double amount,
            WalletOperations wallet)

            throws InsufficientBalanceException,
                   WalletLimitExceededException,
                   InvalidAmountException {

        if (amount <= 0) {

            throw new InvalidAmountException(
                    "Transfer amount invalid"
            );
        }

        if (amount > DAILY_TRANSFER_LIMIT) {

            throw new WalletLimitExceededException(
                    "Daily transfer limit exceeded"
            );
        }

        if (amount > balance) {

            throw new InsufficientBalanceException(
                    "Low balance"
            );
        }

        balance -= amount;

        wallet.addMoney(amount);

        System.out.println("Transfer successful");
    }

    public void displayBalance() {

        System.out.println(
                "Wallet Balance: ₹" + balance
        );
    }
}