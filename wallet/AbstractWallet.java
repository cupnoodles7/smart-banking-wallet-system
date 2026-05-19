package wallet;

import exception.*;
import util.AmountValidator;

// Template-method parent for all wallet providers
public abstract class AbstractWallet
        implements WalletOperations {

    protected double balance;

    // Hard ceiling on stored balance, checked on every top-up.
    protected final double MAX_LIMIT = 50000;

    // Per-transfer cap (acts as a stand-in for a real daily aggregate).
    protected final double DAILY_TRANSFER_LIMIT = 20000;

    public AbstractWallet(double balance) {
        this.balance = balance;
    }

    @Override
    public void addMoney(double amount)
            throws WalletLimitExceededException,
                   InvalidAmountException {

        AmountValidator.requirePositive(amount, "Top-up");

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

        AmountValidator.requirePositive(amount, "Bill");
        AmountValidator.requireSufficient(balance, amount);

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

        AmountValidator.requirePositive(amount, "Transfer");

        // Daily cap is wallet-specific (not just "positive amount"), so it
        // stays inline rather than moving to AmountValidator.
        if (amount > DAILY_TRANSFER_LIMIT) {
            throw new WalletLimitExceededException(
                    "Daily transfer limit exceeded"
            );
        }

        AmountValidator.requireSufficient(balance, amount);

        balance -= amount;

        // Destination's addMoney re-validates its own MAX_LIMIT, so we
        // don't need to check it here.
        wallet.addMoney(amount);

        System.out.println("Transfer successful");
    }

    public void displayBalance() {
        System.out.println(
                "Wallet Balance: ₹" + balance
        );
    }
}
