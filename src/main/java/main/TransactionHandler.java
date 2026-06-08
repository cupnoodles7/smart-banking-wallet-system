package main;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import service.BankingService;

// Handles all money-movement menu flows: deposit, withdraw, transfer, and view-transactions
public class TransactionHandler {

    private final BankingService bankingService;
    private final InputHandler input;

    public TransactionHandler(BankingService bankingService, InputHandler input) {
        this.bankingService = bankingService;
        this.input = input;
    }

    public void deposit() throws InvalidAmountException {
        String accountNumber = input.readLine("Account Number: ");
        double amount        = input.readDouble("Amount: ");
        bankingService.deposit(accountNumber, amount);
    }

    public void withdraw()
            throws InvalidAmountException, InsufficientBalanceException {
        String accountNumber = input.readLine("Account Number: ");
        double amount        = input.readDouble("Amount: ");
        bankingService.withdraw(accountNumber, amount);
    }

    public void transfer()
            throws InvalidAmountException, InsufficientBalanceException {
        String sender   = input.readLine("Sender Account: ");
        String receiver = input.readLine("Receiver Account: ");
        double amount   = input.readDouble("Amount: ");
        bankingService.transfer(sender, receiver, amount);
    }

    public void viewTransactions() {
        String accountNumber = input.readLine("Account Number: ");
        bankingService.viewTransactions(accountNumber);
    }
}
