package util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import exception.InvalidEmailException;
import exception.InvalidPhoneNumberException;

public class CustomerValidatorTest {


   @Test
   void testValidateEmail() {

    assertThrows(InvalidEmailException.class, () -> CustomerValidator.validateEmail("abcgmail.com"));
}

@Test
void testValidateEmailNull() {
    
    assertThrows(InvalidEmailException.class, () -> CustomerValidator.validateEmail(null));
}


@Test
void testValidateEmailValid() {
    
    assertDoesNotThrow(() -> CustomerValidator.validateEmail("abc@gmail.com"));

}

@Test
void testValidatePhoneNumber() {

    assertThrows(InvalidPhoneNumberException.class, () -> CustomerValidator.validatePhoneNumber("12345"));
}

@Test
void testValidatePhoneNumberNull() {
    assertThrows(InvalidPhoneNumberException.class, () -> CustomerValidator.validatePhoneNumber(null));
}


@Test
void testValidatePhoneNumberValid() {
    assertDoesNotThrow(() -> CustomerValidator.validatePhoneNumber("1234567890"));

}




}
