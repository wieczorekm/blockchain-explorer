package pl.edu.pw.elka.etherscan.validator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import pl.edu.pw.elka.etherscan.BadEthereumAddressException;
import pl.edu.pw.elka.etherscan.EthereumAddressValidator;

public class EthereumAddressValidatorTests {

    private static final String CORRECT_ADDRESS_1 = "0xbff4c784f69c825e21d079ac6aacdf1f8d317ece";
    private static final String CORRECT_ADDRESS_2 = "0x804cb76f481bd3c8190ae48486790cd4996f4542";
    private static final String BAD_SHORT_ADDRESS_1 = "0xbff4c784f69c825e21d079ac6aacdf1f8d317e";
    private static final String BAD_SHORT_ADDRESS_2 = "0x804cb76f481bd3c8190ae48486790cd4996f45";
    private static final String BAD_NO_0X_ADDRESS_1 = "0abff4c784f69c825e21d079ac6aacdf1f8d317ece";
    private static final String BAD_NO_0X_ADDRESS_2 = "9x804cb76f481bd3c8190ae48486790cd4996f4542";
    private static final String BAD_BIG_LETTER_ADDRESS_1 = "0xbff4c784f69c825e21d079ac6Aacdf1f8d317ece";
    private static final String BAD_BIG_LETTER_ADDRESS_2 = "0x804cb76f481bd3c8190aE48486790cd4996f4542";

    private EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();

    @Test
    public void testCorrectAddress1() {
        ethereumAddressValidator.validateAddress(CORRECT_ADDRESS_1);
    }

    @Test
    public void testCorrectAddress2() {
        ethereumAddressValidator.validateAddress(CORRECT_ADDRESS_2);
    }

    @Test(expected=BadEthereumAddressException.class)
    public void testTooShortAddress1() {
        ethereumAddressValidator.validateAddress(BAD_SHORT_ADDRESS_1);
    }

    @Test(expected=BadEthereumAddressException.class)
    public void testTooShortAddress2() {
        ethereumAddressValidator.validateAddress(BAD_SHORT_ADDRESS_2);
    }

    @Test(expected=BadEthereumAddressException.class)
    public void testAddressWithout0x1() {
        ethereumAddressValidator.validateAddress(BAD_NO_0X_ADDRESS_1);
    }

    @Test(expected=BadEthereumAddressException.class)
    public void testAddressWithout0x2() {
        ethereumAddressValidator.validateAddress(BAD_NO_0X_ADDRESS_2);
    }

    @Test(expected=BadEthereumAddressException.class)
    public void testAddressWithBigLetter1() {
        ethereumAddressValidator.validateAddress(BAD_BIG_LETTER_ADDRESS_1);
    }

    @Test(expected=BadEthereumAddressException.class)
    public void testAddressWithBigLetter2() {
        ethereumAddressValidator.validateAddress(BAD_BIG_LETTER_ADDRESS_2);
    }

}
