package pl.edu.pw.elka.etherscan;

public class BadEthereumAddressException extends RuntimeException {


    public BadEthereumAddressException(String wrong_ethereum_address) {
        super(wrong_ethereum_address);
    }
}
