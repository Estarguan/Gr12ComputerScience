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
    	fileDropDown.addActionListener(this);
    	fileDropDown.setActionCommand("file chosen");
    	
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
        while((line = inFile.readLine())!= null){

            //This should remove every single whitespace
            String[] words = line.split("\\s+");
            
            
            
            for (String word : words){
            	word = word.toLowerCase();
            	if (!specialCases.contains(word)) {
            		 //It says to make it case insensitive
                    //So $ means at the end and ^ means at the start apparently
                    word = word.replaceAll("^'+|'$+", "");

                    //Im removing every 's so all possevive gets removed, only issue is that he's gets turned into he. 0 clue how to fix that
                    word = word.replaceAll("'s$", "");

                    //if its not a character, number, ', or - remove it. So keep all digits but remove ALL symbols.
                    word = word.replaceAll("[^\\w\\d'-]+", "");

                    //Since I don't remove - at start and end later in the code I do it now. All occurences included so --hellothere'' becomes hellothere
                    word = word.replaceAll("^-+|-$+","");
                    //Standalone characters become empty strings and I dont wanna add that to my map
            	}
                if (word.isEmpty()){
                    continue;
                }

                //wordMap.put(word,new WordObj(word,wordMap.getOrDefault(word, new WordObj(word,0)).getFreq()+1));
                wordMap.putIfAbsent(word, new WordObj(word,0));
                wordMap.get(word).addOne();
                //                if (wordMap.containsKey(word)){
//                    wordMap.get(word).addOne();
//                }else{
//                    wordMap.put(word,new WordObj(word,1));
//                }
            }
        }
        inFile.close();
        PriorityQueue <WordObj> pq = new PriorityQueue<>(wordMap.values());
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 20; i++){
            if (pq.peek() == null)break;
            WordObj removedWord = pq.poll();
            output.append(String.format("\t%d) %s\t\t%d%n", (i+1),removedWord.getWord(),removedWord.getFreq()));
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

