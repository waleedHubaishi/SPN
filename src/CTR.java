import java.util.ArrayList;

public class CTR {

    int L;
    String y ;

    ConverionsOfNumbers converionsOfNumbers = new ConverionsOfNumbers();
    Xor xor = new Xor();
    SPN spn = new SPN(4,4,4);


    public CTR(int l, String y){
        this.L = l;
        this.y = y;
    }


    /**
     * This method deciffers the string passed to it (y) based on the L (y-1 length) set by the constructor and the SPN
     * rules set in the SPN class.
     *
     * @return finalResultAsText is the deciffered of the passed message text.
     */
    public String CTRdeciffer(){

        //this contains the decimal value of y-1 so it is easier to apply +i mod 2^L rule of CTR.
        int yminus1Decimal = generateYMinus1DecimnalValue(y,L);

        //this contains the (y-1)+i mod 2^L values in decimal.
        ArrayList<Double> decimalValuesYMinus1PlusIMode2 = generateYminus1PlusIMod2InDecimal(yminus1Decimal,L,y.length());

        for(int i=0;i<decimalValuesYMinus1PlusIMode2.size();i++){

           // System.out.println(decimalValuesYMinus1PlusIMode2.get(i));

        }

        //this contains the binary representation of (y-1)+i mod 2^L.
        ArrayList<String> binaryOfDecimalValuesYMinus1PlusIMode2 = generateYminus1plusIMode2InBinary(decimalValuesYMinus1PlusIMode2,L);


        //this contains the output of SPN boxes distributed around the CTR.
        ArrayList<String> listOfSPNoutput = new ArrayList<String>();
        for(int i=0;i<binaryOfDecimalValuesYMinus1PlusIMode2.size();i++){
            listOfSPNoutput.add(spn.calculateE(binaryOfDecimalValuesYMinus1PlusIMode2.get(i)));
        }

        //this contains the list of all y(i).
        ArrayList<String> listOfY = converionsOfNumbers.splitEqually(y,L);

        //remove y-1 value from the list of y(i).
        listOfY.remove(0);

        //this contains the result of XOR y(i) with the output of SPNs.
        ArrayList<String> finalResults = generateXvalues(listOfY,listOfSPNoutput);

        //it detects padding if exists.
        detectPadding(finalResults);

        //this contains the binary representation of the whole text.
        String binaryStringOfResult = "";

        for(int i=0;i<finalResults.size();i++){
            binaryStringOfResult += finalResults.get(i);
        }

        //saving each 8 bits in a separate index.
        ArrayList<String> eightBitStringsOfBinary = converionsOfNumbers.splitEqually(binaryStringOfResult,8);

        //this contains the decimal value of the 8 binary bits.
        ArrayList<Integer> decimalOfEightBits = new ArrayList<Integer>();
        for(int i=0;i<eightBitStringsOfBinary.size();i++){
            decimalOfEightBits.add(converionsOfNumbers.convertBinaryToDecimal(eightBitStringsOfBinary.get(i)));
        }

        //this contains result in actual text.
        String finalResultAsText = "";

        //convert ASCII to character.
        for(int i=0;i<decimalOfEightBits.size();i++){
            finalResultAsText += Character.toString((char) (int)decimalOfEightBits.get(i));
        }

        return finalResultAsText;
    }


    /**
     * This method gets the y-1 decimal value from the given y binary representation.
     *
     * @param y the y value given.
     * @param length which is how many Ys to have to cover all characters.
     * @return YMinus1 is the y-1 value to use.
     */
    public int generateYMinus1DecimnalValue(String y, int length){

        ArrayList yMinus1List = converionsOfNumbers.splitEqually(y,length);
        String YMinus1BinaryRepresentation = yMinus1List.get(0).toString();

        //the decimal value of y-1
        int decimalYminus1 = converionsOfNumbers.convertBinaryToDecimal(YMinus1BinaryRepresentation);

        return decimalYminus1;
    }

    /**
     * This method gets a list that contains the y-1 value + i, then mod 2 to the power of l.
     *
     * @param decimalYminus1 the decimal value of y-1.
     * @param yMinus1Size the lenght of y-1 to determine the size of the followings y of i.
     * @param lenghtOfY the lenght of the whole y string containing all y of i values and y-1 as well.
     * @return yMinus1PlusIArray is array containing the y-1 value + i then mod 2 to the power of l and in decimal.
     */
    public ArrayList generateYminus1PlusIMod2InDecimal(int decimalYminus1,int yMinus1Size, int lenghtOfY){

        ArrayList yMinus1PlusIArray = new ArrayList();

        //basically how many Ys (except y-1) do we have, also how many SPN boxes would we end up with as well as X(i).
        int numberOfI = (lenghtOfY/yMinus1Size);

        //CTR normal values preparation.
        for (int i = 0;i<numberOfI;i++){
            yMinus1PlusIArray.add((decimalYminus1+i)%Math.pow(2,yMinus1Size));
        }

        return yMinus1PlusIArray;
    }

