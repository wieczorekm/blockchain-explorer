package pl.edu.pw.elka.transactions.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class TransactionsDto {

    private final Set<String> inTransactions;
    private final Set<String> outTransactions;

}
