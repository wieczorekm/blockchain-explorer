package pl.edu.pw.elka.etherscan;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

class RealEtherscanConnector implements EtherscanConnector {

    private final RestTemplate restTemplate;

    private final static String BASE_API_URL =
            "http://api.etherscan.io/api?module=account&action=txlist&startblock=0&endblock=99999999&sort=asc&address=";

    private final static String MINED_BLOCKS_URL =
            "https://api.etherscan.io/api?module=account&action=getminedblocks&blocktype=blocks&address=";

    RealEtherscanConnector() {
        restTemplate = new RestTemplate();
    }

    @Override
    public EtherscanTransactionsDto getTransactions(String address) {
        String apiUrl = BASE_API_URL + address;
        ResponseEntity<EtherscanTransactionsDto> entity = restTemplate.getForEntity(apiUrl, EtherscanTransactionsDto.class);
        return entity.getBody();
    }

    @Override
    public MinedBlocksDto getMinedBlocks(String address) {
        String apiUrl = MINED_BLOCKS_URL + address;
        ResponseEntity<MinedBlocksDto> entity = restTemplate.getForEntity(apiUrl, MinedBlocksDto.class);
        return entity.getBody();
    }
}
