package util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;

public class AmountValidatorTest {



    @Test
    void testRequirePositiveException() {

        assertThrows(InvalidAmountException.class, () -> AmountValidator.requirePositive(-50, "Deposit"));
        
}


@Test
void testRequirePositive() {

    assertDoesNotThrow(() -> AmountValidator.requirePositive(100, "Deposit"));

}

@Test
void testSufficientException() {

    assertThrows(InsufficientBalanceException.class, () -> AmountValidator.requireSufficient(100, 150));
}


@Test
void testSufficient() {

    assertDoesNotThrow(() -> AmountValidator.requireSufficient(100, 50));


}


}