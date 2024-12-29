package Assignment6;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//Estar Guan
//December 18, 2024
//Assignment 6: Word Frequency Assignment
//This program allows users to select txt files and see the top 20 words that appear in that file as well as the file itself
//The files of the story Moby Dick and Alice in the Wonderland should be preloaded upon opening

public class Assignment6Bonus implements ActionListener  {
    //Global Vars
    public static JComboBox<String> fileDropDown;
    public static JTextArea outputField;
    public static JTextArea previewField;

    //Constructor of code that runs when an instance of Assignment 6 is made. I declare all my graphic variables here
    //Parameters: None
    //Return: None
    public Assignment6Bonus() {
        JFrame myFrame = new JFrame("Assignment6");
        JPanel myPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JButton myButton = new JButton("Add File");
        previewField = new JTextArea();
        JScrollPane previewScrollPane = new JScrollPane(previewField);
        fileDropDown = new JComboBox<>();
        outputField = new JTextArea();
        JScrollPane outputScrollPane = new JScrollPane(outputField);

        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        myPanel.setPreferredSize(new Dimension(1200,800));
        myPanel.setLayout(new GridLayout(1,2));

        leftPanel.add(outputScrollPane,BorderLayout.CENTER);
        topPanel.add(myButton);
        topPanel.add(fileDropDown);
        leftPanel.add(topPanel,BorderLayout.NORTH);
        fileDropDown.addItem("ALICE.txt");
        fileDropDown.addItem("MOBY.txt");
        fileDropDown.addActionListener(this);
        fileDropDown.setActionCommand("file chosen");
        outputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        myButton.addActionListener(this);
        myButton.setActionCommand("add file");

        myPanel.add(leftPanel);
        myPanel.add(previewScrollPane);
        myFrame.add(myPanel);
        myFrame.pack();
        myFrame.setVisible(true);
    }

    //Main Method
    public static void main(String[] args){
        new Assignment6Bonus();
    }

    //This method reads through our selected textfile adds all words to a hashmap which tracks each words frequency's in a word object
    //It then proceeds to add all the values of this hashset to a priority queue where it will then add the top 20 words to well formated string
    //Parameters: The name of the file we are taking in
    //Return: The string we need to display in our textfield
    public String handleFile(String fileName) throws IOException{
        int chapter = 0;
        BufferedReader inFile = new BufferedReader(new FileReader(fileName));
        String line;
        HashMap<String, Word> wordMap = new HashMap<>();
        HashSet<String> specialCases = new HashSet<>();
        specialCases.add("he's");
        specialCases.add("she's");
        specialCases.add("it's");
        specialCases.add("there's");
        StringBuilder output = new StringBuilder();
        long startTime = System.currentTimeMillis();
        while((line = inFile.readLine())!= null) {
            if (line.contains(".. < chapter")){
                if (chapter != 0){
                    PriorityQueue <Word> pq = new PriorityQueue<>(wordMap.values());
                    output.append("Chapter " + chapter+":\n");
                    for (int i = 0; i < 10; i++){
                        if (pq.peek() == null)break;
                        Word removedWord = pq.poll();
                        //Making the spacing line up perfectly
                        if (i<9){
                            output.append(String.format("\t%d) %-24s%d%n", (i+1),removedWord.getWord(),removedWord.getFreq()));
                        }else {
                            output.append(String.format("\t%d) %-23s%d%n", (i+1),removedWord.getWord(),removedWord.getFreq()));
                        }
                    }
                    wordMap = new HashMap<>();
                }
                chapter++;

            }
            StringTokenizer st = new StringTokenizer(line, " ");
            while (st.hasMoreTokens()) {

                String word = st.nextToken().toLowerCase();
                word = word.toLowerCase();
                boolean needChange = true;

                while (needChange) {

                    //I accept all numbers. If there is a symbol in the middle of the word (not leading or trailing) I just treat is as a letter.
                    //''-5hello#!@@#123 will be 5hello#!@@#123 because the numbers are kept meaning the symbols are between numbers and are treated as letters.

                    needChange = false;

                    //remove leading symbols/numbers
                    if (!word.isEmpty()) {
                        if (!Character.isLetter(word.charAt(0)) && !Character.isDigit(word.charAt(0))) {
                            word = word.substring(1);
                            needChange = true;
                        }
                    }

                    //remove 's except the 4 special cases
                    if (word.endsWith("'s") && !specialCases.contains(word)) {
                        word = word.substring(0, word.length() - 2);
                        needChange = true;
                    }
                    //remove trailing symbols/numbers
                    if (!word.isEmpty()) {
                        if (!Character.isLetter(word.charAt(word.length() - 1)) && !Character.isDigit(word.charAt(word.length()-1))) {
                            word = word.substring(0, word.length() - 1);
                            needChange = true;
                        }
                    }

                }
                if (!word.isEmpty()){
                    wordMap.putIfAbsent(word, new Word(word, 0));
                    wordMap.get(word).addOne();
                }

            }

        }
        inFile.close();
        //You will always miss the last chapter otherwise as the chapters loop ends the moment the lines are finished and the last line will not have the word chapter
        PriorityQueue <Word> pq = new PriorityQueue<>(wordMap.values());
        if (chapter != 0)
        output.append("Chapter " + chapter+":\n");
        for (int i = 0; i < 10; i++){
            if (pq.peek() == null)break;
            Word removedWord = pq.poll();
            //Making the spacing line up perfectly
            if (i<9){
                output.append(String.format("\t%d) %-24s%d%n", (i+1),removedWord.getWord(),removedWord.getFreq()));
            }else {
                output.append(String.format("\t%d) %-23s%d%n", (i+1),removedWord.getWord(),removedWord.getFreq()));
            }
        }
        return String.format("\tTotal Time: %d milleseconds%n%n\t20 Most Frequent Words%n\tWords\t\t\tFrequency%n%s",(System.currentTimeMillis()-startTime), output);
    }

