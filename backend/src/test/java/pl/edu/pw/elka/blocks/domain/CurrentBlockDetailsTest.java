package pl.edu.pw.elka.blocks.domain;

import org.junit.Test;
import pl.edu.pw.elka.blocks.dto.BlockDetailsDto;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CurrentBlockDetailsTest {

    private BlockFacade facade = new BlockConfiguration().blockFacade();

    @Test
    public void canFetchCurrentBlockDetails() {
        BlockDetailsDto blockDetailsDto = facade.fetchBlockDetails();
        assertThat(blockDetailsDto.getHeight()).isEqualTo(12345);
        assertThat(blockDetailsDto.getHash()).isEqualTo("123");
        assertThat(blockDetailsDto.getParentHash()).isEqualTo("333");
    }
}
