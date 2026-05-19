# Smart Banking & Digital Wallet System

A Java-based console application built using Object-Oriented Programming concepts and robust Exception Handling mechanisms.

This project simulates a Smart Banking & Digital Wallet platform that supports:

- Customer management
- Bank account operations
- Wallet services
- Transaction handling
- File logging
- Deep cloning
- Menu-driven execution

---

# Features

## Customer Module

- Create customers
- Validate email and phone number
- Prevent duplicate customer IDs

## Banking Module

- Open accounts
- Deposit money
- Withdraw money
- Transfer funds
- View transaction history

## Wallet Module

- Paytm Wallet
- PhonePe Wallet
- Add money
- Pay bills
- Wallet-to-wallet transfers
- Wallet limit validation

## Transaction Module

- Maintain transaction records
- Validate transaction amount

## File Logging

- Store failed transactions in log file
- Handle IOException and FileNotFoundException

## Cloning

- Deep cloning of account/customer profiles
- Prevent shallow copy issues

---

# Technologies Used

- Java 21
- VS Code
- Git & GitHub
- Java Collections Framework
- File Handling
- Exception Handling

---

# OOP Concepts Implemented

- Abstraction
- Encapsulation
- Inheritance
- Polymorphism
- Interfaces
- Abstract Classes
- Method Overriding

---

# Exception Handling

## Custom Exceptions

- DuplicateCustomerException
- InsufficientBalanceException
- InvalidAmountException
- InvalidEmailException
- InvalidPhoneNumberException
- WalletLimitExceededException

## Built-in Exceptions

- IOException
- FileNotFoundException
- IllegalArgumentException
- NullPointerException
- CloneNotSupportedException

---

# Project Structure

```text
smart-banking-wallet-system/
│
├── exception/
│   ├── DuplicateCustomerException.java
│   ├── InsufficientBalanceException.java
│   ├── InvalidAmountException.java
│   ├── InvalidEmailException.java
│   ├── InvalidPhoneNumberException.java
│   └── WalletLimitExceededException.java
│
├── main/
│   ├── BankingController.java
│   ├── GlobalExceptionHandler.java
│   ├── InputHandler.java
│   ├── Main.java
│   └── MenuController.java
│
├── model/
│   ├── BankAccount.java
│   ├── CurrentAccount.java
│   ├── Customer.java
│   ├── SavingsAccount.java
│   └── Transaction.java
│
├── service/
│   ├── BankingService.java
│   ├── CustomerService.java
│   └── WalletService.java
│
├── util/
│   ├── CustomerValidator.java
│   └── FileLogger.java
│
├── wallet/
│   ├── AbstractWallet.java
│   ├── PaytmWallet.java
│   ├── PhonePeWallet.java
│   └── WalletOperations.java
│
├── .gitignore
└── README.md
```

---


---

# Wallet Module Design

To avoid duplicate code between wallet implementations, a common abstract class `AbstractWallet` was introduced.

### Advantages

- Follows DRY Principle
- Improves maintainability
- Promotes code reusability
- Supports future wallet extensions

Example future extensions:

- GooglePayWallet
- AmazonPayWallet

---

# Important Business Rules

## Deposit Rules

- Deposit amount must be greater than 0

## Withdraw Rules

- Cannot withdraw beyond available balance

## Transfer Rules

- Sender and receiver cannot be same account
- Transfer amount must be positive

## Wallet Rules

- Daily transfer limit: ₹20,000
- Maximum wallet balance: ₹50,000

---

# How to Run

## Compile Project

```bash
javac */*.java
```

## Run Application

java main.Main


---

# Sample Error Logs

[ERROR] Invalid deposit amount
[ERROR] Insufficient balance during transfer
[ERROR] Wallet limit exceeded

---

# Git Workflow

- Feature branch development
- Pull Request based integration
- Team collaboration using GitHub

---

# Future Enhancements

- Database Integration
- GUI Application
- Spring Boot APIs
- Authentication & Authorization
- Online Banking Support

---

# Contributors

- Akriti Kheta
- A Sree Vaishnavi
- Kashish Prasad
- Haripriyaa G B

Developed as part of a Java OOP & Exception Handling Case Study Project.
