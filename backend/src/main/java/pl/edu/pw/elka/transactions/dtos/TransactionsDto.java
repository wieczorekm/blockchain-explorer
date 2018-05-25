package pl.edu.pw.elka.transactions.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class TransactionsDto {

    private final String address;
    private final Set<TransactionDto> inTransactions;
    private final Set<TransactionDto> outTransactions;
    private final BigDecimal minedBlocksReward;

}
