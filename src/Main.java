import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {

        String y = readFile("CifferText");

        //the size of the key of SPN, yet it is used to define a lot of other variables such as y-1 length.
        int L = 16;
        CTR ctr = new CTR(L,y);

        //the retrieved message
        String x = ctr.CTRdeciffer();

        writeToFile(x);
    }

    /**
     * This method reads the y value from the file passed as a parameter.
     *
     * @param fileName that contains the y value.
     * @return line the actual y value as a string.
     */
    public static String readFile(String fileName)
    {
        File file = new File(fileName+".txt");

        String line="";
        Scanner scan = null;

        try {
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                line = scan.nextLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            scan.close();
        }

        return line;
    }

    /**
     * This method writes the actual text into the file "KlarText.txt".
     *
     * @param m which is the message that should be written into the file.
     */
    public static void writeToFile(String m)
    {
        File textFile = new File("KlarText.txt");

        try (FileOutputStream fop = new FileOutputStream(textFile)) {

            // if file doesn't exists, then create it
            if (!textFile.exists()) {
                textFile.createNewFile();
            }

            // get the content in bytes
            byte[] contentInChar = m.getBytes();
            fop.write(contentInChar);
            fop.flush();
            fop.close();

            System.out.println("Please check the file KlarText.txt to see the original text");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
