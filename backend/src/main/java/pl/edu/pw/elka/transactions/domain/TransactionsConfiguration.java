package pl.edu.pw.elka.transactions.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TransactionsConfiguration {

    TransactionsFacade transactionsFacade() {
        TransactionsConnector transactionsConnector = new MockedTransactionsConnector();
        return transactionsFacade(transactionsConnector);
    }

    @Bean
    public TransactionsFacade transactionsFacade(TransactionsConnector transactionsConnector) {
        return new TransactionsFacade(transactionsConnector);
    }

    @Bean
    public TransactionsConnector transactionsConnector() {
        return new EtherscanTransactionsConnector();
    }
}
