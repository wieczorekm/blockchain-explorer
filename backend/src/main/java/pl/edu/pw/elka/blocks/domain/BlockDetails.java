package pl.edu.pw.elka.blocks.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.pw.elka.blocks.dto.BlockDetailsDto;

@Getter
@RequiredArgsConstructor
@Builder
class BlockDetails {

    private final int height;
    private final String hash;
    private final String parentHash;

    public BlockDetailsDto toDto() {
        return BlockDetailsDto.builder()
                .height(height)
                .hash(hash)
                .parentHash(parentHash)
                .build();
    }
}
