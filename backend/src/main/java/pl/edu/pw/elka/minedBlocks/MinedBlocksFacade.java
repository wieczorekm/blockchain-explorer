package pl.edu.pw.elka.minedBlocks;

import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

public class MinedBlocksFacade {

    private  final EtherscanFacade etherscanFacade;

    public MinedBlocksFacade(EtherscanFacade etherscanFacade) {
        this.etherscanFacade = etherscanFacade;
    }

    public MinedBlocksDto getMinedBlocksForAddress(String address){
        return etherscanFacade.getMinedBlocksForAddress(address);
    }

}
