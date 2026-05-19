package model;

public class SavingsAccount extends BankAccount {

    public SavingsAccount(String accountNumber,
                          Customer customer,
                          double balance) {

        super(accountNumber, customer, balance);
    }

    @Override
    protected String getAccountType() {
        return "Savings Account";
    }
}