package pl.edu.pw.elka.transactions.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.pw.elka.transactions.domain.TransactionDetails;

import java.util.ArrayList;

@Getter
@RequiredArgsConstructor
@Builder
public class TransactionListsDto {

    private ArrayList<TransactionDetails> incomeTransactionsList;
    private ArrayList<TransactionDetails> outcomeTransactionsList;

    public TransactionListsDto(ArrayList<TransactionDetails> arrayIn, ArrayList<TransactionDetails> arrayOut) {
        incomeTransactionsList = arrayIn;
        outcomeTransactionsList = arrayOut;
    }
}
