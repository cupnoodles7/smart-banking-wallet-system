package service;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import model.BankAccount;
import model.CurrentAccount;
import model.Customer;
import model.SavingsAccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankingService {

    private final Map<String, BankAccount> accountRegistry =
            new LinkedHashMap<>();

    private static final Logger logger =
            LoggerFactory.getLogger(BankingService.class);

    public BankAccount openAccount(String accountNumber,
                                   Customer customer,
                                   double openingBalance,
                                   String type) {

        logger.info("Opening account process started");

        if (customer == null) {

            logger.error("Customer object is null");

            throw new NullPointerException(
                    "Customer cannot be null"
            );
        }

        if (openingBalance < 0) {

            logger.error("Negative opening balance attempted: {}",
                    openingBalance);

            throw new IllegalArgumentException(
                    "Opening balance cannot be negative"
            );
        }

        BankAccount account;

        if ("SAVINGS".equalsIgnoreCase(type)) {

            logger.info("Creating Savings Account");

            account = new SavingsAccount(
                    accountNumber,
                    customer,
                    openingBalance
            );

        }
        else if ("CURRENT".equalsIgnoreCase(type)) {

            logger.info("Creating Current Account");

            account = new CurrentAccount(
                    accountNumber,
                    customer,
                    openingBalance
            );

        }
        else {

            logger.error("Invalid account type provided: {}", type);

            throw new IllegalArgumentException(
                    "Unknown account type: '" + type +
                    "'. Use SAVINGS or CURRENT."
            );
        }

        accountRegistry.put(accountNumber, account);

        logger.info("Account created successfully: {}", accountNumber);

        return account;
    }

    public BankAccount findByAccountNumber(String accountNumber) {

        logger.info("Searching account: {}", accountNumber);

        BankAccount account = accountRegistry.get(accountNumber);

        if (account == null) {

            logger.error("No account found with number: {}",
                    accountNumber);

            throw new NullPointerException(
                    "No account found with number: " +
                    accountNumber
            );
        }

        logger.info("Account found successfully");

        return account;
    }

    public void deposit(String accountNumber, double amount)
            throws InvalidAmountException {

        logger.info("Deposit request received");

        findByAccountNumber(accountNumber).deposit(amount);

        logger.info("Deposit successful. Amount: {}", amount);
    }

    public void withdraw(String accountNumber, double amount)
            throws InvalidAmountException,
                   InsufficientBalanceException {

        logger.info("Withdrawal request received");

        findByAccountNumber(accountNumber).withdraw(amount);

        logger.info("Withdrawal successful. Amount: {}", amount);
    }

    public void transfer(String senderAccount,
                         String receiverAccount,
                         double amount)
            throws InvalidAmountException,
                   InsufficientBalanceException {

        logger.info("Transfer started");

        BankAccount sender =
                findByAccountNumber(senderAccount);

        BankAccount receiver =
                findByAccountNumber(receiverAccount);

        sender.transfer(receiver, amount);

        logger.info(
                "Transfer successful from {} to {} amount {}",
                senderAccount,
                receiverAccount,
                amount
        );
    }

    public BankAccount cloneAccount(String accountNumber)
            throws CloneNotSupportedException {

        logger.info("Cloning account: {}", accountNumber);

        return findByAccountNumber(accountNumber).clone();
    }

    public void viewTransactions(String accountNumber) {

        logger.info("Viewing transactions for account: {}",
                accountNumber);

        findByAccountNumber(accountNumber).viewTransactions();
    }

    public Map<String, BankAccount> getAllAccounts() {

        logger.info("Fetching all registered accounts");

        return Collections.unmodifiableMap(accountRegistry);
    }
}