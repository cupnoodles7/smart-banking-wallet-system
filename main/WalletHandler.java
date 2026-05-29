package main;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import exception.WalletLimitExceededException;
import service.CustomerService;
import service.WalletService;
import wallet.WalletOperations;

// Handles the "Wallet Operations" menu flow. Links a wallet to a customer and then runs the inner add-money / pay-bill /
// wallet-to-wallet sub-menu.
public class WalletHandler {

    private final WalletService walletService;
    private final CustomerService customerService;
    private final InputHandler input;

    public WalletHandler(WalletService walletService,
                         CustomerService customerService,
                         InputHandler input) {
        this.walletService = walletService;
        this.customerService = customerService;
        this.input = input;
    }

    public void walletOperations()
            throws InvalidAmountException,
                   InsufficientBalanceException,
                   WalletLimitExceededException {

        String customerId = input.readLine("Customer ID: ");
        customerService.findById(customerId); // guard: customer must exist

        String type = input.readLine("Wallet type (PAYTM/PHONEPE): ");
        WalletOperations wallet = walletService.linkWallet(customerId, type);

        System.out.println("1. Add Money");
        System.out.println("2. Pay Bill");
        System.out.println("3. Transfer To Wallet");
        System.out.println("4. View Wallet Transactions");
        int op = input.readInt("Choose operation: ");

        switch (op) {
            case 1 -> {
                double amount = input.readDouble("Amount: ");
                wallet.addMoney(amount);
            }
            case 2 -> {
                double amount = input.readDouble("Amount: ");
                wallet.payBill(amount);
            }
            case 3 -> {
                String destCustomerId = input.readLine("Destination customer ID: ");
                customerService.findById(destCustomerId);
                String destType = input.readLine("Destination wallet type (PAYTM/PHONEPE): ");
                WalletOperations destWallet = walletService.findWallet(destCustomerId, destType);
                double amount = input.readDouble("Amount: ");
                wallet.transferToWallet(amount, destWallet);
            }
            case 4 -> {
            	((wallet.AbstractWallet) wallet).viewWalletTransactions();
            }
            default -> System.out.println("Invalid wallet operation.");
        }
    }
}
