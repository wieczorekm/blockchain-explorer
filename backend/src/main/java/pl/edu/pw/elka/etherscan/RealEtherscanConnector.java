package pl.edu.pw.elka.etherscan;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;

class RealEtherscanConnector implements EtherscanConnector {

    private final static String BASE_API_URL =
            "http://api.etherscan.io/api?module=account&action=txlist&startblock=0&endblock=99999999&sort=asc&address=";

    @Override
    public EtherscanTransactionsDto getTransactions(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = BASE_API_URL + address;
        ResponseEntity<EtherscanTransactionsDto> entity = restTemplate.getForEntity(apiUrl, EtherscanTransactionsDto.class);
        return entity.getBody();
    }
}
