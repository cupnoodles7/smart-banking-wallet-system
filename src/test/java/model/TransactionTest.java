package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exception.InvalidAmountException;

public class TransactionTest {

    @Test
    void testTransactionConstructorAndGetters() throws InvalidAmountException {
        Transaction transaction = new Transaction("TXN001", 1000.0, "DEPOSIT");
        assertEquals("TXN001", transaction.getTransactionId());
        assertEquals(1000.0, transaction.getAmount());
        assertEquals("DEPOSIT", transaction.getType());
    }

    @Test
    void testTimestampIsNotNull() throws InvalidAmountException {
        Transaction transaction = new Transaction("TXN002", 500.0, "WITHDRAW");
        assertNotNull(transaction.getTimestamp());
    }

    @Test
    void testNegativeAmountThrowsException() {
        assertThrows(InvalidAmountException.class, () ->
            new Transaction("TXN003", -100.0, "DEPOSIT")
        );
    }

    @Test
    void testZeroAmountThrowsException() {
        assertThrows(InvalidAmountException.class, () ->
            new Transaction("TXN004", 0.0, "DEPOSIT")
        );
    }

    @Test
    void testToStringContainsTransactionId() throws InvalidAmountException {
        Transaction transaction = new Transaction("TXN005", 200.0, "TRANSFER");
        assertTrue(transaction.toString().contains("TXN005"));
    }

    @Test
    void testToStringContainsAmount() throws InvalidAmountException {
        Transaction transaction = new Transaction("TXN006", 300.0, "WITHDRAW");
        assertTrue(transaction.toString().contains("300.0"));
    }

    @Test
    void testToStringContainsType() throws InvalidAmountException {
        Transaction transaction = new Transaction("TXN007", 400.0, "DEPOSIT");
        assertTrue(transaction.toString().contains("DEPOSIT"));
    }
}