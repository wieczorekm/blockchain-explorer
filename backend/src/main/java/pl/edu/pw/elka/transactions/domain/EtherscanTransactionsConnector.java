package pl.edu.pw.elka.transactions.domain;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

class EtherscanTransactionsConnector implements TransactionsConnector {

    @Override
    public TransactionLists fetchTransactionLists(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String transactionsStringJson = restTemplate.getForObject("http://api.etherscan.io/api?module=account&action=txlist&address="+address+"&startblock=0&endblock=99999999&sort=asc&apikey=S5343FW214RWWDHQKR5E9ECC43EVGPEH9R", String.class);

        ArrayList<TransactionDetails> incomeTransactionsArray = new ArrayList<>();
        ArrayList<TransactionDetails> outcomeTransactionsArray = new ArrayList<>();

        fillTransactionArrays(address, transactionsStringJson, incomeTransactionsArray, outcomeTransactionsArray);

        return new TransactionLists(incomeTransactionsArray, outcomeTransactionsArray);
    }

    private void fillTransactionArrays(String address, String jsonData, ArrayList<TransactionDetails> incomeArray, ArrayList<TransactionDetails> outcomeArray) {
        JSONObject givenJsonObject = new JSONObject(jsonData);
        JSONArray givenJsonArray = givenJsonObject.getJSONArray("result");

        for(int i = 0; i < givenJsonArray.length(); i++){
            JSONObject object = givenJsonArray.getJSONObject(i);

            if(object.getString("from").equals(address)) {
                TransactionDetails transactionDetails = new TransactionDetails(object.getString("to"), object.getString("value"));
                outcomeArray.add(transactionDetails);
            }
            else if(object.getString("to").equals(address)) {
                TransactionDetails transactionDetails = new TransactionDetails(object.getString("from"), object.getString("value"));
                incomeArray.add(transactionDetails);
            }
        }
    }
}
