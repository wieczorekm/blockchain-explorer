package pl.edu.pw.elka.minedBlocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlockDto;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class MinedBlocksTests {

    private static final String MY_ADDRESS = "0xe103f7baef7a048cb22a4d566a37f72f762df229";

    private MinedBlocksFacade minedBlocksFacade;
    private EtherscanFacade etherscanFacade;

    @Before
    public void before() {
        etherscanFacade = Mockito.mock(EtherscanFacade.class);
        minedBlocksFacade = new MinedBlocksFacade(etherscanFacade);
    }

    @Test
    public void shouldReturnNoBlocks() {
        mockEmptyBlocks();
        final MinedBlocksDto blocks = minedBlocksFacade.getMinedBlocksForAddress(MY_ADDRESS);
        assertThat(blocks.getMinedBlocksRewards()).hasSize(0);
    }

    @Test
    public void shouldReturnOneBlock() {
        mockOneBlock();
        final MinedBlocksDto blocks = minedBlocksFacade.getMinedBlocksForAddress(MY_ADDRESS);
        assertThat(blocks.getMinedBlocksRewards()).hasSize(1);
    }

    @Test
    public void shouldReturnTwoBlocks() {
        mockTwoBlocks();
        final MinedBlocksDto blocks = minedBlocksFacade.getMinedBlocksForAddress(MY_ADDRESS);
        assertThat(blocks.getMinedBlocksRewards()).hasSize(2);
    }

    @Test
    public void shouldReturnOneBlockWith1Ether() {
        mockOneBlockWithValue("1");
        final MinedBlocksDto blocks = minedBlocksFacade.getMinedBlocksForAddress(MY_ADDRESS);
        MinedBlockDto block = blocks.getMinedBlocksRewards().stream().findAny().get();
        assertThat(block.getBlockReward()).isEqualTo("1");
    }

    private void mockOneBlockWithValue(String value) {
        final MinedBlockDto block = new MinedBlockDto(value);
        final List<MinedBlockDto> blocks = Collections.singletonList(block);
        Mockito.when(etherscanFacade.getMinedBlocksForAddress(any()))
                .thenReturn(new MinedBlocksDto(blocks));
    }

    private void mockTwoBlocks() {
        final MinedBlockDto[] blockDtos = {
                new MinedBlockDto(null),
                new MinedBlockDto(null)
        };
        Mockito.when(etherscanFacade.getMinedBlocksForAddress(any()))
                .thenReturn(new MinedBlocksDto(Arrays.asList(blockDtos)));
    }

    private void mockOneBlock() {
        final MinedBlockDto block = new MinedBlockDto(null);
        final List<MinedBlockDto> blocks = Collections.singletonList(block);
        Mockito.when(etherscanFacade.getMinedBlocksForAddress(any()))
                .thenReturn(new MinedBlocksDto(blocks));
    }

    private void mockEmptyBlocks() {
        Mockito.when(etherscanFacade.getMinedBlocksForAddress(any()))
                .thenReturn(new MinedBlocksDto(Collections.emptyList()));
    }
}
