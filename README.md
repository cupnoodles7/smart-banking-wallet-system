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

# Wallet Module Design

To avoid duplicate code between wallet implementations, a common abstract class `AbstractWallet` was introduced.

### Advantages

- Follows DRY Principle
- Improves maintainability
- Promotes code reusability
- Supports future wallet extensions

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

# Quick Start

### 1. Clean previous build

```bash
rm -rf bin
```

### 2. Compile

```bash
javac -d bin exception/*.java model/*.java util/*.java wallet/*.java service/*.java main/*.java
```

### 3. Run

```bash
java -cp bin main.Main
```
