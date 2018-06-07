package pl.edu.pw.elka.transactions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PAYLOAD_TOO_LARGE)
public class TooManyElementsException extends RuntimeException {

    public TooManyElementsException() {
        super("There are more than 50 transactions for given range, please make the range smaller.");
    }
}
