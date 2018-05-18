package pl.edu.pw.elka.etherscan;

import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;

public class EtherscanFacade {

    private final EtherscanConnector etherscanConnector;

    public EtherscanFacade(EtherscanConnector etherscanConnector) {
        this.etherscanConnector = etherscanConnector;
    }

    public EtherscanTransactionsDto getTransactionsForAddress(String address) {
        EthereumAddressValidator ethereumAddressValidator = new EthereumAddressValidator();
            if (ethereumAddressValidator.checkAddres(address))
                return etherscanConnector.getTransactions(address);
            else
                throw new BadEthereumAddressException("Wrong Ethereum address");
    }
}
