package pl.edu.pw.elka.etherscan;

public class BadEthereumAddressException extends RuntimeException {

    public BadEthereumAddressException(String reason) {
        super(reason);
    }
}
