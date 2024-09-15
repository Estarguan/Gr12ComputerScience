package assignment1;
// Jonathan Jiang
// September 15, 2024
// Assignment 1: Java Review and Using System Methods
//
// Part C:
// This program takes in a text file input.txt and returns the longest palindrome on each line
// The program loops through each character of each line and checks for the
// palindrome starting from the inside to the outside, outputting the longest one.
import java.util.Scanner;
import java.io.*;
public class test {
    public static void main(String[] args) throws IOException{
        //File scanner
        Scanner scan = new Scanner(System.in);

        //Variables
        String str;
        String longest;
        int longestLength;
        long s = System.nanoTime();

        //Print out finding longest
        System.out.println("Finding the largest palindrome");


        str = scan.nextLine();
        longest = "";
        longestLength = 0;
        for (int i = 0; i < str.length(); i++) {
            //Checks for odd length palindrome at char i of str
            //int l checks towards the left of the character and int r checks towards the right
            int l = i, r = i;
            while (l >= 0 && r < str.length() && Character.toLowerCase(str.charAt(l)) == Character.toLowerCase(str.charAt(r))) {
                if (r - l + 1 > longestLength) {
                    longest = str.substring(l, r + 1);
                    longestLength = r - l + 1;
                }
                l--;
                r++;
            }

            //Checks for even length palindrome at char i of str
            l = i;
            r = i + 1;
            while (l >= 0 && r < str.length() && Character.toLowerCase(str.charAt(l)) == Character.toLowerCase(str.charAt(r))) {
                if (r - l + 1 > longestLength) {
                    longest = str.substring(l, r + 1);
                    longestLength = r - l + 1;
                }
                l--;
                r++;
            }
        }
        //Print out palindrome, the index of the palindrome, cand the length of the palindrome
        System.out.println("Largest palindrome: " + longest);
        System.out.println("Starting position: " + (str.indexOf(longest)+1));
        System.out.println("Length: " + longest.length() + "\n");
        //Runtime of program
        System.out.println((System.nanoTime()-s)/1000000);
        //Close file scanner
        }
}