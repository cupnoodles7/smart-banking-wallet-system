package main;

import service.BankingService;
import service.CustomerService;
import service.WalletService;


public class BankingController {

    // Services are the in-memory data layer — shared across all handlers.
    private final CustomerService customerService = new CustomerService();
    private final BankingService bankingService   = new BankingService();
    private final WalletService walletService     = new WalletService();

    private final MenuController menuController = new MenuController();
    private final InputHandler input            = new InputHandler();

    // One handler/controller per feature area, each is given only the services it needs
    private final CustomerHandler customerHandler =
            new CustomerHandler(customerService, input);
    private final AccountHandler accountHandler =
            new AccountHandler(bankingService, customerService, input);
    private final TransactionHandler transactionHandler =
            new TransactionHandler(bankingService, input);
    private final WalletHandler walletHandler =
            new WalletHandler(walletService, customerService, input);

    public void run() {

        boolean running = true;

        while (running) {
            menuController.printMenu();
            
            try {
                int choice = input.readInt("Enter choice: ");

                switch (choice) {
                    case 1 -> customerHandler.createCustomer();
                    case 2 -> accountHandler.openAccount();
                    case 3 -> transactionHandler.deposit();
                    case 4 -> transactionHandler.withdraw();
                    case 5 -> transactionHandler.transfer();
                    case 6 -> walletHandler.walletOperations();
                    case 7 -> accountHandler.cloneAccount();
                    case 8 -> transactionHandler.viewTransactions();
                    case 9 -> running = false;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                GlobalExceptionHandler.handle(e);
            }
        }
    }

    public void shutdown() {
        input.close();
    }
}
