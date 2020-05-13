public class Bitpermutation {

    private int[][] betaTable = {
            {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
            {0,4,8,12,1,5,9,13,2,6,10,14,3,7,11,15}
    };


    /**
     * This method finds the new Index of an old Index, the re-mapping is done based on the given Permutation Table.
     *
     * @param oldIndex to be remapped.
     * @return newIndex to put the value of the oldIndex in.
     */
    public int findNewIndex(int oldIndex) {

        int newIndex = 0;

        for (int i = 0; i < betaTable[0].length; i++) {
            if (betaTable[0][i] == oldIndex) {

                //put the new index found in the table into the newIndex int variable.
                newIndex = betaTable[1][i];

                //break so we dont proceed cause we found it already.
                break;
            }
        }
        return newIndex;
    }

    /**
     * This method retrieves a permutated string out of a given string based on the rules of the Permutation table.
     *
     * @param input string to be permutated.
     * @return permutatedString that follows the permutation table rules.
     */
    public String retrieveBitPermutationResult(String input){

        //split all the character in a given string into an array of strings, toCharArray() gives back char array
        //and not a String array that is why we used this regex.
        String [] charInStringArray = input.split("(?!^)");

        //create an array of the same length of the string input to hold the permutated characters.
        String[] permutatedCharactersArray = new String[input.length()];

        for(int i=0;i<charInStringArray.length;i++){

            //put the character into its new position in the array.
            permutatedCharactersArray[findNewIndex(i)] = charInStringArray[i];
            }

        //build a string out of the character array with the permutated new positions of the characters.
        StringBuilder builder = new StringBuilder();
        for(String s : permutatedCharactersArray) {
            builder.append(s);
        }
        String permutatedString = builder.toString();

       return permutatedString;
    }
}
