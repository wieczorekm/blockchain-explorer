package pl.edu.pw.elka.etherscan;

import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

public class EtherscanFacade {

    private final EtherscanConnector etherscanConnector;

    public EtherscanFacade(EtherscanConnector etherscanConnector) {
        this.etherscanConnector = etherscanConnector;
    }

    public EtherscanTransactionsDto getTransactionsForAddress(String address) {
        // TODO address validation
        return etherscanConnector.getTransactions(address);
    }

    public MinedBlocksDto getMinedBlocksRewardForAddress(String address){
        //TODO address validation
        return etherscanConnector.getMinedBlocks(address);
    }
}
