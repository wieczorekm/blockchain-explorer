package pl.edu.pw.elka.transactions.domain;

interface TransactionsConnector {
    TransactionLists fetchTransactionLists(String address);
}
