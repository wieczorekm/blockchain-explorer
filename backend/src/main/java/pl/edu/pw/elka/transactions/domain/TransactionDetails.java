package pl.edu.pw.elka.transactions.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransactionDetails {
    private final String address;
    private final String amount;
}
