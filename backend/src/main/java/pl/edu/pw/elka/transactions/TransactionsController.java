package pl.edu.pw.elka.transactions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.elka.transactions.domain.TransactionsFacade;
import pl.edu.pw.elka.transactions.dto.TransactionListsDto;

@RestController
public class TransactionsController {

    private final TransactionsFacade facade;

    public TransactionsController(TransactionsFacade facade) {
        this.facade = facade;
    }

    @GetMapping(value = "/ethereum/transactions/{address}")
    public ResponseEntity<TransactionListsDto> fetchTransactionsLists(@PathVariable("address") String address) {
        TransactionListsDto dto = facade.fetchTransactionLists(address);
        return ResponseEntity.ok(dto);
    }
}
