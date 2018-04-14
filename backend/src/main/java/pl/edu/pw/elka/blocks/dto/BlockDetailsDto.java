package pl.edu.pw.elka.blocks.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class BlockDetailsDto {

    private final int height;
    private final String hash;
    private final String parentHash;
}
