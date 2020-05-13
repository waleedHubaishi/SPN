public class SBox {

    ConverionsOfNumbers converionsOfNumbers = new ConverionsOfNumbers();
    int m;

    /**
     * The S-Box.
     */
    private String[][] sBoxTable = {
            {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"},
            {"E","4","D","1","2","F","B","8","3","A","6","C","5","9","0","7"}
    };

    public SBox(int m){
        this.m = m;
    }

    /**
     * This method returns the value corresponding to an input from the S-Box.
     *
     * @param input to get the corresponding value of from the S-Box.
     * @return sBoxValue is the value corresponding of an input based on the S-Box.
     * @throws IllegalArgumentException if the input has no corresponding value;
     */
    public String bringSboxValue(String input){

        String [][] binaryArray = createBinarySboxTable();
        for(int i = 0; i<binaryArray[0].length;i++){

            if(binaryArray[0][i].equals(input)){
                return binaryArray[1][i];
            }
        }
        throw new IllegalArgumentException("The input has no corresponding value in the S-Box");
    }


    /**
     * This method returns the S-Box table but filled with binary representations of x and S(x).
     *
     * @return binarySboxTable is binary table of S-Box.
     */
    public String[][] createBinarySboxTable(){

        //this will contain the Binary representations.
        String [][] binarySboxTable = new String[sBoxTable.length][sBoxTable[0].length];

        for(int row=0; row<sBoxTable.length;row++){
            for(int col=0; col< sBoxTable[row].length;col++){

                //this contains the decimal value of sBoxTable[row][col].
                int decimalValue = converionsOfNumbers.convertHexaToDecimal(sBoxTable[row][col]);

                //this contains the binary representation of sBoxTable[row][col].
                String binaryRepresentation =  converionsOfNumbers.convertDecimalToBinary(decimalValue);

                if(binaryRepresentation.length() < m){

                    String zeros = "";

                    //prepare the Zero string with an appropriate number of zeros.
                    for(int i = 0; i<m-binaryRepresentation.length();i++){
                        zeros+="0";
                    }

                    //if the binary representation length is not already m bits, then fill it with Zeros at the start.
                    String sizedFourBinary = zeros+binaryRepresentation;
                    binaryRepresentation = sizedFourBinary;

                }

                //set the binary representation in the same position of the decimal value.
                binarySboxTable[row][col] = binaryRepresentation;
            }
        }
        return binarySboxTable;
    }
}
