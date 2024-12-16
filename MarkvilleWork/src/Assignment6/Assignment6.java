package Assignment6;
import java.util.*;
import java.io.*;

public class Assignment6 {

    public static void main(String[] args) throws IOException{
        BufferedReader inFile = new BufferedReader(new FileReader("Assignment6Input.txt"));
        String line;
        HashMap<String, WordObj> wordMap = new HashMap<>();
        while((line = inFile.readLine())!= null){

            //This should remove every single whitespace
            String[] words = line.split("\\s+");

            for (String word : words){
                //It says to make it case insensitive
                word = word.toLowerCase();
                //So $ means at the end and ^ means at the start apparently
                word = word.replaceAll("^'+|'$+", "");

                //Im removing every 's so all possevive gets removed, only issue is that he's gets turned into he. 0 clue how to fix that
                word = word.replaceAll("'s$", "");

                //if its not a character, number, ', or - remove it. So keep all digits.
                word = word.replaceAll("[^\\w\\d'-]+", "");

                //Since I don't remove - at start and end later in the code I do it now. All occurences included so --hellothere'' becomes hellothere
                word = word.replaceAll("^-+|-$+","");
                //Standalone characters become empty strings and I dont wanna add that to my map
                if (word.isEmpty()){
                    continue;
                }

                if (wordMap.containsKey(word)){
                    wordMap.get(word).addOne();
                }else{
                    wordMap.put(word,new WordObj(word,1));
                }
            }
        }
        inFile.close();
        PriorityQueue <WordObj> pq = new PriorityQueue<>(wordMap.values());
        for (int i = 0; i < 20; i++){
            if (pq.peek() == null)break;
            System.out.println(pq.poll());
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

