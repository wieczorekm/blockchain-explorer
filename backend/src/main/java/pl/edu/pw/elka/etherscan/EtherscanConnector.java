package pl.edu.pw.elka.etherscan;

import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

interface EtherscanConnector {

    EtherscanTransactionsDto getTransactions(String address);
    MinedBlocksDto getMinedBlocks(String address);
}
