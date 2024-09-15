package assignment1;
import java.io.*;
import java.util.*;
public class test {

//    public static void main(String[] args) throws IOException {
//        // Initialize variables for file reading
//        BufferedReader inputFile = new BufferedReader(new FileReader("inputFilePartC.txt"));
//        String line;
//
//        // Search for the largest palindrome in each line of the input file
//        while ((line = inputFile.readLine()) != null) {
//            int length = 0;
//            int start = 0;
//            System.out.println("Finding the largest palindrome");
//
//            // Loop through every letter as well as the space between each letter
//            // Then, use i / 2 as the starting index, which will yield 0, 0, 1, 1, ... line.length - 1
//            // If i is even, then the current loop is checking a character in the line
//            // If i is odd, then the current loop is checking the space between two characters
//            for (int i = 0; i < 2 * line.length() - 1; i++) {
//                // j is how far from each side of i the current loop checks
//                for (int j = 0; j < line.length() / 2 + 1; j++) {
//                    // If the i / 2 - j or i / 2 + j are out of bounds, exit the loop
//                    // If the characters on either side of i / 2 are different, there is no palindrome and we exit the loop
//                    if (i / 2 - j < 0 || i / 2 + j + i % 2 > line.length() - 1 || line.charAt(i / 2 - j) != line.charAt(i / 2 + j + i % 2)) break;
//                    if (2 * j + 1 + i % 2 > length) {
//                        start = i / 2 - j;
//                        length = 2 * j + 1 + i % 2;
//                    }
//                }
//            }
//
//            // Display results
//            System.out.println("Largest palindrome: " + line.substring(start, start + length));
//            System.out.println("Starting position: " + ++start);
//            System.out.println("Length: " + length);
//        }
//    }
    public static void main(String[] args) {
        final int SIZE = 100000; // Number of characters
        final String FILE_NAME = "largeInput.txt"; // Output file name
        Random random = new Random();

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            // Generate a string of SIZE random alphabetical characters
            for (int i = 0; i < SIZE; i++) {
                if (i%1000==0){
                    System.out.println("");
                }
                // Random character from 'a' to 'z'
                char c = (char) ('a' + random.nextInt(26));
                writer.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("File generated successfully: " + FILE_NAME);
    }
}
