package pl.edu.pw.elka.blocks.domain;

import org.springframework.web.client.RestTemplate;
import org.json.*;

class EtherscanBlockchainConnector implements BlockchainConnector {
    @Override
    public BlockDetails fetchCurrentBlockDetails(String blockNumber) {
        //TODO implement http requests to etherscan API --- DONE?
        RestTemplate restTemplate = new RestTemplate();
//        JSONObject object = restTemplate.getForObject("https://api.etherscan.io/api?module=proxy&action=eth_getBlockByNumber&tag="+blockNumber+"&boolean=true&apikey=S5343FW214RWWDHQKR5E9ECC43EVGPEH9R", JSONObject.class);
//        JSONObject object = restTemplate.getForObject("http://api.etherscan.io/api?module=account&action=txlist&address=0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae&startblock=0&endblock=99999999&sort=asc&apikey=YourApiKeyToken", JSONObject.class);
        String json = restTemplate.getForObject("https://api.etherscan.io/api?module=proxy&action=eth_getBlockByNumber&tag="+blockNumber+"&boolean=true&apikey=S5343FW214RWWDHQKR5E9ECC43EVGPEH9R", String.class);
        JSONObject object = new JSONObject(json);
        object = object.getJSONObject("result");
        return new BlockDetails(Integer.decode(object.getString("number")), object.getString("hash"), object.getString("parentHash"));
    }
}
