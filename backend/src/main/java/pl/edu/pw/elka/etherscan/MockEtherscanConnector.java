package pl.edu.pw.elka.etherscan;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pw.elka.etherscan.dtos.EtherscanTransactionsDto;
import pl.edu.pw.elka.minedBlocks.dtos.MinedBlocksDto;

import java.io.IOException;

class MockEtherscanConnector implements EtherscanConnector {

    @Override
    public EtherscanTransactionsDto getTransactions(String address) {
        ObjectMapper objectMapper = new ObjectMapper();
        String sampleResponse = getSampleResponse();
        try {
            return objectMapper.readValue(sampleResponse, EtherscanTransactionsDto.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public MinedBlocksDto getMinedBlocks(String address) {
        ObjectMapper objectMapper = new ObjectMapper();
        String sampleResponse = getSampleMinedBlocks();
        try {
            return objectMapper.readValue(sampleResponse, MinedBlocksDto.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private String getSampleResponse() {
        return "{\n" +
                "  \"status\": \"1\",\n" +
                "  \"message\": \"OK\",\n" +
                "  \"result\": [{\n" +
                "    \"blockNumber\": \"4900809\",\n" +
                "    \"timeStamp\": \"1515835535\",\n" +
                "    \"hash\": \"0x42b9a619894cf5592f9ee3f0c7b6aea8b933d0acb7ffa517dd30cc1639e6f400\",\n" +
                "    \"nonce\": \"0\",\n" +
                "    \"blockHash\": \"0xb769ea5c775482a1ecd530f60f48873e5c3c5b40e59e106eed4f86856f8efa08\",\n" +
                "    \"transactionIndex\": \"154\",\n" +
                "    \"from\": \"0xbff4c784f69c825e21d079ac6aacdf1f8d317ece\",\n" +
                "    \"to\": \"0xe103f7baef7a048cb22a4d566a37f72f762df229\",\n" +
                "    \"value\": \"50000000000000000\",\n" +
                "    \"gas\": \"90000\",\n" +
                "    \"gasPrice\": \"50000000000\",\n" +
                "    \"isError\": \"0\",\n" +
                "    \"txreceipt_status\": \"1\",\n" +
                "    \"input\": \"0x\",\n" +
                "    \"contractAddress\": \"\",\n" +
                "    \"cumulativeGasUsed\": \"4043236\",\n" +
                "    \"gasUsed\": \"21000\",\n" +
                "    \"confirmations\": \"590831\"\n" +
                "  }, {\n" +
                "    \"blockNumber\": \"4901020\",\n" +
                "    \"timeStamp\": \"1515838918\",\n" +
                "    \"hash\": \"0xa4462c7ae793eb0c27a99f3f14c42a3ea76c80ad89d1926f1f5054f53da46f22\",\n" +
                "    \"nonce\": \"0\",\n" +
                "    \"blockHash\": \"0xe73e036ae8ca6225f8e03f0f4fa7432a3fb29c07bdeb4e21012296cfa2aa779e\",\n" +
                "    \"transactionIndex\": \"109\",\n" +
                "    \"from\": \"0x804cb76f481bd3c8190ae48486790cd4996f4542\",\n" +
                "    \"to\": \"0xe103f7baef7a048cb22a4d566a37f72f762df229\",\n" +
                "    \"value\": \"61990000000000000\",\n" +
                "    \"gas\": \"90000\",\n" +
                "    \"gasPrice\": \"50000000000\",\n" +
                "    \"isError\": \"0\",\n" +
                "    \"txreceipt_status\": \"1\",\n" +
                "    \"input\": \"0x\",\n" +
                "    \"contractAddress\": \"\",\n" +
                "    \"cumulativeGasUsed\": \"4278112\",\n" +
                "    \"gasUsed\": \"21000\",\n" +
                "    \"confirmations\": \"590620\"\n" +
                "  }]\n" +
                "}";
    }

    private String getSampleMinedBlocks() {
        return "{\n" +
                "  \"status\": \"1\",\n" +
                "  \"message\": \"OK\",\n" +
                "  \"result\": [{\n" +
                "    \"blockNumber\": \"3462296\",\n" +
                "    \"timeStamp\": \"1491118514\",\n" +
                "    \"blockReward\": \"5194770940000000000\"\n" +
                "  }, {\n" +
                "    \"blockNumber\": \"2691400\",\n" +
                "    \"timeStamp\": \"1480072029\",\n" +
                "    \"blockReward\":\"5086562212310617100\"\n" +
                "  }]\n" +
                "}";
    }
}
