import java.util.ArrayList;

public class Key {


    /**
     * this is the value of the key to be used in the SPN.
     */
    static final String KEY_USED = "00111010100101001101011000111111";


    /**
     * This method generates the keys to be used in the SPN encryption operation.
     *
     * @param rounds which is the number of rounds in SPN.
     * @return keysArray an array that contains the keys generated.
     */
    public ArrayList<String> keyGenerator(int rounds){

        int numberOfKeys = rounds+1;
        ArrayList keysArray = new ArrayList<String>();

        for(int i=0;i<numberOfKeys;i++){

            int startingIndexForKEy = 4*i;
            String key = String.valueOf(KEY_USED.charAt(startingIndexForKEy));

            for(int z = 1; z<16;z++){

                //put the following yet in scope character for each i (k of i) into the key string.
                key = key+String.valueOf(KEY_USED.charAt(z+startingIndexForKEy));
            }
            keysArray.add(key);
        }
        return keysArray;
    }
}
