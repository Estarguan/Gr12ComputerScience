package Assignment6;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Assignment6 implements ActionListener {
	//Global Vars
	public static JComboBox<String> fileDropDown;
	public static JTextArea outputField;
	
	public Assignment6() {
		JFrame myFrame = new JFrame("Assignment6");
    	JPanel myPanel = new JPanel();
    	JPanel leftPanel = new JPanel();
    	JPanel topPanel = new JPanel();
    	JButton myButton = new JButton("Add File");
    	JTextArea previewField = new JTextArea();
    	fileDropDown = new JComboBox<>();
    	outputField = new JTextArea();
    	
    	leftPanel.setLayout(new BorderLayout());    
    	leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	myPanel.setPreferredSize(new Dimension(1200,800));
    	myPanel.setLayout(new GridLayout(1,2));
    	
    	leftPanel.add(outputField,BorderLayout.CENTER);
    	topPanel.add(myButton);
    	topPanel.add(fileDropDown);
    	leftPanel.add(topPanel,BorderLayout.NORTH);
    	fileDropDown.addItem("ALICE.txt");
    	fileDropDown.addItem("MOBY.txt");
        fileDropDown.addItem("Assignment6Input.txt");
    	fileDropDown.addActionListener(this);
    	fileDropDown.setActionCommand("file chosen");
        outputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
    	
     	myPanel.add(leftPanel);
    	myPanel.add(previewField);
    	myFrame.add(myPanel);
    	myFrame.pack();
    	myFrame.setVisible(true);
	}
	
    public static void main(String[] args){
    	new Assignment6();
    }

    public String handleFile(String fileName) throws IOException{
    	BufferedReader inFile = new BufferedReader(new FileReader(fileName));
        String line;
        HashMap<String, WordObj> wordMap = new HashMap<>();
        HashSet<String> specialCases = new HashSet<>();
        specialCases.add("he's");
        specialCases.add("she's");
        specialCases.add("it's");
        specialCases.add("there's");
        long startTime = System.currentTimeMillis();
        while((line = inFile.readLine())!= null) {
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

                    //remove 's except the 4 sepcial cases
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
                wordMap.putIfAbsent(word, new WordObj(word, 0));
                wordMap.get(word).addOne();
                }

            }

        }
        inFile.close();
        PriorityQueue <WordObj> pq = new PriorityQueue<>(wordMap.values());
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 20; i++){
            if (pq.peek() == null)break;
            WordObj removedWord = pq.poll();
            if (i<10){
            output.append(String.format("\t%d) %-24s%d%n", (i+1),removedWord.getWord(),removedWord.getFreq()));
            }else {
                output.append(String.format("\t%d) %-23s%d%n", (i+1),removedWord.getWord(),removedWord.getFreq()));

            }
        }
        return String.format("\tTotal Time: %d milleseconds%n%n\t20 Most Frequent Words%n\tWords\t\tFrequency%n%s",(System.currentTimeMillis()-startTime), output);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("file chosen")) {
			try {
				outputField.setText(handleFile(fileDropDown.getSelectedItem().toString()));

			} catch (IOException exception) {
				JOptionPane.showMessageDialog(null, "IOException Try Again",
                        "File Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

class WordObj implements Comparable<WordObj>{
    //Made variables static because all objects of the same word should have the same word and same freq ALTHOUGH there should never be one instance of each word.
    private String word;
    private int freq;

    public WordObj(String word, int freq){
        this.word = word;
        this.freq = freq;
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
    public int compareTo(WordObj o) {
        return o.freq-this.freq;
    }
}

