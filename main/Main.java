package main;

import model.Customer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Smart Banking Wallet System - skeleton structure created");
        Customer c = new Customer("c1", "Alice", "alice@example.com");
        System.out.println(c);
    }
}
