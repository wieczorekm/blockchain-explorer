package pl.edu.pw.elka.transactions.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class TransactionDto {

    private final String address;
    private final BigDecimal value;

}
