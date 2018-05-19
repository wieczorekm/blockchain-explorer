package pl.edu.pw.elka.transactions;

import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionDto;
import pl.edu.pw.elka.transactions.dtos.TransactionDto;
import pl.edu.pw.elka.transactions.dtos.TransactionsDto;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionsFacade {

    private static final BigDecimal WEIS_IN_ETHER = BigDecimal.TEN.pow(18);
    private final EtherscanFacade etherscanFacade;

    public TransactionsFacade(EtherscanFacade etherscanFacade) {
        this.etherscanFacade = etherscanFacade;
    }

    public TransactionsDto getTransactionsForAddress(String address) {
        final List<EtherscanTransactionDto> transactions = etherscanFacade.getTransactionsForAddress(address).getTransactions();
        final Set<TransactionDto> inTransactions = transactions.stream()
                .filter(tx -> tx.getTo().equals(address))
                .collect(Collectors.groupingBy(
                        EtherscanTransactionDto::getFrom,
                        Collectors.reducing(BigDecimal.ZERO, tx -> mapWeiToEther(tx.getValue()), BigDecimal::add)))
                .entrySet()
                .stream()
                .map(entry -> new TransactionDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());

        final Set<TransactionDto> outTransactions = transactions.stream()
                .filter(tx -> tx.getFrom().equals(address))
                .map(tx -> new TransactionDto(tx.getTo(), mapWeiToEther(tx.getValue())))
                .collect(Collectors.toSet());


        BigDecimal minedBlocksReward;
        try {
            minedBlocksReward = etherscanFacade.getMinedBlocksForAddress(address)
                    .getMinedBlocksRewards()
                    .stream()
                    .map(block -> mapWeiToEther(block.getBlockReward()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (NullPointerException e) {
            minedBlocksReward = BigDecimal.ZERO;
        }

        return new TransactionsDto(address, inTransactions, outTransactions, minedBlocksReward);
    }

    private BigDecimal mapWeiToEther(String value) {
        return new BigDecimal(value).divide(WEIS_IN_ETHER);
    }

}
