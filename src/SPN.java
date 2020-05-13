import java.util.ArrayList;

public class SPN {

    private int r;
    private int n;
    private int m;

    public SPN(int m, int r, int n){
        this.r = r;
        this.m = m;
        this.n = n;
    }

    /**
     * those objects will be needed to calculate the SPN output.
     */
    ConverionsOfNumbers conv = new ConverionsOfNumbers();
    Xor xor = new Xor();
    SBox sBox = new SBox(4);
    Bitpermutation bitpermutation = new Bitpermutation();

    /**
     * ArraList containing the keys, the key is defined as a final in the Key class so no need to pass it here.
     */
    ArrayList keys = new Key().keyGenerator(4);


    /**
     * This method calculates the E output which is the output of SPN.
     *
     * @param text is the value that enters the SPN to calculate the output of with k.
     * @return xorResult the final result of SPN for the entered text and k which have the same size.
     */
    public String calculateE(String text){
        ArrayList splittedEquallyTextAfterXOR = new ArrayList<String>();
        String xorResult = "";
        String sBoxedString = "";
        String bitPermutatedString = "";

        for(int i=0;i<r;i++){

             //XOR the text with the corresponding key for the round, except the last.
             xorResult = xor.calculateXor(text,String.valueOf(keys.get(i)));

             //splitting the result after XORing to do S-Box.
             splittedEquallyTextAfterXOR = conv.splitEqually(xorResult,m);

                 for(int z = 0; z < splittedEquallyTextAfterXOR.size();z++){
                     //retrieve the S(x) value for each x.
                     sBoxedString += sBox.bringSboxValue(splittedEquallyTextAfterXOR.get(z).toString());
                 }

                 //do Bitpermutation except for the last round.
                 if(i < r-1){
                     bitPermutatedString = bitpermutation.retrieveBitPermutationResult(sBoxedString);
                     sBoxedString = "";
                     text = bitPermutatedString;
                 }
                 //the last round will have only S-Box value to pass, no Bitpermutation.
                 else{
                     text = sBoxedString;
                 }
        }

        //here we XOR the last key with the text coming out the last S-Box operation.
        xorResult = xor.calculateXor(text,String.valueOf(keys.get(keys.size()-1)));

        return xorResult;

    }
}
