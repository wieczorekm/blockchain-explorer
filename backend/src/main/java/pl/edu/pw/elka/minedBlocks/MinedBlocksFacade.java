package pl.edu.pw.elka.minedBlocks;

import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

import java.math.BigDecimal;
import java.util.Optional;

public class MinedBlocksFacade {

    private  final EtherscanFacade etherscanFacade;
    private static final BigDecimal WEIS_IN_ETHER = BigDecimal.TEN.pow(18);

    public MinedBlocksFacade(EtherscanFacade etherscanFacade) {
        this.etherscanFacade = etherscanFacade;
    }

    public BigDecimal getMinedBlocksRewardForAddress(String address){
        Optional<MinedBlocksDto> blocksOpt = Optional.ofNullable(
                getMinedBlocksForAddress(address));

        return blocksOpt.isPresent() ?
                blocksOpt.get()
                        .getMinedBlocksRewards()
                        .stream()
                        .map(block -> mapWeiToEther(block.getBlockReward()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                : BigDecimal.ZERO;
    }

    public MinedBlocksDto getMinedBlocksForAddress(String address){
        return etherscanFacade.getMinedBlocksForAddress(address);
    }

    private BigDecimal mapWeiToEther(String value) {
        return new BigDecimal(value).divide(WEIS_IN_ETHER);
    }

}
