package model;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
public abstract class BankAccount implements Cloneable {
   protected String accountNumber;
   protected Customer customer;
   protected double balance;
   public BankAccount(String accountNumber, Customer customer, double balance) {
       this.accountNumber = accountNumber;
       this.customer = customer;
       this.balance = balance;
   }
   // DEPOSIT
   public void deposit(double amount) throws InvalidAmountException {
       if (amount <= 0) {
           throw new InvalidAmountException("Deposit amount must be greater than 0");
       }
       balance += amount;
       System.out.println("Amount Deposited: ₹" + amount);
   }
   // WITHDRAW 
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
   // TRANSFER 
   public void transfer(BankAccount receiver, double amount)
           throws InvalidAmountException, InsufficientBalanceException {
       if (this.accountNumber.equals(receiver.accountNumber)) {
           throw new IllegalArgumentException("Sender and receiver cannot be same account");
       }
       withdraw(amount);
       receiver.deposit(amount);
       System.out.println("Transferred ₹" + amount + " successfully");
   }
   // DISPLAY 
   public void displayDetails() {
       System.out.println("Account Number: " + accountNumber);
       System.out.println("Customer: " + customer.getName());
       System.out.println("Balance: ₹" + balance);
   }
   public Customer getCustomer(){
    return customer;
   }
   
   // SHALLOW COPY + DEEP COPY FIX (CLONING SECTION)
   @Override
   public BankAccount clone() throws CloneNotSupportedException {
       // SHALLOW COPY
       BankAccount cloned = (BankAccount) super.clone();
       // DEEP COPY FIX (customer object recreated)
       cloned.customer = new Customer(
               customer.getCustomerId(),
               customer.getName(),
               customer.getEmail(),
               customer.getPhoneNumber()
       );
       return cloned;
   }
}