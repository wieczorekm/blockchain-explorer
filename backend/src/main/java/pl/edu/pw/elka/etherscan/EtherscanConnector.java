package pl.edu.pw.elka.etherscan;

import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;

interface EtherscanConnector {

    EtherscanTransactionsDto getTransactions(String address);
}
