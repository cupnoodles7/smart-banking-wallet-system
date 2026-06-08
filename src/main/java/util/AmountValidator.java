package util;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;

// Centralizes the two amount-related checks that used to be copy-pasted across BankAccount, AbstractWallet, and Transaction.
public class AmountValidator {

    private AmountValidator() {}

    // Rejects zero or negative amounts.
    public static void requirePositive(double amount, String operation)
            throws InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    operation + " amount must be greater than zero"
            );
        }
    }

    // Rejects withdrawals/transfers/bills that exceed the available balance.
    public static void requireSufficient(double balance, double amount)
            throws InsufficientBalanceException {

        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
    }
}
