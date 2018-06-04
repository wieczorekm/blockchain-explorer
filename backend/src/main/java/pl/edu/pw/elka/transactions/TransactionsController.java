package pl.edu.pw.elka.transactions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.elka.transactions.dtos.TransactionsDto;

@RestController
class TransactionsController {

    private final TransactionsFacade facade;

    TransactionsController(TransactionsFacade facade) {
        this.facade = facade;
    }

    @GetMapping(value = "/ethereum/transactions/{address}")
    TransactionsDto getTransactions(@PathVariable String address,
                                    @RequestParam(required = false, defaultValue = "0") String startBlock,
                                    @RequestParam(required = false, defaultValue = "99999999") String endBlock) {
        return facade.getTransactionsForAddress(address, startBlock, endBlock);
    }

}
