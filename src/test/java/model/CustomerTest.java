package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    void testCustomerConstructorAndGetters() {
        Customer customer = new Customer("C001", "Alice", "alice@email.com", "9876543210");
        assertEquals("C001", customer.getCustomerId());
        assertEquals("Alice", customer.getName());
        assertEquals("alice@email.com", customer.getEmail());
        assertEquals("9876543210", customer.getPhoneNumber());
    }

    @Test
    void testSetName() {
        Customer customer = new Customer("C002", "Bob", "bob@email.com", "9876543211");
        customer.setName("Robert");
        assertEquals("Robert", customer.getName());
    }

    @Test
    void testSetEmail() {
        Customer customer = new Customer("C003", "Carol", "carol@email.com", "9876543212");
        customer.setEmail("carol_new@email.com");
        assertEquals("carol_new@email.com", customer.getEmail());
    }

    @Test
    void testSetPhoneNumber() {
        Customer customer = new Customer("C004", "Dave", "dave@email.com", "9876543213");
        customer.setPhoneNumber("9999999999");
        assertEquals("9999999999", customer.getPhoneNumber());
    }

    @Test
    void testToString() {
        Customer customer = new Customer("C005", "Eve", "eve@email.com", "9876543214");
        String expected = "Customer[id=C005, name=Eve, email=eve@email.com, phone=9876543214]";
        assertEquals(expected, customer.toString());
    }

    @Test
    void testCloneNotNull() {
        Customer customer = new Customer("C006", "Frank", "frank@email.com", "9876543215");
        assertDoesNotThrow(() -> {
            Customer cloned = customer.clone();
            assertNotNull(cloned);
        });
    }

    @Test
    void testCloneIsDifferentObject() {
        Customer customer = new Customer("C007", "Grace", "grace@email.com", "9876543216");
        assertDoesNotThrow(() -> {
            Customer cloned = customer.clone();
            assertNotSame(customer, cloned);
        });
    }

    @Test
    void testCloneHasSameValues() {
        Customer customer = new Customer("C008", "Hank", "hank@email.com", "9876543217");
        assertDoesNotThrow(() -> {
            Customer cloned = customer.clone();
            assertEquals(customer.getCustomerId(), cloned.getCustomerId());
            assertEquals(customer.getName(), cloned.getName());
            assertEquals(customer.getEmail(), cloned.getEmail());
            assertEquals(customer.getPhoneNumber(), cloned.getPhoneNumber());
        });
    }
}