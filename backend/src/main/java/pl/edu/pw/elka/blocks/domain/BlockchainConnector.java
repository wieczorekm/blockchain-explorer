package pl.edu.pw.elka.blocks.domain;

interface BlockchainConnector {
    BlockDetails fetchCurrentBlockDetails(String blockNumber);
}
