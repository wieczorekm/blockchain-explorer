package pl.edu.pw.elka.transactions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.minedBlocks.MinedBlocksFacade;

@Configuration
class TransactionsConfiguration {

    @Bean
    TransactionsFacade transactionsFacade(EtherscanFacade etherscanFacade, MinedBlocksFacade minedBlocksFacade) {
        return new TransactionsFacade(etherscanFacade, minedBlocksFacade);
    }
}
