package assignment1;
import java.util.*;
import java.io.*;

public class Assignment1PartA {
	//Estar Guan, 2024.09.07
	//Assignment 1 Part A Buying brawl star items.
	
	//Check if Can use throws
	
	//Description: This method repeatly asks for a double representing the amount of money to be spent.
	//Parameters: Takes in the question to ask, the BufferedReader to use
	//Return: the amount of money to use
	public static double getInput(String question, BufferedReader in)throws IOException {
		do {
			try {
				System.out.print(question);
				double amt = Double.parseDouble(in.readLine());
				if (amt < 0) {
					throw new NumberFormatException();
				}
				return amt;
			}catch(NumberFormatException e) {
				System.out.println("Invalid. ");
			}
		}while(true);
	}
	//Description: This method repeatedly asks for an integer number of gems and checks if you can afford it.
	//Parameters: Takes in the question to ask, the BufferedReader to use, and all the needed arrays
	//Return: the amount of gems bought
	public static int getInput(String question, int[] gemPacks, double[] gemPackPrices,double money, BufferedReader in) throws IOException{
		do {
			try {
				System.out.print(question);
				int s = Integer.parseInt(in.readLine());
				for (int i = 0; i < gemPacks.length;i++) {
					if (gemPacks[i] == s) {
						if (money < gemPackPrices[i]) {
							System.out.print("You cannot afford this. ");
						}else {
							return s;							
						}
					}
				}
				throw new NumberFormatException();
			}catch(NumberFormatException e) {
				System.out.println("Invalid. ");
			}
		}while(true);
	}
	//Description: This method checks the cost of the gems you're buying to calculate change afterwards
	//Parameters: The amount of gems bought, the array of gems and all their costs
	//Return: The cost of the gems
	public static double getCost(int gems, int[] gemPacks, double[] gemPackPrices) {
		for (int i = 0; i < gemPacks.length; i++) {
			if (gems==gemPacks[i]) {
				return gemPackPrices[i];
			}
		}
		return -1;
	}
	//Description: This method checks if the users item is a valid option
	//Parameters: The item the use is buying and the list of possible items.
	//Return: True if the item exists otherwise false
	public static boolean checkItem(String item, String[] items) {
		for (int i = 0; i < items.length;i++) {
			if (item.equalsIgnoreCase(items[i])) {
				return true;
			}
		}
		return false;
	}
	
	//Description: This method checks the cost of the item being bought and whether the user can afford it.
	//Parameters: The item being bought, the amount of gems we have, the array of skins, the array of brawlers, the bufferedreader for inputs. Also the skin cost cuz apparently its not random
	//Return: Returns the price of the item
	public static int calculateCosts(String item,int gems,int[] skinCosts,String[] brawlerTypes, int[] brawlerCosts, BufferedReader in,PrintWriter outFile, int skinCost) throws IOException{
		int itemCost = 0;
		if (item.equalsIgnoreCase("Spray")) {
			itemCost = 19;
			item = "Spray";
			outFile.printf("%-20s%-22s%-12d\n","Spray","\\",itemCost);
		}else if (item.equalsIgnoreCase("XP doubler")) {
			itemCost = 25;
			item = "XP doubler";
			outFile.printf("%-20s%-22s%-12d\n","XP doubler","\\",itemCost);
		}else if (item.equalsIgnoreCase("skin")) {
			itemCost = skinCost;
			if (gems<itemCost) {
				System.out.printf("It will cost %d gems to purchase skin. ",itemCost);
			}
			else {
				outFile.printf("%-20s%-22s%-12d\n","Skin","\\",itemCost);
			}
			item = "skin";
		}else if (item.equalsIgnoreCase("brawler")) {
			//Since we know we can't afford even 29 gems I just set the cost to 29 to print you do not have enough to buy this.
			if (gems < 29){
				itemCost = 29;
			}else{
				itemCost = getBrawlerCost(gems, brawlerTypes,brawlerCosts, in,outFile);
				item="brawler";
			}
		}
		
		if (gems<itemCost) {
			System.out.println("You do not have enough to buy this.");
			return gems;
		}
		System.out.printf("\t%d gems have been spent on %s. You have %d gems left.%n",itemCost, item, (gems-itemCost));
		return gems-itemCost;
		
	}
	
