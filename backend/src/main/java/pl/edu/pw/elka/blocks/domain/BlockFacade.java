package pl.edu.pw.elka.blocks.domain;

import pl.edu.pw.elka.blocks.dto.BlockDetailsDto;

public class BlockFacade {

    private final BlockchainConnector blockchainConnector;

    public BlockFacade(BlockchainConnector blockchainConnector) {
        this.blockchainConnector = blockchainConnector;
    }

    public BlockDetailsDto fetchBlockDetails(String blockNumber) {
        BlockDetails blockDetails = blockchainConnector.fetchCurrentBlockDetails(blockNumber); //nie przypisujemy wartości polom w klasie BlockDetails
        return blockDetails.toDto(); //wywoluje metode toDto z klasy BlockDetailsDto - to daje dane w JSON?
    }
}
