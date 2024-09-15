package assignment1;
import java.util.*;
import java.io.*;

public class Assignment1PartC {




    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        long s = System.nanoTime();
        String line = in.nextLine();
        int longestLength = 0;
        String ans ="";

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
        System.out.println(ans);
        System.out.println(longestLength);
        System.out.println((System.nanoTime()-s)/1000000);

    }
}
