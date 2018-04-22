package pl.edu.pw.elka.transactions.domain;

import java.util.ArrayList;

class MockedTransactionsConnector implements TransactionsConnector {

    @Override
    public TransactionLists fetchTransactionLists(String address) {
        ArrayList<TransactionDetails> arrayIn = new ArrayList<>();
        ArrayList<TransactionDetails> arrayOut = new ArrayList<>();

        arrayIn.add(new TransactionDetails("0.1bc48h", "0x12345"));
        arrayIn.add(new TransactionDetails("0.abf2h", "0x4c5"));
        arrayIn.add(new TransactionDetails("0.84448h", "0x10ab0"));

        arrayOut.add(new TransactionDetails("0.1f18h", "0x545"));
        arrayOut.add(new TransactionDetails("0.f94h", "0xab"));

        return TransactionLists.builder()
                .incomeTransactionsList(arrayIn)
                .outcomeTransactionsList(arrayOut)
                .build();
    }
}
