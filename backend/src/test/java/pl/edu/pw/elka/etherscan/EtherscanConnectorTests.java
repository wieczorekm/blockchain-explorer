package pl.edu.pw.elka.etherscan;

import org.junit.Test;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class EtherscanConnectorTests {

    private EtherscanFacade facade = new EtherscanFacade(new MockEtherscanConnector());

    @Test
    public void returnsTransactions() {
        EtherscanTransactionsDto dto = facade.getTransactionsForAddress("");

        assertThat(dto.getTransactions()).hasSize(2);

    }
}
