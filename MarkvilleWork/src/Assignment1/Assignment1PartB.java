package Assignment1;
import java.util.*;
import java.io.*;

// Estar Guan
// September 10, 2024,
// This program reads from an input file called "inputFilePartB.txt
// Which is encoded with hexidecimal values. The code reads in the lines one by one and decodes them.
public class Assignment1PartB {

    public static void main (String[] args) throws IOException{
        //Variables
        BufferedReader inFile = new BufferedReader(new FileReader("inputFilePartB.txt"));
        String s;
        while ((s = inFile.readLine())!=null){
            String newString = "";
            int i = 0;
            while (i<s.length()){
                if (s.charAt(i) == '%'){
                    //Make sure it is not %%
                    if (s.charAt(i+1)=='%'){
                        newString += "%";
                        i+=1;
                    }else{
                        //Find the integer value of the hexidecimal and then convert it to its ascii character
                        newString += (char)Integer.parseInt(s.substring(i+1,i+3),16);
                        i += 2;
                    }
                }else{
                    //If there are no % signs you can just add as normal
                    newString += s.charAt(i);
                }
                i++;
            }
            System.out.println(newString);
        }
        inFile.close();
    }
}
