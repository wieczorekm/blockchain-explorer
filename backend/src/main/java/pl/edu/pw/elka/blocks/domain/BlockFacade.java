package pl.edu.pw.elka.blocks.domain;

import pl.edu.pw.elka.blocks.dto.BlockDetailsDto;

public class BlockFacade {

    private final BlockchainConnector blockchainConnector;

    public BlockFacade(BlockchainConnector blockchainConnector) {
        this.blockchainConnector = blockchainConnector;
    }

    public BlockDetailsDto fetchBlockDetails(String blockNumber) {
        BlockDetails blockDetails = blockchainConnector.fetchCurrentBlockDetails(blockNumber);
        return blockDetails.toDto();
    }
}