    //This method takes our selected text file, reads through each line, and then combines in all into one big string which we will later display
    //Onto our textarea.
    //Parameters: The name of our txt file
    //Return: The very large string that contains the entire file
    public String previewFile(String fileName) throws IOException{
        BufferedReader inFile = new BufferedReader(new FileReader(fileName));
        StringBuilder output = new StringBuilder();
        String line = "";
        while ((line = inFile.readLine()) != null){
            output.append(line + "\n");
        }
        return output.toString();
    }

    //This is the overridden method from the action listener interface which listens for when we click a button
    //Click the dropdown menu's items. When clicked it will run methods depedning on the action commands
    //Parameters: ActionEvent e which is an object that contains data from the actions/events that occurred.
    //Return: Void
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("file chosen")) {
            try {
                outputField.setText(handleFile(fileDropDown.getSelectedItem().toString()));
                previewField.setText(previewFile(fileDropDown.getSelectedItem().toString()));
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null, "IOException Try Again",
                        "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (command.equals("add file")) {
            JFileChooser chooseFile = new JFileChooser();
            int option = chooseFile.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = chooseFile.getSelectedFile(); //get file
                if (file.getName().endsWith(".txt")){
                    fileDropDown.addItem(file.getName()); //add file name to dropdown
                }else{
                    JOptionPane.showMessageDialog(null, "Not a .txt file",
                            "File Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }
}

//My word object class which stores the name of the word as well as the frequency it appears
class Word implements Comparable<Word>{
    //Made variables static because all objects of the same word should have the same word and same freq ALTHOUGH there should never be one instance of each word.
    private String word;
    private int freq;

    //Constructor of the word object which intializes our word string as well as its starting frequency which is typically 0.
    public Word(String word, int freq){
        this.word = word;
        this.freq = freq;
    }

    //Similar to a setter method but we are incrementing rather than setting so it doesn't start with set
    //This method just adds one to our frequency of this word
    //Parameters: None
    //Return: Void
    public void addOne(){
        this.freq++;
    }

    //This method just returns our word name
    //Parameters: None
    //Return: String
    public String getWord(){
        return word;
    }
    //This method just returns our word frequency
    //Parameters: None
    //Return: Int
    public int getFreq(){
        return freq;
    }

    //This method just returns the string form of our object in case we need to print it which I dont think we do
    //Parameters: None
    //Return: String
    public String toString(){
        return this.word+ "=" + this.freq;
    }

    //Overridden method from the Comparable interface
    //Parameters: WordObj o we are comparing to
    //Return: int which determines which object goes first in the sort order
    @Override
    public int compareTo(Word o) {
        return o.freq-this.freq;
    }
}

