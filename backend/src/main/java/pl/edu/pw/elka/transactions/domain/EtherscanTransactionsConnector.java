package pl.edu.pw.elka.transactions.domain;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

class EtherscanTransactionsConnector implements TransactionsConnector {

    @Override
    public TransactionLists fetchTransactionLists(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String incomeTransactionsStringJson = restTemplate.getForObject("http://api.etherscan.io/api?module=account&action=txlist&address="+address+"&startblock=0&endblock=99999999&sort=asc&apikey=S5343FW214RWWDHQKR5E9ECC43EVGPEH9R", String.class);
        String outcomeTransactionsStringJson = restTemplate.getForObject("http://api.etherscan.io/api?module=account&action=txlistinternal&address="+address+"&startblock=0&endblock=2702578&sort=asc&apikey=S5343FW214RWWDHQKR5E9ECC43EVGPEH9R", String.class);

        return new TransactionLists(makeIncomeTransactionArray(incomeTransactionsStringJson), makeOutcomeTransactionArray(outcomeTransactionsStringJson));
    }

    private ArrayList<TransactionDetails> makeIncomeTransactionArray(String jsonData) {
        JSONObject givenJsonObject = new JSONObject(jsonData);
        JSONArray givenJsonArray = givenJsonObject.getJSONArray("result");
        ArrayList<TransactionDetails> resultArray = new ArrayList<>();

        for(int i = 0; i < givenJsonArray.length(); i++){
            JSONObject object = givenJsonArray.getJSONObject(i);
            TransactionDetails transactionDetails = new TransactionDetails(object.getString("from"), object.getString("value"));

            resultArray.add(transactionDetails);
        }

        return resultArray;
    }

    private ArrayList<TransactionDetails> makeOutcomeTransactionArray(String jsonData) {
        JSONObject givenJsonObject = new JSONObject(jsonData);
        JSONArray givenJsonArray = givenJsonObject.getJSONArray("result");
        ArrayList<TransactionDetails> resultArray = new ArrayList<>();

        for(int i = 0; i < givenJsonArray.length(); i++){
            JSONObject object = givenJsonArray.getJSONObject(i);
            TransactionDetails transactionDetails = new TransactionDetails(object.getString("to"), object.getString("value"));

            resultArray.add(transactionDetails);
        }

        return resultArray;
    }
}
