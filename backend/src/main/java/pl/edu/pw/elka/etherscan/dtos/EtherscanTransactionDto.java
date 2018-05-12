package pl.edu.pw.elka.etherscan.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EtherscanTransactionDto {

    @JsonProperty
    private String from;

    @JsonProperty
    private String to;

    @JsonProperty
    private String blockNumber;

    @JsonProperty
    private String value;
}
