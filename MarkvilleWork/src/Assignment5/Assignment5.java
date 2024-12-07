package Assignment5;
import java.util.*;
import java.io.*;

public class Assignment5 {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader inFile = new BufferedReader(new FileReader("assignment5Input.txt"));
		System.out.println("What question do you want to do?: (1,2,3)");
		int question = 0;
		do {
			try {
				question = Integer.parseInt(in.readLine());
				break;
			}catch (NumberFormatException e) {
				System.out.println("Input a number");
			}
		}while(true);
		if (question == 1) {
			int testCases = Integer.parseInt(inFile.readLine());
			System.out.println("Finding the number of Substrings");
			for (int t = 0; t < testCases; t++) {
				String s = inFile.readLine();
				HashSet<String> set = new HashSet<>();
				set.add("");
				for (int i = 0; i <s.length();i++) {
					for (int j = i+1; j<s.length()+1;j++) {
						set.add(s.substring(i,j));
					}
				}

				System.out.println("String: " + s);
				System.out.println("No. of Substrings: " + set.size());
			}
		}
		else if (question == 2) {
			int lowerLimNum, lowerLimDen, upperLimNum, upperLimDen;
			System.out.print("Enter the maximum denominator: ");
			int n = Integer.parseInt(in.readLine());
			do {
				System.out.print("Enter the lower limit: ");
				String lowerLimit = in.readLine();
				if (lowerLimit.indexOf("/") != 1) {
					continue;
				}
				try {  //11/16
					lowerLimNum = Integer.parseInt(lowerLimit.substring(0,lowerLimit.indexOf("/")));
					lowerLimDen = Integer.parseInt(lowerLimit.substring(lowerLimit.indexOf("/")+1));
					//Im assuming lower limit cannot be exactly 1 or exactly 0
					if (lowerLimNum > lowerLimDen || lowerLimDen == 0) {
							continue;
					}
					break;
				}catch(NumberFormatException e) {
					continue;
				}
			}while(true);

			do {
				System.out.print("Enter the upper limit: ");
				String upperLimit = in.readLine();
				
				try {  //11/16
					upperLimNum = Integer.parseInt(upperLimit.substring(0,upperLimit.indexOf("/")));
					upperLimDen = Integer.parseInt(upperLimit.substring(upperLimit.indexOf("/")+1));
					//Im assuming lower limit cannot be exactly 1 or exactly 0
					if (upperLimNum > upperLimDen || upperLimDen == 0) {
							continue;
					}
					break;
				}catch(NumberFormatException e) {
					continue;
				}
			}while(true);
			
			Fraction upperLimit = new Fraction (upperLimNum, upperLimDen);
			Fraction lowerLimit = new Fraction (lowerLimNum,lowerLimDen);
			
			TreeSet<Fraction> mySet = new TreeSet<>();
			for (int den = 1; den <= n;den++) {
				for (int num = 0; num <= den; num++) {
					mySet.add(new Fraction(num,den));
				}
			}
			System.out.println(mySet);
			int ans = mySet.subSet(lowerLimit,true, upperLimit,true).size();
			System.out.println("Total number of fractions: " + mySet.size());
			System.out.printf("Number of Fractions between %s and %s inclusive: %d", lowerLimit,upperLimit,ans);
		}else if (question == 3) {
			System.out.print("What is your number of cards?: ");
			int cardNum = Integer.parseInt(in.readLine());
			Deque<Integer> dq = new LinkedList<>();
			for (int i = cardNum; i>0;i--) {
				if (dq.size() > 1) {
					dq.offerFirst(dq.pollLast());
				}
				dq.offerFirst(i);
			}
			System.out.println(dq);
			
		}
		
	}
}

class Fraction implements Comparable<Fraction>{
	private int numerator;
	private int denominator;
	
	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public int getNumerator() {
		return this.numerator;
	}
	public int getDenominator() {
		return this.denominator;
	}
	
	public String toString() {
		return String.format("%d/%d", this.numerator,this.denominator);
	}
	public int compareTo(Fraction fraction) {
		if (this.numerator*fraction.denominator == this.denominator*fraction.numerator) {
			return 0;
		}else if(this.numerator*fraction.denominator < this.denominator*fraction.numerator) {
			return -1;
		}return 1;
	}
	
}