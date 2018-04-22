package pl.edu.pw.elka.blocks.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BlockConfiguration {

    BlockFacade blockFacade() {
        BlockchainConnector blockchainConnector = new MockedBlockchainConnector();
        return blockFacade(blockchainConnector);
    }

    @Bean
    public BlockFacade blockFacade(BlockchainConnector blockchainConnector) {
        return new BlockFacade(blockchainConnector);
    }

    @Bean
    public BlockchainConnector blockchainConnector() {
        return new EtherscanBlockchainConnector();
    }

}