	//Description: This method checks the cost of the brawler being bought and checks if it is affordable.
	//Parameters: Array of all the brawlers, their costs, and the bufferedReader.
	//Return: The cost of the brawler.
	public static int getBrawlerCost(int gems,String[] brawlerTypes, int[] brawlerCosts,BufferedReader in,PrintWriter outFile) throws IOException {
		do {
			try {
				System.out.print("Enter the brawler type (rare, super rare, epic, mythic, legendary): ");
				String brawler = in.readLine();
				for (int i = 0; i < brawlerTypes.length;i++) {
					if (brawler.equalsIgnoreCase(brawlerTypes[i])) {
						if (gems<brawlerCosts[i]) {
							System.out.println("You do not have enough to buy this.");
						}else {
							outFile.printf("%-20s%-22s%-12d\n","Brawler",brawler,brawlerCosts[i]);
							return brawlerCosts[i];							
						}
					}
				}
				throw new NumberFormatException();
			}catch (NumberFormatException e) {
				System.out.print("Invalid. ");
			}
		}while(true);

	}
	
	public static void main (String[] args) throws IOException {
		//Variables
		int[] gemPacks = {30,80, 170, 360, 950, 2000};
		double[] gemPackPrices = {1.99, 4.99, 9.99,19.99, 49.99, 99.99};
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		String[] items = {"spray", "XP doubler", "skin","brawler"};
		int[] skinCosts = {29, 49, 79, 149, 199, 299, 499};
		String[] brawlerTypes = {"Rare", "Super Rare", "Epic", "Mythic", "Legendary"};
		int[] brawlerCosts = {29, 79, 169, 349, 699};
		int skinCost = skinCosts[(int)(Math.random()*(6-0+1))];
		
		
		PrintWriter outFile = new PrintWriter(new FileWriter("summary.txt"),true);
		outFile.println("Summary of your purchases:\n");
		
		//First paragraph
		//Calculate the budget
		double money = getInput("How much money are you spending on gems today? $", in);
		
		if (money < 1.99) {
			System.out.println("You are too poor to buy anything");
			System.exit(0);
		}
		//Calculate the amount of gems and change
		int gems = getInput("Enter the gem pack to purchase (30, 80, 170, 360, 950, 2000): ", gemPacks,gemPackPrices,money,in);
		int startGems = gems;


		double cost = getCost(gems,gemPacks,gemPackPrices);
		outFile.printf("AMOUNT SPENT\t\t$%.2f\n",cost);
		outFile.printf("# GEMS PURCHASHED\t%d\n\n",gems);
		for (int i = 0; i < 56;i++) {
			outFile.print("-");
		}
		outFile.println();
		System.out.printf("\tYou paid $%.2f. Your change is %.2f.%n%n", money,(money-cost));
		
		//Second Paragraph
		outFile.println("ITEM PURCHASED\t\tTYPE\t\t\t\t# GEMS");
		outFile.println("--------------\t\t----\t\t\t\t-------");

		//For some reason outfile.print() doesn't work only println

		//for (int i = 0; i < 14;i++) {
		//	outFile.print("Hello-");
		//}
		//outFile.print("\t\t-----\t\t\t\t-------");

		//The entire process of buying items
		boolean shopping = true;
		do {

			boolean itemPrinted = false;
			System.out.print("Enter the item to purchase (spray, XP doubler, skin, brawler): ");
			String item = in.readLine();
			if (checkItem(item, items)) {
				gems = calculateCosts(item, gems, skinCosts,brawlerTypes,brawlerCosts, in, outFile, skinCost);
				itemPrinted = true;	
				
			}else {
				System.out.print("Invalid. ");
			}
			if (itemPrinted) {
				do {
					//If you are too poor the afford anything the program should end.
					if (gems<19) {
						System.out.println("You cannot afford anything. ");
						shopping = false;
						break;
					}
					System.out.print("Are you buying anymore items? (y/n): ");
					String response = in.readLine();
					if (response.equalsIgnoreCase("Y")) {
						System.out.println("");
						break;
					}else if(response.equalsIgnoreCase("N")) {
						shopping = false;
						break;
					}else {
						System.out.print("Invalid. ");
					}
					
				}while(true);
			}
		}while(shopping);

		for (int i = 0; i < 56;i++) {
			outFile.print("-");
		}

		outFile.println();
		outFile.printf("%-42s%d\n","TOTAL # GEMS SPENT",(startGems-gems));
		outFile.printf("%-42s%d\n","# GEMS LEFT",gems);
		System.out.printf("You have %d gems left. %nThank for your purchases. Summary of your purchases is recorded in summary.txt",gems );
		//Closing outFile.
		outFile.close();
		
		
	}
	
}
