package pl.edu.pw.elka.transactions.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.pw.elka.transactions.dto.TransactionListsDto;

import java.util.ArrayList;

@Getter
@RequiredArgsConstructor
@Builder
class TransactionLists {

    private ArrayList<TransactionDetails> incomeTransactionsList;
    private ArrayList<TransactionDetails> outcomeTransactionsList;

    public TransactionLists(ArrayList<TransactionDetails> arrayIn, ArrayList<TransactionDetails> arrayOut) {
        incomeTransactionsList = arrayIn;
        outcomeTransactionsList = arrayOut;
    }

    public TransactionListsDto toDto() {
        return TransactionListsDto.builder()
                .incomeTransactionsList(incomeTransactionsList)
                .outcomeTransactionsList(outcomeTransactionsList)
                .build();
    }
}
