package pl.edu.pw.elka.transactions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pw.elka.etherscan.EtherscanFacade;

@Configuration
class TransactionsConfiguration {

    @Bean
    TransactionsFacade transactionsFacade(EtherscanFacade etherscanFacade) {
        return new TransactionsFacade(etherscanFacade);
    }
}
