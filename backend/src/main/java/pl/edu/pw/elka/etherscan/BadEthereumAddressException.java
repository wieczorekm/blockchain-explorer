package pl.edu.pw.elka.etherscan;

public class BadEthereumAddressException extends RuntimeException {

    BadEthereumAddressException(String reason) {
        super(reason);
    }
}
