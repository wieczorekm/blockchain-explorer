package pl.edu.pw.elka.transactions.domain;

import pl.edu.pw.elka.transactions.dto.TransactionListsDto;

public class TransactionsFacade {

    private final TransactionsConnector transactionsConnector;

    public TransactionsFacade(TransactionsConnector transactionsConnector) {
        this.transactionsConnector = transactionsConnector;
    }

    public TransactionListsDto fetchTransactionLists(String address) {
        TransactionLists transactionLists = transactionsConnector.fetchTransactionLists(address);
        return transactionLists.toDto();
    }
}
