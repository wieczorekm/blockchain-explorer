package pl.edu.pw.elka.blocks.domain;

class MockedBlockchainConnector implements BlockchainConnector {

    @Override
    public BlockDetails fetchCurrentBlockDetails() {
        return BlockDetails.builder()
                .height(12345)
                .hash("123")
                .parentHash("333")
                .build();
    }
}
