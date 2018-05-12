package pl.edu.pw.elka.transactions;

import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionDto;
import pl.edu.pw.elka.transactions.dtos.TransactionDto;
import pl.edu.pw.elka.transactions.dtos.TransactionsDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionsFacade {

    private static final BigDecimal WEIS_IN_ETHER = BigDecimal.TEN.pow(18);
    private final EtherscanFacade etherscanFacade;

    public TransactionsFacade(EtherscanFacade etherscanFacade) {
        this.etherscanFacade = etherscanFacade;
    }

    public TransactionsDto getTransactionsForAddress(String address) {
        final List<EtherscanTransactionDto> transactions = etherscanFacade.getTransactionsForAddress(address).getTransactions();
        final List<TransactionDto> collect = transactions.stream()
                .filter(tx -> tx.getTo().equals(address))
                .map(tx -> new TransactionDto(tx.getFrom(), mapWeiToEther(tx.getValue())))
                .collect(Collectors.toList());

        final Map<String, TransactionDto> inTransactionsMap = new HashMap<>();
        for (TransactionDto transactionDto : collect) {
            if (inTransactionsMap.containsKey(transactionDto.getAddress())) {
                final TransactionDto dto = inTransactionsMap.get(transactionDto.getAddress());
                inTransactionsMap.put(transactionDto.getAddress(), new TransactionDto(transactionDto.getAddress(), dto.getValue().add(transactionDto.getValue())));
            } else {
                inTransactionsMap.put(transactionDto.getAddress(), transactionDto);
            }
        }

        final Set<TransactionDto> inTransactions = new HashSet<>(inTransactionsMap.values());

        final Set<TransactionDto> outTransactions = transactions.stream()
                .filter(tx -> tx.getFrom().equals(address))
                .map(tx -> new TransactionDto(tx.getTo(), mapWeiToEther(tx.getValue())))
                .collect(Collectors.toSet());

        return new TransactionsDto(address, inTransactions, outTransactions);
    }

    private BigDecimal mapWeiToEther(String value) {
        return new BigDecimal(value).divide(WEIS_IN_ETHER);
    }

}
