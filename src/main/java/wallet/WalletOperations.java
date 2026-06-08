package wallet;

import exception.*;

public interface WalletOperations {

    void addMoney(double amount)
            throws WalletLimitExceededException, InvalidAmountException;

    void payBill(double amount)
            throws InsufficientBalanceException, InvalidAmountException;

    void transferToWallet(double amount, WalletOperations wallet)
            throws InsufficientBalanceException,
                   WalletLimitExceededException,
                   InvalidAmountException;
}