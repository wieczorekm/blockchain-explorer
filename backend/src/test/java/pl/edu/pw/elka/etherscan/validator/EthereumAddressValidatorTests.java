package pl.edu.pw.elka.etherscan.validator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import pl.edu.pw.elka.etherscan.BadEthereumAddressException;
import pl.edu.pw.elka.etherscan.EthereumAddressValidator;

public class EthereumAddressValidatorTests {

    private static final String ADDRESS_1 = "0xbff4c784f69c825e21d079ac6aacdf1f8d317ece";   // OK
    private static final String ADDRESS_2 = "0x804cb76f481bd3c8190ae48486790cd4996f4542";   // OK
    private static final String ADDRESS_3 = "0xbff4c784f69c825e21d079ac6aacdf1f8d317e";     // TOO SHORT
    private static final String ADDRESS_4 = "0x804cb76f481bd3c8190ae48486790cd4996f45";     // TOO SHORT
    private static final String ADDRESS_5 = "0abff4c784f69c825e21d079ac6aacdf1f8d317ece";   // NO 0x
    private static final String ADDRESS_6 = "9x804cb76f481bd3c8190ae48486790cd4996f4542";   // NO 0x
    private static final String ADDRESS_7 = "0xbff4c784f69c825e21d079ac6Aacdf1f8d317ece";   // BIG LETTER
    private static final String ADDRESS_8 = "0x804cb76f481bd3c8190aE48486790cd4996f4542";   // BIG LETTER

    private Exception exception;
    private BadEthereumAddressException badEthereumAddressException = new BadEthereumAddressException("Wrong Ethereum address");

    @Test
    public void testCorrectAddress1() {
        try {
            EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();
            ethereumAddressValidator.validateAddress(ADDRESS_1);
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    public void testCorrectAddress2() {
        try {
            EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();
            ethereumAddressValidator.validateAddress(ADDRESS_2);
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    public void testTooShortAddress1() {
        try {
            EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();
            ethereumAddressValidator.validateAddress(ADDRESS_3);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(badEthereumAddressException.getMessage(), exception.getMessage());
    }

    @Test
    public void testTooShortAddress2() {
        try {
            EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();
            ethereumAddressValidator.validateAddress(ADDRESS_4);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(badEthereumAddressException.getMessage(), exception.getMessage());
    }

    @Test
    public void testAddressWithout0x1() {
        try {
            EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();
            ethereumAddressValidator.validateAddress(ADDRESS_5);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(badEthereumAddressException.getMessage(), exception.getMessage());
    }

    @Test
    public void testAddressWithout0x2() {
        try {
            EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();
            ethereumAddressValidator.validateAddress(ADDRESS_6);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(badEthereumAddressException.getMessage(), exception.getMessage());
    }

    @Test
    public void testAddressWithBigLetter1() {
        try {
            EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();
            ethereumAddressValidator.validateAddress(ADDRESS_7);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(badEthereumAddressException.getMessage(), exception.getMessage());
    }

    @Test
    public void testAddressWithBigLetter2() {
        try {
            EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();
            ethereumAddressValidator.validateAddress(ADDRESS_8);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(badEthereumAddressException.getMessage(), exception.getMessage());
    }

}