    /**
     * This method generates the list of binary string representation of the decimal value and filled with zeros to
     * match a given length.
     *
     * @param arrayOfDecimalValues the arraylist containing the decimal values.
     * @param length to match the binary string filled with zero with.
     * @return arrayOfBinaryYminus1PlusiMod is array containing binary representation strings filled with zeros.
     */
    public ArrayList generateYminus1plusIMode2InBinary(ArrayList<Double> arrayOfDecimalValues, int length){
        ArrayList arrayOfBinaryYminus1PlusiMod = new ArrayList();
        for(int i=0;i<arrayOfDecimalValues.size();i++){

            //this would hold the binary representation of the decimal value of (y-1)+i Mod 2k
            String binary = converionsOfNumbers.convertDecimalToBinary(arrayOfDecimalValues.get(i).intValue());

            //fill the binary representation so it matches the desired length.
            arrayOfBinaryYminus1PlusiMod.add(converionsOfNumbers.filledWithZerosBinary(binary,length));
        }

        return arrayOfBinaryYminus1PlusiMod;
    }


    /**
     * This method generates the list of binary string representation of X of i for CTR.
     *
     * @param yArrayList the arraylist of Y of i.
     * @param SPNoutput to XOR with Y of i to retrieve the actual X of i values.
     * @return arrayOfX is array containing X of i values after XORing the SPN output with Y of i.
     */
    public ArrayList<String> generateXvalues(ArrayList<String> yArrayList, ArrayList<String> SPNoutput){
        ArrayList<String> arrayOfX = new ArrayList<String>();

        for(int i=0;i<yArrayList.size();i++){

            //XOR SPN output with y(i).
            arrayOfX.add(xor.calculateXor(yArrayList.get(i),SPNoutput.get(i)));
        }
        return arrayOfX;
    }

    /**
     * This method detects the padding and then remove it remove it.
     *
     * @param arrayListOfY the arraylist of Y of i.
     */
    public void detectPadding(ArrayList<String> arrayListOfY){

        String filledWithZero = "";
        int lastIndex = arrayListOfY.size()-1;
        int blocksize = arrayListOfY.get(0).length();

        //this block is to mock how would a normal block look like if it is full of zeros.
        for(int i=0;i<blocksize;i++){
            filledWithZero +="0";
        }

        //if the last index value is just zeros.
        if((arrayListOfY.get(lastIndex).equals(filledWithZero))){

            char lastCharInSecondFromLastString = arrayListOfY.get(arrayListOfY.size()-2).charAt(arrayListOfY.get(arrayListOfY.size()-2).length()-1);

            //check if the last character of the previous index value is 1.
            if(lastCharInSecondFromLastString == '1'){

                String secondFromLastString = arrayListOfY.get(arrayListOfY.size()-2);
                //if yes, remove the last index because it is just full of zeros.
                arrayListOfY.remove(lastIndex);

                //remove the padding of the previous index value.
                String newStringWithoutPadding = retrieveStringWithoutPadding(secondFromLastString);

                //replace the value of the previous index with the new retrieved value without padding.
                arrayListOfY.set(arrayListOfY.size()-2,newStringWithoutPadding);
            }
        }

        // if the last index is not filled with zeros.
        else{

            char lastCharacterInLastString = arrayListOfY.get(lastIndex).charAt(arrayListOfY.get(lastIndex).length()-1);

            //check if the last character is zero
            if(lastCharacterInLastString == '0'){
                String newString = retrieveStringWithoutPadding(arrayListOfY.get(lastIndex));

                //if yes then put the new value without padding instead.
                arrayListOfY.set(lastIndex,newString);
            }
        }
    }

    /**
     * This method detects the padding part in a string and removes it.
     *
     * @param suspectedString the string that should have padding inside.
     * @return newString that contains no padding in it, different size than the original.
     */
    public String retrieveStringWithoutPadding(String suspectedString){
        int indexOfFirst1 = 0;
        String newString = "";

        for(int i=suspectedString.length()-1;i >= 0;i--){

            //if the last character in the suspected index is a 1.
            if(suspectedString.charAt(i) == '1'){
                //save the index of the last 1 as i.
                indexOfFirst1 = i;

                //dont waste your time going back, we have found it !
                break;
            }
        }
        for(int i=0;i<indexOfFirst1;i++){

            //include all the characters in the new string till the last appearance of a 1.
            newString +=suspectedString.charAt(i);
        }

        return newString;

    }




}
