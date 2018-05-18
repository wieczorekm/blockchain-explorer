package pl.edu.pw.elka.etherscan;



public class EthereumAddressValidator {

    public boolean checkAddres(String address){
        if(address.charAt(0) != '0' || address.charAt(1) != 'x')
            return false;
        if(address.length() != 42)
            return false;
        String tmpAddress = address.replaceFirst("0x", "");
        if(tmpAddress.equals(tmpAddress.toUpperCase()) || tmpAddress.equals(tmpAddress.toLowerCase()))
            return true;
        return ifChecksum(tmpAddress);
    }

    private boolean ifChecksum(String address) {
        //TODO:    Check that all int in string after sh3 after parsingInt is different than original
        return true;
    }

}
