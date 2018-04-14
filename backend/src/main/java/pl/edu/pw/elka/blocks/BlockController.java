package pl.edu.pw.elka.blocks;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.elka.blocks.domain.BlockFacade;
import pl.edu.pw.elka.blocks.dto.BlockDetailsDto;

@RestController
class BlockController {

    private final BlockFacade facade;

    public BlockController(BlockFacade facade) {
        this.facade = facade;
    }

    @GetMapping(value = "/ethereum/blocks/current")
    public ResponseEntity<BlockDetailsDto> fetchCurrentBlockDetails() {
        BlockDetailsDto dto = facade.fetchBlockDetails();
        return ResponseEntity.ok(dto);
    }
}
