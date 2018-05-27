package pl.edu.pw.elka.etherscan;

import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

public class EtherscanFacade {

    private final EtherscanConnector etherscanConnector;
    private final EthereumAddressValidator ethereumAddressValidator;

    EtherscanFacade(EtherscanConnector etherscanConnector, EthereumAddressValidator ethereumAddressValidator) {
        this.etherscanConnector = etherscanConnector;
        this.ethereumAddressValidator = ethereumAddressValidator;
    }

    public EtherscanTransactionsDto getTransactionsForAddress(String address) {
        ethereumAddressValidator.validateAddress(address);
        return etherscanConnector.getTransactions(address);
    }

    public MinedBlocksDto getMinedBlocksForAddress(String address){
        ethereumAddressValidator.validateAddress(address);
        return etherscanConnector.getMinedBlocks(address);
    }
}
