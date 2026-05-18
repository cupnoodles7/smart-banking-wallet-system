package model;

public class SavingsAccount extends BankAccount {

    public SavingsAccount(String accountNumber,
                          Customer customer,
                          double balance) {

        super(accountNumber, customer, balance);
    }

    @Override
    public void displayDetails() {

        System.out.println("Savings Account");

        System.out.println(
                "Account Number: "
                        + accountNumber
        );

        System.out.println(
                "Balance: ₹"
                        + balance
        );
    }
}