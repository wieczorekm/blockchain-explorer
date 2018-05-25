package pl.edu.pw.elka.minedBlocks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pw.elka.etherscan.EtherscanFacade;

@Configuration
public class MinedBlocksConfiguration {

    @Bean
    MinedBlocksFacade minedBlocksFacade(EtherscanFacade etherscanFacade){
        return new MinedBlocksFacade(etherscanFacade);
    }
}
