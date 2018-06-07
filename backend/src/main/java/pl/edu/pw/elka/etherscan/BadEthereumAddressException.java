package pl.edu.pw.elka.etherscan;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadEthereumAddressException extends RuntimeException {

    BadEthereumAddressException(String reason) {
        super(reason);
    }
}
