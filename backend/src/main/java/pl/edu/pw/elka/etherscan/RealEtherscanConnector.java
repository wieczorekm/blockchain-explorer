package pl.edu.pw.elka.etherscan;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

class RealEtherscanConnector implements EtherscanConnector {

    private final RestTemplate restTemplate;

    private final static String BASE_API_URL =
            "http://api.etherscan.io/api?module=account&action=txlist&startblock=%s&endblock=%s&sort=asc&address=%s";

    private final static String MINED_BLOCKS_URL =
            "https://api.etherscan.io/api?module=account&action=getminedblocks&blocktype=blocks&address=%s";

    RealEtherscanConnector() {
        restTemplate = new RestTemplate();
    }

    @Override
    public EtherscanTransactionsDto getTransactions(String address, String startBlock, String endBlock) {
        final String apiUrl = String.format(BASE_API_URL, startBlock, endBlock, address);
        System.out.println(apiUrl);
        final ResponseEntity<EtherscanTransactionsDto> entity = restTemplate.getForEntity(apiUrl, EtherscanTransactionsDto.class);
        return entity.getBody();
    }

    @Override
    public MinedBlocksDto getMinedBlocks(String address) {
        final String apiUrl = String.format(MINED_BLOCKS_URL, address);
        final ResponseEntity<MinedBlocksDto> entity = restTemplate.getForEntity(apiUrl, MinedBlocksDto.class);
        return entity.getBody();
    }
}
