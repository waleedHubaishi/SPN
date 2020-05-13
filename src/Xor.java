public class Xor {

    /**
     * This method returns result of XORing two equally sized binary numbers.
     *
     * @param fNum which is the first number represented in binary.
     * @param sNum which is the second number represented in binary.
     * @return result is the result of XORing both binaries.
     * @throws IllegalArgumentException if the size of both binaries is not the same;
     */
    public  String calculateXor(String fNum, String sNum){

        if(fNum.length() != sNum.length()){

            throw new IllegalArgumentException("The size of the parameters is not the same");
        }

        String result ="";
        for(int i=0;i<fNum.length();i++){
            if(fNum.charAt(i) == sNum.charAt(i)){
                result=result+"0";
            }
            else{
                result = result + "1";
            }
        }

        return result;

    }
}
