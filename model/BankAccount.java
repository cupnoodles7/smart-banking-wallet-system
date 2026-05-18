package model;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
public abstract class BankAccount {
   protected String accountNumber;
   protected Customer customer;
   protected double balance;
   public BankAccount(String accountNumber, Customer customer, double balance) {
       this.accountNumber = accountNumber;
       this.customer = customer;
       this.balance = balance;
   }
   public void deposit(double amount) throws InvalidAmountException {
       if (amount <= 0) {
           throw new InvalidAmountException("Deposit amount must be greater than 0");
       }
       balance += amount;
       System.out.println("Amount Deposited: ₹" + amount);
   }
   public void withdraw(double amount)
           throws InvalidAmountException, InsufficientBalanceException {
       if (amount <= 0) {
           throw new InvalidAmountException("Withdraw amount must be greater than 0");
       }
       if (amount > balance) {
           throw new InsufficientBalanceException("Insufficient balance");
       }
       balance -= amount;
       System.out.println("Amount Withdrawn: ₹" + amount);
   }
   public void transfer(BankAccount receiver, double amount)
           throws InvalidAmountException, InsufficientBalanceException {
       if (this.accountNumber.equals(receiver.accountNumber)) {
           throw new IllegalArgumentException("Sender and receiver cannot be same account");
       }
       withdraw(amount);
       receiver.deposit(amount);
       System.out.println("Transferred ₹" + amount + " successfully");
   }
   public void displayDetails() {
       System.out.println("Account Number: " + accountNumber);
       System.out.println("Customer: " + customer.getName());
       System.out.println("Balance: ₹" + balance);
   }
}