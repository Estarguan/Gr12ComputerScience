package Assignment6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

//Evan Wang
//2024/12/18
//Word Frequency Assignment
//This program allows users to input text files and finds the # of occurences of the 20 most frequent words

public class Evan extends JFrame {
    //global variables
    private JComboBox<String> fileDropdown; //dropdown for file selection
    private JTextArea outputArea, fileContentArea; // text areas for displaying file content and word frequencies
    private JButton addButton; // button to add new files
    private Font textFont = new Font("Monospaced", Font.PLAIN, 14); // monospaced font for better alignment

    //constructor: sets up the GUI and initializes components
    public Evan() {
        setTitle("Word Frequency Finder");
        setSize(900, 600); // window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to close
        setLayout(new GridLayout(1, 2)); // divides window into two columns

        // create panel for file operations
        JPanel filePanel = new JPanel(new BorderLayout());
        addButton = new JButton("Add File"); //button to add files
        fileDropdown = new JComboBox<>(); //dropdown for selecting files
        fileDropdown.setPreferredSize(new Dimension(200, 20)); //size of dropdown
        JPanel topPanel = new JPanel(); //top panel
        topPanel.add(addButton);
        topPanel.add(fileDropdown);
        filePanel.add(topPanel, BorderLayout.NORTH); //add to top of window

        // text area to display file text
        fileContentArea = new JTextArea();
        fileContentArea.setFont(textFont); // set font
        fileContentArea.setEditable(false); //make it read-only
        JScrollPane fileScroll = new JScrollPane(fileContentArea); //add scroll pane to text area
        filePanel.add(fileScroll, BorderLayout.CENTER); //add scrollable text area to center of file

        // panel for output
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea(20, 30); //text area for output
        outputArea.setFont(textFont); // set font
        outputArea.setEditable(false); //make it read-only
        JScrollPane outputScroll = new JScrollPane(outputArea); //add scroll pane to output
        outputPanel.add(outputScroll, BorderLayout.CENTER); //add output to center of panel

        // add panels to main frame
        add(filePanel);
        add(outputPanel);

        // put default files in dropdown
        loadInitialFiles();

        // action for adding new files
        addButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(); //file choose for selecting files
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile(); //get file
                fileDropdown.addItem(file.getName()); //add file name to dropdown
            }
        });

        // action when file is selected
        fileDropdown.addActionListener(e -> {
            if (fileDropdown.getSelectedItem() != null) { //check if item is selected
                String fileName = fileDropdown.getSelectedItem().toString(); //get file name
                displayFileContent(fileName); //display file content in text area
                displayWordFrequencies(fileName); //display word frequencies
            }
        });

        setVisible(true);
    }

    //Method that loads the two default files into the dropdown
    //No parameters
    //No Return
    private void loadInitialFiles() {
        fileDropdown.addItem("ALICE.txt");
        fileDropdown.addItem("MOBY.txt");
        fileDropdown.addItem("Assignment6Input.txt");
    }

    //Method that reads text in a file and displays it in the text area
    //Parameter is a string of the file name
    //No Return
    private void displayFileContent(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            StringBuilder fileText = new StringBuilder(); //store file text
            while (scanner.hasNextLine()) { //readlines and add to stringbuilder
                fileText.append(scanner.nextLine() + "\n");
            }
            fileContentArea.setText(fileText.toString()); //display text
            scanner.close();
        } catch (FileNotFoundException e) {
            fileContentArea.setText("File not found: " + fileName);
        }
    }

    //Method that finds the 20 most frequent words in the file and displays it
    //Parameter is a string of the file name
    //No Return
    private void displayWordFrequencies(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            HashMap<String, Word> wordCount = new HashMap<>(); //hashmap to store each word and its corresponding word object containing its frequency
            long startTime = System.currentTimeMillis(); //start calculating time

            while (scanner.hasNextLine()) { //read every line
                String line = scanner.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(line, " "); //split by spaces
                while (tokenizer.hasMoreTokens()) {
                    String word = tokenizer.nextToken().toLowerCase(); //turn word to lowercase to be case insensitive
                    word = process(word); //process the word to remove symbols
                    if (word.length() > 0) { //if it isn't empty
                        if (wordCount.containsKey(word)) { //if it's not in hashmap, add it
                            wordCount.get(word).addOne();
                        } else { //if it is then increment its frequency
                            wordCount.put(word, new Word(word));
                        }

                    }
                }
            }

            //create and sort arraylist of values (word objects)
            List<Word> words = new ArrayList <> (wordCount.values());
            Collections.sort(words);

            //create stringbuilder to add to output area
            StringBuilder output = new StringBuilder();
            //add the time
            output.append("Total Time: " + (System.currentTimeMillis() - startTime) + " milliseconds\n\n");
            output.append("20 Most Frequent Words\n");

            // headers
            output.append(String.format("%-4s%-19s%s\n", "", "Words", "Frequency"));

            // get top 20 words or all words if it's less than 20
            for (int i = 0; i < 20 && i < words.size(); i++) {
                //format to be aligned
                output.append(String.format("%2d) %-18s %d\n", i + 1, words.get(i).getWord(), words.get(i).getFreq()));
            }

            outputArea.setText(output.toString()); //add text to output area
            scanner.close();
        } catch (FileNotFoundException e) {
            outputArea.setText("File not found: " + fileName);
        }
    }

    //Cleans up a word by removing necessary symbols and ignoring numbers
    //Parameter is the word as a string
    //Returns the processed string
    private String process(String s) {
        //Assumptions: Symbols will only be at the start or end of a word
        //Contractions are one word
        //All numbers ignored
        s = s.toLowerCase(); //make lowercase
        boolean changeMade = true; //if a change is made
        while (changeMade) { //stop when nothing is removed this pass
            changeMade = false;
//remove 's unless it is there's it's he's she's
            if (s.endsWith("'s") && !s.startsWith("there") && !s.startsWith("it") && !s.startsWith("he") && !s.startsWith("she")) {
                s = s.substring(0, s.length()-2);
                changeMade = true;
            }
//remove leading symbols/numbers
            if (s.length() > 0) {
                if (!Character.isLetter(s.charAt(s.length()-1))) {
                    s = s.substring(0, s.length()-1);
                    changeMade = true;
                }
            }
//remove trailing symbols/numbers
            if (s.length() > 0) {
                if (!Character.isLetter(s.charAt(0))) {
                    s = s.substring(1, s.length());
                    changeMade = true;
                }
            }
        }

        return s;
    }

    //main method
    public static void main(String[] args) {
        new Evan();
    }
}

class Word implements Comparable<Word>{
    //Made variables static because all objects of the same word should have the same word and same freq ALTHOUGH there should never be one instance of each word.
    private String word;
    private int freq;

    public Word(String word){
        this.word = word;
        this.freq = 1;
    }

    public void addOne(){
        this.freq++;
    }

    public String getWord(){
        return word;
    }
    public int getFreq(){
        return freq;
    }

    public String toString(){
        return this.word+ "=" + this.freq;
    }

    @Override
    public int compareTo(Word o) {
        return o.freq-this.freq;
    }
}