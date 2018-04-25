package pl.edu.pw.elka.transactions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionDto;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;
import pl.edu.pw.elka.transactions.dtos.TransactionsDto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class TransactionsTest {

    private static final String ADDRESS_1 = "0xbff4c784f69c825e21d079ac6aacdf1f8d317ece";
    private static final String ADDRESS_2 = "0x804cb76f481bd3c8190ae48486790cd4996f4542";
    private static final String MY_ADDRESS = "0xe103f7baef7a048cb22a4d566a37f72f762df229";

    private TransactionsFacade transactionsFacade;

    @Before
    public void before() {
        EtherscanFacade etherscanFacade = Mockito.mock(EtherscanFacade.class);
        Mockito.when(etherscanFacade.getTransactionsForAddress(any())).thenReturn(sampleEtherscanTransactionsDto());
        transactionsFacade = new TransactionsFacade(etherscanFacade);
    }

    @Test
    public void returnsTransactions() {
        TransactionsDto dto = transactionsFacade.getTransactionsForAddress(MY_ADDRESS);
        assertThat(dto.getOutTransactions()).hasSize(0);
        assertThat(dto.getInTransactions()).hasSize(2);
        assertThat(dto.getInTransactions()).containsExactlyInAnyOrder(ADDRESS_1, ADDRESS_2);
    }

    private EtherscanTransactionsDto sampleEtherscanTransactionsDto() {
        List<EtherscanTransactionDto> etherscanTransactionDtos = Arrays.asList(
                new EtherscanTransactionDto(ADDRESS_1, MY_ADDRESS),
                new EtherscanTransactionDto(ADDRESS_2, MY_ADDRESS)
        );
        return new EtherscanTransactionsDto(new HashSet<>(etherscanTransactionDtos));
    }
}
