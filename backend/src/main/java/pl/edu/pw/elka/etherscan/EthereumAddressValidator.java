package pl.edu.pw.elka.etherscan;



public class EthereumAddressValidator {

    private final static int correctAddressLength = 42;
    private final static char correctFirstChar = '0';
    private final static char correctSecondChar = 'x';

    public void validateAddress(String address){
            validateNull(address);
            validateLength(address);
            validateFirstChar(address);
            validateSecondChar(address);
            String addressWithout0x = address.replaceFirst("0x", "");
            validateStructure(addressWithout0x);
            validateChecksum(addressWithout0x);
    }

    private void validateNull(String address) {
        if(address == null) {
            throw new BadEthereumAddressException("Wrong Ethereum address - No address");
        }
    }

    private void validateLength(String address) {
        if(address.length() != correctAddressLength) {
            throw new BadEthereumAddressException("Wrong Ethereum address - Wrong address length");
        }
    }

    private void validateFirstChar(String address) {
        if(address.charAt(0) != correctFirstChar) {
            throw new BadEthereumAddressException("Wrong Ethereum address - 0x prefix not found");
        }
    }

    private void validateSecondChar(String address) {
        if(address.charAt(1) != correctSecondChar) {
            throw new BadEthereumAddressException("Wrong Ethereum address - 0x prefix not found");
        }
    }

    private void validateStructure(String address) {
        if(!address.equals(address.toUpperCase()) && !address.equals(address.toLowerCase())) {
            throw new BadEthereumAddressException("Wrong Ethereum address - Wrong address structure");
        }
    }

    private void validateChecksum(String address) {
        //TODO:    Check that all int in string after sh3 after parsingInt is different than original
    }

}
