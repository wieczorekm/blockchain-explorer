package pl.edu.pw.elka.minedBlocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlockDto;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class MinedBlocksTests {

    private static final BigDecimal WEIS_IN_ETHER = BigDecimal.TEN.pow(18);
    private static final String MY_ADDRESS = "0xe103f7baef7a048cb22a4d566a37f72f762df229";

    private MinedBlocksFacade minedBlocksFacade;
    private EtherscanFacade etherscanFacade;

    @Before
    public void before() {
        etherscanFacade = Mockito.mock(EtherscanFacade.class);
        minedBlocksFacade = new MinedBlocksFacade(etherscanFacade);
    }

    @Test
    public void shouldReturnNoBlocksAndValueZeroEther() {
        mockEmptyBlocks();
        final MinedBlocksDto blocks = minedBlocksFacade.getMinedBlocksForAddress(MY_ADDRESS);
        assertThat(blocks.getMinedBlocksRewards()).hasSize(0);
        final BigDecimal reward = minedBlocksFacade.getMinedBlocksRewardForAddress(MY_ADDRESS);
        assertThat(reward).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void shouldReturnOneBlockAndValue1Ether() {
        mockOneBlockWithValue(WEIS_IN_ETHER.toString());
        final MinedBlocksDto blocks = minedBlocksFacade.getMinedBlocksForAddress(MY_ADDRESS);
        assertThat(blocks.getMinedBlocksRewards()).hasSize(1);
        final BigDecimal reward = minedBlocksFacade.getMinedBlocksRewardForAddress(MY_ADDRESS);
        assertThat(reward.toString()).isEqualTo("1");
    }

    @Test
    public void shouldReturnTwoBlocksAndValue3Ether() {
        mockTwoBlocksWithValues(WEIS_IN_ETHER, WEIS_IN_ETHER.add(WEIS_IN_ETHER));
        final MinedBlocksDto blocks = minedBlocksFacade.getMinedBlocksForAddress(MY_ADDRESS);
        assertThat(blocks.getMinedBlocksRewards()).hasSize(2);
        final BigDecimal reward = minedBlocksFacade.getMinedBlocksRewardForAddress(MY_ADDRESS);
        assertThat(reward.toString()).isEqualTo("3");
    }

    private void mockOneBlockWithValue(String value) {
        final MinedBlockDto block = new MinedBlockDto(value);
        final List<MinedBlockDto> blocks = Collections.singletonList(block);
        Mockito.when(etherscanFacade.getMinedBlocksForAddress(any()))
                .thenReturn(new MinedBlocksDto(blocks));
    }

    private void mockTwoBlocksWithValues(BigDecimal val1, BigDecimal val2) {
        final MinedBlockDto[] blockDtos = {
                new MinedBlockDto(val1.toString()),
                new MinedBlockDto(val2.toString())
        };
        Mockito.when(etherscanFacade.getMinedBlocksForAddress(any()))
                .thenReturn(new MinedBlocksDto(Arrays.asList(blockDtos)));
    }

    private void mockEmptyBlocks() {
        Mockito.when(etherscanFacade.getMinedBlocksForAddress(any()))
                .thenReturn(new MinedBlocksDto(Collections.emptyList()));
    }
}
