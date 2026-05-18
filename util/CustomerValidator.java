

package util;

import exception.InvalidEmailException;
import exception.InvalidPhoneNumberException;

public class CustomerValidator {
    private static final int    PHONE_LENGTH   = 10;
    private static final String PHONE_PATTERN  = "\\d{" + PHONE_LENGTH + "}";

    private CustomerValidator() {} // utility class — no instantiation

    public static void validateEmail(String email) throws InvalidEmailException {
        if (email == null || !email.contains("@")) {
            throw new InvalidEmailException(email);
        }
    }

    public static void validatePhoneNumber(String phone) throws InvalidPhoneNumberException {
        if (phone == null || !phone.matches(PHONE_PATTERN)) {
            throw new InvalidPhoneNumberException(phone);
        }
    }
}
