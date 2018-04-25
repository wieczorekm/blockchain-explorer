package pl.edu.pw.elka.etherscan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EtherscanConfiguration {

    @Bean
    EtherscanFacade etherscanFacade() {
        return new EtherscanFacade(new RealEtherscanConnector());
    }
}
