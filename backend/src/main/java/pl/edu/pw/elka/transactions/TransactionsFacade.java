package pl.edu.pw.elka.transactions;

import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionDto;
import pl.edu.pw.elka.minedBlocks.MinedBlocksFacade;
import pl.edu.pw.elka.transactions.dtos.TransactionDto;
import pl.edu.pw.elka.transactions.dtos.TransactionsDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionsFacade {

    private static final BigDecimal WEIS_IN_ETHER = BigDecimal.TEN.pow(18);
    private final EtherscanFacade etherscanFacade;
    private final MinedBlocksFacade minedBlocksFacade;

    public TransactionsFacade(EtherscanFacade etherscanFacade, MinedBlocksFacade minedBlocksFacade) {
        this.etherscanFacade = etherscanFacade;
        this.minedBlocksFacade = minedBlocksFacade;
    }

    public TransactionsDto getTransactionsForAddress(String address) {
        final List<EtherscanTransactionDto> transactions = etherscanFacade.getTransactionsForAddress(address).getTransactions();

        final Stream<EtherscanTransactionDto> inStream = transactions.stream()
                .filter(tx -> tx.getTo().equals(address));

        final Stream<EtherscanTransactionDto> outStream = transactions.stream()
                .filter(tx -> tx.getFrom().equals(address));

        return new TransactionsDto(address,
                collectToResultSet(inStream, EtherscanTransactionDto::getFrom),
                collectToResultSet(outStream, EtherscanTransactionDto::getTo),
                minedBlocksFacade.getMinedBlocksRewardForAddress(address));
    }

    private Set<TransactionDto> collectToResultSet(Stream<EtherscanTransactionDto> dtos, Function<EtherscanTransactionDto, String> getFunction) {
        return dtos
                .collect(getCollector(getFunction))
                .entrySet()
                .stream()
                .map(entry -> new TransactionDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    private Collector<EtherscanTransactionDto, ?, Map<String, BigDecimal>> getCollector(Function<EtherscanTransactionDto, String> getFunction) {
        return Collectors.groupingBy(getFunction,
                Collectors.reducing(BigDecimal.ZERO, tx -> mapWeiToEther(tx.getValue()), BigDecimal::add));
    }

    private BigDecimal mapWeiToEther(String value) {
        return new BigDecimal(value).divide(WEIS_IN_ETHER);
    }

}
