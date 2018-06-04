package pl.edu.pw.elka.transactions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionDto;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;
import pl.edu.pw.elka.minedBlocks.MinedBlocksFacade;
import pl.edu.pw.elka.transactions.dtos.TransactionDto;
import pl.edu.pw.elka.transactions.dtos.TransactionsDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class TransactionsTest {

    private static final String ADDRESS_1 = "0xbff4c784f69c825e21d079ac6aacdf1f8d317ece";
    private static final String ADDRESS_2 = "0x804cb76f481bd3c8190ae48486790cd4996f4542";
    private static final String MY_ADDRESS = "0xe103f7baef7a048cb22a4d566a37f72f762df229";
    private static final String BLOCK_NUMBER = "12345";
    private static final BigDecimal WEIS_IN_ETHER = BigDecimal.TEN.pow(18);
    private static final String ONE = "1";
    private static final String SAMPLE_START_BLOCK = "5";
    private static final String SAMPLE_END_BLOCK = "10";

    private TransactionsFacade transactionsFacade;
    private EtherscanFacade etherscanFacade;
    private MinedBlocksFacade minedBlocksFacade;

    @Before
    public void before() {
        etherscanFacade = Mockito.mock(EtherscanFacade.class);
        minedBlocksFacade = Mockito.mock(MinedBlocksFacade.class);
        transactionsFacade = new TransactionsFacade(etherscanFacade, minedBlocksFacade);
    }

    @Test
    public void shouldReturnAddress() {
        mockEmptyTransactions();
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        assertThat(txs.getAddress()).isEqualTo(MY_ADDRESS);
    }

    @Test
    public void shouldReturnNoTransactions() {
        mockEmptyTransactions();
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        assertThat(txs.getInTransactions()).hasSize(0);
        assertThat(txs.getOutTransactions()).hasSize(0);
    }

    @Test
    public void shouldReturnOneInTransaction() {
        mockOneInTransaction();
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        assertThat(txs.getInTransactions()).hasSize(1);
        assertThat(txs.getOutTransactions()).hasSize(0);
        TransactionDto dto = txs.getInTransactions().stream().findAny().get();
        assertThat(dto.getAddress()).isEqualTo(ADDRESS_1);
    }

    @Test
    public void shouldReturnTwoInTransactions() {
        mockTwoInTransactions();
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        assertThat(txs.getInTransactions()).hasSize(2);
        assertThat(txs.getOutTransactions()).hasSize(0);
        assertThat(txs.getInTransactions().stream()
                .map(TransactionDto::getAddress)
                .collect(Collectors.toSet())
        ).containsExactlyInAnyOrder(ADDRESS_1, ADDRESS_2);
    }

    @Test
    public void shouldReturnOneOutTransaction() {
        mockOneOutTransaction();
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        assertThat(txs.getInTransactions()).hasSize(0);
        assertThat(txs.getOutTransactions()).hasSize(1);
        TransactionDto dto = txs.getOutTransactions().stream().findAny().get();
        assertThat(dto.getAddress()).isEqualTo(ADDRESS_1);
    }

    @Test
    public void shouldReturnValueForInTransaction1Ether() {
        mockOneInTransactionWithValue(WEIS_IN_ETHER);
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        TransactionDto dto = txs.getInTransactions().stream().findAny().get();
        assertThat(dto.getValue()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void shouldReturnValueForInTransaction2Ethers() {
        mockOneInTransactionWithValue(WEIS_IN_ETHER.multiply(new BigDecimal("2")));
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        TransactionDto dto = txs.getInTransactions().stream().findAny().get();
        assertThat(dto.getValue()).isEqualTo(new BigDecimal("2"));
    }

    @Test
    public void shouldReturnValueForOutTransaction1Ether() {
        mockOneOutTransactionWithValue(WEIS_IN_ETHER);
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        TransactionDto dto = txs.getOutTransactions().stream().findAny().get();
        assertThat(dto.getValue()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void shouldReturnValueForOutTransaction2Ethers() {
        mockOneOutTransactionWithValue(WEIS_IN_ETHER.multiply(new BigDecimal("2")));
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        TransactionDto dto = txs.getOutTransactions().stream().findAny().get();
        assertThat(dto.getValue()).isEqualTo(new BigDecimal("2"));
    }

    @Test
    public void shouldAddTransactionInValuesFromTheSameAddress() {
        mockTwoInTransactionsFromTheSameAddress();
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        assertThat(txs.getInTransactions()).hasSize(1);
        TransactionDto dto = txs.getInTransactions().stream().findAny().get();
        assertThat(dto.getValue()).isEqualTo(new BigDecimal("2"));
    }

    @Test
    public void shouldAddTransactionOutValuesFromTheSameAddress() {
        mockTwoOutTransactionsFromTheSameAddress();
        final TransactionsDto txs = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        assertThat(txs.getOutTransactions()).hasSize(1);
        TransactionDto dto = txs.getOutTransactions().stream().findAny().get();
        assertThat(dto.getValue()).isEqualTo(new BigDecimal("2"));
    }

    @Test
    public void shouldReturnReward1Ether() {
        mockOneInTransactionWithValue(WEIS_IN_ETHER);
        mockOneRewardWithValue(WEIS_IN_ETHER);
        final TransactionsDto transactions = transactionsFacade.getTransactionsForAddress(MY_ADDRESS, SAMPLE_START_BLOCK, SAMPLE_END_BLOCK);
        assertThat(transactions.getMinedBlocksReward()).isEqualTo(WEIS_IN_ETHER);
    }

    private void mockOneRewardWithValue(BigDecimal value) {
        Mockito.when(minedBlocksFacade.getMinedBlocksRewardForAddress(any()))
                .thenReturn(value);
    }

    private void mockEmptyTransactions() {
        Mockito.when(etherscanFacade.getTransactionsForAddress(any(), SAMPLE_START_BLOCK, SAMPLE_END_BLOCK)).thenReturn(
                new EtherscanTransactionsDto(Collections.emptyList()));
    }

    private void mockOneInTransaction() {
        final EtherscanTransactionDto tx = new EtherscanTransactionDto(ADDRESS_1, MY_ADDRESS, null, ONE);
        final List<EtherscanTransactionDto> transactions = Collections.singletonList(tx);
        Mockito.when(etherscanFacade.getTransactionsForAddress(any(), SAMPLE_START_BLOCK, SAMPLE_END_BLOCK)).thenReturn(
                new EtherscanTransactionsDto(transactions));
    }

    private void mockOneInTransactionWithValue(BigDecimal value) {
        final EtherscanTransactionDto tx = new EtherscanTransactionDto(ADDRESS_1, MY_ADDRESS, null, value.toString());
        final List<EtherscanTransactionDto> transactions = Collections.singletonList(tx);
        Mockito.when(etherscanFacade.getTransactionsForAddress(any(), SAMPLE_START_BLOCK, SAMPLE_END_BLOCK)).thenReturn(
                new EtherscanTransactionsDto(transactions));
    }

    private void mockTwoInTransactions() {
        final EtherscanTransactionDto[] txs = {
                new EtherscanTransactionDto(ADDRESS_1, MY_ADDRESS, null, ONE),
                new EtherscanTransactionDto(ADDRESS_2, MY_ADDRESS, null, ONE),
        };
        Mockito.when(etherscanFacade.getTransactionsForAddress(any(), SAMPLE_START_BLOCK, SAMPLE_END_BLOCK)).thenReturn(
                new EtherscanTransactionsDto(Arrays.asList(txs)));
    }

    private void mockOneOutTransaction() {
        final EtherscanTransactionDto tx = new EtherscanTransactionDto(MY_ADDRESS, ADDRESS_1, null, ONE);
        final List<EtherscanTransactionDto> transactions = Collections.singletonList(tx);
        Mockito.when(etherscanFacade.getTransactionsForAddress(any(), SAMPLE_START_BLOCK, SAMPLE_END_BLOCK)).thenReturn(
                new EtherscanTransactionsDto(transactions));
    }

    private void mockOneOutTransactionWithValue(BigDecimal value) {
        final EtherscanTransactionDto tx = new EtherscanTransactionDto(MY_ADDRESS, ADDRESS_1, null, value.toString());
        final List<EtherscanTransactionDto> transactions = Collections.singletonList(tx);
        Mockito.when(etherscanFacade.getTransactionsForAddress(any(), SAMPLE_START_BLOCK, SAMPLE_END_BLOCK)).thenReturn(
                new EtherscanTransactionsDto(transactions));
    }

    private void mockTwoInTransactionsFromTheSameAddress() {
        final EtherscanTransactionDto[] txs = {
                new EtherscanTransactionDto(ADDRESS_1, MY_ADDRESS, null, WEIS_IN_ETHER.toString()),
                new EtherscanTransactionDto(ADDRESS_1, MY_ADDRESS, null, WEIS_IN_ETHER.toString()),
        };
        Mockito.when(etherscanFacade.getTransactionsForAddress(any(), SAMPLE_START_BLOCK, SAMPLE_END_BLOCK)).thenReturn(
                new EtherscanTransactionsDto(Arrays.asList(txs)));

    }

    private void mockTwoOutTransactionsFromTheSameAddress() {
        final EtherscanTransactionDto[] txs = {
                new EtherscanTransactionDto(MY_ADDRESS, ADDRESS_1, null, WEIS_IN_ETHER.toString()),
                new EtherscanTransactionDto(MY_ADDRESS, ADDRESS_1, null, WEIS_IN_ETHER.toString()),
        };
        Mockito.when(etherscanFacade.getTransactionsForAddress(any(), SAMPLE_START_BLOCK, SAMPLE_END_BLOCK)).thenReturn(
                new EtherscanTransactionsDto(Arrays.asList(txs)));

    }


}
