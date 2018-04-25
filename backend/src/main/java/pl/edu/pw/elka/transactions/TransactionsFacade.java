package pl.edu.pw.elka.transactions;

import pl.edu.pw.elka.etherscan.EtherscanFacade;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionDto;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;
import pl.edu.pw.elka.transactions.dtos.TransactionsDto;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionsFacade {

    private final EtherscanFacade etherscanFacade;

    public TransactionsFacade(EtherscanFacade etherscanFacade) {
        this.etherscanFacade = etherscanFacade;
    }

    public TransactionsDto getTransactionsForAddress(String address) {
        EtherscanTransactionsDto transactionsForAddress = etherscanFacade.getTransactionsForAddress(address);

        Set<String> outAddresses = getOutTransactions(address, transactionsForAddress);
        Set<String> inAddresses = getInTransactions(address, transactionsForAddress);
        return new TransactionsDto(inAddresses, outAddresses);
    }

    private Set<String> getOutTransactions(String address, EtherscanTransactionsDto transactionsForAddress) {
        return getTransactions(transactionsForAddress,
                transactionDto -> transactionDto.getFrom().equals(address), EtherscanTransactionDto::getTo);
    }

    private Set<String> getInTransactions(String address, EtherscanTransactionsDto transactionsForAddress) {
        return getTransactions(transactionsForAddress,
                transactionDto -> transactionDto.getTo().equals(address), EtherscanTransactionDto::getFrom);
    }


    private Set<String> getTransactions(EtherscanTransactionsDto transactionsForAddress,
                                        Predicate<EtherscanTransactionDto> etherscanTransactionDtoPredicate,
                                        Function<EtherscanTransactionDto, String> getFunction) {
        return transactionsForAddress.getTransactions().stream()
                .filter(etherscanTransactionDtoPredicate)
                .map(getFunction)
                .collect(Collectors.toSet());
    }
}
