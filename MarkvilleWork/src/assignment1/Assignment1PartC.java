package assignment1;
import java.util.*;
import java.io.*;

//Estar Guan
//September 14 2024
//This program reads in from a file and then finds the longest palindrome in each line
//For each line it will print the longest palindrome, its starting position and its length

//This code SHOULD include the bonus  which is to run a 100000 character file almost instantly
public class Assignment1PartC {

    public static void main(String[] args) throws IOException{
       BufferedReader inFile = new BufferedReader(new FileReader("inputFilePartC.txt"));
       String line;
        long s = System.nanoTime();
        while ((line = inFile.readLine())!=null){
            int longestLength = 0;
            String ans ="";
            //int
            for (int i = 0; i < line.length();i++){
                int leftCheck = i;
                int rightCheck = i;
                while(leftCheck>=0 && rightCheck<line.length() && (line.charAt(rightCheck)+"").equalsIgnoreCase((line.charAt(leftCheck)+""))){
                    if (rightCheck-leftCheck+1>longestLength){
                        longestLength = rightCheck-leftCheck+1;
                        ans = line.substring(leftCheck,rightCheck+1);
                    }
                    leftCheck--;
                    rightCheck++;
                }
                leftCheck = i;
                rightCheck = i+1;
                while(leftCheck>= 0 && rightCheck<line.length() &&(line.charAt(rightCheck)+"").equalsIgnoreCase((line.charAt(leftCheck)+""))){
                    if (rightCheck-leftCheck+1>longestLength){
                        longestLength = rightCheck-leftCheck+1;
                        ans = line.substring(leftCheck,rightCheck+1);
                    }
                    leftCheck--;
                    rightCheck++;
                }
            }
            System.out.println("Finding the largest palindrome");
            System.out.println("Largest palindrome: " + ans);
            System.out.println("Starting position: " + (line.indexOf(ans)+1));
            System.out.println("Length: " + longestLength);
        }
        System.out.println((System.nanoTime()-s)/1000000);
        //Closing inFile
        inFile.close();
    }
}
