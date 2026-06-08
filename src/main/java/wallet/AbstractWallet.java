package wallet;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import exception.WalletLimitExceededException;

import util.AmountValidator;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Template-method parent for all wallet providers
public abstract class AbstractWallet
        implements WalletOperations {

    // Logger object
    private static final Logger logger =
            LoggerFactory.getLogger(AbstractWallet.class);

    protected double balance;

    // Hard ceiling on stored balance
    protected final double MAX_LIMIT = 50000;

    // Per-transfer cap
    protected final double DAILY_TRANSFER_LIMIT = 20000;

    // Wallet transaction history
    private List<Transaction> walletTransactions =
            new ArrayList<>();

    // Constructor
    public AbstractWallet(double balance) {

        this.balance = balance;
    }

    // Helper method to store transactions
    protected void addTransaction(
            String type,
            double amount) {

        try {

            String transactionId =
                    "WTX" + (walletTransactions.size() + 1);

            walletTransactions.add(
                    new Transaction(
                            transactionId,
                            amount,
                            type
                    )
            );

        } catch (InvalidAmountException e) {

            System.out.println(
                    "Failed to create wallet transaction"
            );
        }
    }

    // Add money to wallet
    @Override
    public void addMoney(double amount)
            throws WalletLimitExceededException,
                   InvalidAmountException {

        AmountValidator.requirePositive(
                amount,
                "Top-up"
        );

        if (balance + amount > MAX_LIMIT) {

            logger.error(
                    "Wallet limit exceeded"
            );

            throw new WalletLimitExceededException(
                    "Wallet limit exceeded"
            );
        }

        balance += amount;

        // Store transaction
        addTransaction(
                "WALLET_ADD",
                amount
        );

        logger.info(
                "Wallet money added: {}",
                amount
        );

        System.out.println(
                "Money added successfully"
        );
    }

    // Pay bill from wallet
    @Override
    public void payBill(double amount)
            throws InsufficientBalanceException,
                   InvalidAmountException {

        AmountValidator.requirePositive(
                amount,
                "Bill"
        );

        AmountValidator.requireSufficient(
                balance,
                amount
        );

        balance -= amount;

        // Store transaction
        addTransaction(
                "BILL_PAYMENT",
                amount
        );

        logger.info(
                "Bill payment successful: {}",
                amount
        );

        System.out.println(
                "Bill payment successful"
        );
    }

    // Transfer money to another wallet
    @Override
    public void transferToWallet(
            double amount,
            WalletOperations wallet)

            throws InsufficientBalanceException,
                   WalletLimitExceededException,
                   InvalidAmountException {

        AmountValidator.requirePositive(
                amount,
                "Transfer"
        );

        if (amount > DAILY_TRANSFER_LIMIT) {

            logger.error(
                    "Daily transfer limit exceeded"
            );

            throw new WalletLimitExceededException(
                    "Daily transfer limit exceeded"
            );
        }

        AmountValidator.requireSufficient(
                balance,
                amount
        );

        balance -= amount;

        // Receiver wallet gets money
        wallet.addMoney(amount);

        // Store sender transaction
        addTransaction(
                "WALLET_TRANSFER",
                amount
        );

        logger.info(
                "Wallet transfer successful: {}",
                amount
        );

        System.out.println(
                "Transfer successful"
        );
    }

    // Display wallet balance
    public void displayBalance() {

        logger.info(
                "Displaying wallet balance"
        );

        System.out.println(
                "Wallet Balance: ₹" + balance
        );
    }

    // View wallet transaction history
    public void viewWalletTransactions() {

        logger.info(
                "Viewing wallet transaction history"
        );

        System.out.println(
                "\nWallet Transaction History:"
        );

        if(walletTransactions.isEmpty()) {

            System.out.println(
                    "No wallet transactions found"
            );

            return;
        }

        for(Transaction transaction :
                walletTransactions) {

            System.out.println(transaction);
        }
    }
}