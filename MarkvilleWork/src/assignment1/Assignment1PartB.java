package assignment1;
import java.util.*;
import java.io.*;

// Estar Guan
// September 10, 2024,
// This program reads from a file that has hexidecimal encoded characters and decodes it back into its actuals values
public class Assignment1PartB {

    public static void main (String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s = in.readLine();
        String newString = "";
        int i = 0;
        while (i<s.length()){
            if (s.charAt(i) == '%'){
                if (s.charAt(i+1)=='%'){
                    newString += "%";
                    i+=1;
                }else{
                    newString += (char)Integer.parseInt(s.substring(i+1,i+3),16);
                    i += 2;
                }
            }else{
                newString += s.charAt(i)+"";
            }
            i+= 1;
        }
        System.out.println(newString);
    }
}
