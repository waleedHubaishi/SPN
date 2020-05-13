import java.util.ArrayList;

public class ConverionsOfNumbers {


    /**
     * This method calculates the binary representation of a given decimal value.
     *
     * @param number which is the decimal value passed.
     * @return binary representation of the decimal value passed.
     */
    public String convertDecimalToBinary(int number){

       return Integer.toBinaryString(number);
    }

    /**
     * This method calculates the decimal value of a given binary representation.
     *
     * @param binary representation of the value to be converted to decimal.
     * @return decimal value of the passed binary.
     */
    public int convertBinaryToDecimal(String binary){

        int decimal = Integer.parseInt(binary,2);

        return decimal;

    }

    /**
     * This method calculates the decimal value of a given Hexadecimal representation.
     *
     * @param hexaNumber representation of the value in Hexadecimal to be converted to decimal.
     * @return decimal value of the passed Hexadecimal.
     */
    public int convertHexaToDecimal(String hexaNumber){

        int decimal = Integer.parseInt(hexaNumber,16);
        return decimal;

    }

    /**
     * This method splits the single string passed to an equally sized Arraylist of strings.
     *
     * @param text to be splitted.
     * @param size of each value in the arraylist.
     * @return ret the arraylist that contains the equally sized strings.
     */
    public ArrayList<String> splitEqually(String text, int size) {

        // Give the list the right capacity to start with.
        ArrayList<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }

    /**
     * This method fills the binary string with zeros to the left to match the size given.
     *
     * @param input to be filled with zeros to the left.
     * @param size to make the input match.
     * @return binaryFilledWithZeros the binary string filled with zeros.
     */
    public String filledWithZerosBinary(String input, int size){

        String binaryFilledWithZeros = "";
        if(input.length() < size){
            String zeros = "";

            for(int i = 0; i<size-input.length();i++){
                //prepare the Zero string with an appropriate number of zeros.
                zeros+="0";
            }
            //put the zeros in front then followed by the actual binary representation.
            binaryFilledWithZeros = zeros+input;
        }
        return binaryFilledWithZeros;

    }


}
