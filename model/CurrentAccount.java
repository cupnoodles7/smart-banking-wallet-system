package model;
public class CurrentAccount extends BankAccount {
   public CurrentAccount(String accountNumber, Customer customer, double balance) {
       super(accountNumber, customer, balance);
   }
   @Override
   public void displayDetails() {
       System.out.println("----- Current Account -----");
       super.displayDetails();
   }
}