package main;

import wallet.*;
import exception.*;

public class Main {

    public static void main(String[] args) {

        PaytmWallet paytm = new PaytmWallet(10000);
        PhonePeWallet phonepe = new PhonePeWallet(5000);

        try {

            paytm.addMoney(2000);

            paytm.payBill(3000);

            paytm.transferToWallet(4000, phonepe);

            paytm.displayBalance();
            phonepe.displayBalance();

        }
        catch (WalletLimitExceededException e) {
            System.out.println(e.getMessage());
        }
        catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }
        catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        }
        finally {
            System.out.println("Wallet operation completed");
        }
    }
}