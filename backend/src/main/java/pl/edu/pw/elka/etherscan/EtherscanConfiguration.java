package pl.edu.pw.elka.etherscan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EtherscanConfiguration {

    @Bean
    EtherscanFacade etherscanFacade() {
        final RealEtherscanConnector etherscanConnector = new RealEtherscanConnector();
        final EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();
        return new EtherscanFacade(etherscanConnector, ethereumAddressValidator);
    }
}
