package pl.edu.pw.elka.minedBlocks;

import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

import java.math.BigDecimal;

public class MinedBlocksFacade {

    private static final BigDecimal WEIS_IN_ETHER = BigDecimal.TEN.pow(18);
    private  final EtherscanFacade etherscanFacade;

    public MinedBlocksFacade(EtherscanFacade etherscanFacade) {
        this.etherscanFacade = etherscanFacade;
    }

    public MinedBlocksDto getMinedBlocksForAddress(String address){
        return etherscanFacade.getMinedBlocksRewardForAddress(address);
    }

    private BigDecimal mapWeiToEther(String value) {
        return new BigDecimal(value).divide(WEIS_IN_ETHER);
    }
}
