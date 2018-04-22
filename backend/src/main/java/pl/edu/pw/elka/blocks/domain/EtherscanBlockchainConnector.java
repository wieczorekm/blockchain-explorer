package pl.edu.pw.elka.blocks.domain;

import org.springframework.web.client.RestTemplate;
import org.json.*;

class EtherscanBlockchainConnector implements BlockchainConnector {
    @Override
    public BlockDetails fetchCurrentBlockDetails(String blockNumber) {
        RestTemplate restTemplate = new RestTemplate();
        String blockStringJson = restTemplate.getForObject("https://api.etherscan.io/api?module=proxy&action=eth_getBlockByNumber&tag="+blockNumber+"&boolean=true&apikey=S5343FW214RWWDHQKR5E9ECC43EVGPEH9R", String.class);
        JSONObject object = new JSONObject(blockStringJson);
        object = object.getJSONObject("result");

        //TODO error handling - case when there is no "result" (object==null)

        return new BlockDetails(Integer.decode(object.getString("number")), object.getString("hash"), object.getString("parentHash"));
    }
}
