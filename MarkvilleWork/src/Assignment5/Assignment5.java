package Assignment5;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

//Estar Guan
//2024/12/06
//Assignment #5: Dynamic Data Structures with 2 Bonus


class Fraction implements Comparable<Fraction>{
	private int numerator;
	private int denominator;

	//Constructor method called whenever new Fraction is made to initialize numerator and denominator
	//Parameters: numerator and denominator for the fraction
	//Return: None
	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	//To String method called when printing a fraction. Prints fraction in the form num/den
	//Parameters: None
	//Return: String
	public String toString() {
		return String.format("%d/%d", this.numerator,this.denominator);
	}

	//Compare To method which defines our natural sort order, important for treesets to determine the sorting when adding
	//nodes. Also used to determine if two fractions are reduced equals of each other
	//Parameter: Fraction to compare
	//Return: Int
	public int compareTo(Fraction fraction) {
		if (this.numerator*fraction.denominator == this.denominator*fraction.numerator) {
			return 0;
		}else if(this.numerator*fraction.denominator < this.denominator*fraction.numerator) {
			return -1;
		}return 1;
	}
}

public class Assignment5 {

	//Main method which allows you to choose which question to pick.
	//Parameters: String[] args
	//Return: Void
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader inFile = new BufferedReader(new FileReader("assignment5Input.txt"));
		System.out.println("What question do you want to do?: (1,2,3)");
		int question = 0;
		do {
			try {
				question = Integer.parseInt(in.readLine());
				if (question < 1 || question > 3){
					throw new NumberFormatException();
				}
				break;
			}catch (NumberFormatException e) {
				System.out.println("Input a valid number");
			}
		}while(true);
		if (question == 1) {
			int testCases = Integer.parseInt(inFile.readLine());
			System.out.println("Finding the number of Substrings");
			for (int t = 0; t < testCases; t++) {
				String s = inFile.readLine();
				// Check if the input line is null
				if (s == null) {
					System.out.println("Encountered null input. Skipping this test case.");
					continue; // Skip processing this test case
				}

				else if (s.length() == 0) { //If string is empty
					System.out.println("String: " + s);
					System.out.println("No. of Substrings: " + "1");
				}

				else {
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
		}
		else if (question == 2) {
			int lowerLimNum, lowerLimDen, upperLimNum, upperLimDen;
			int n=-1;
			do{
				System.out.print("Enter the maximum denominator: ");
				try{
					n = Integer.parseInt(in.readLine());
				}catch (NumberFormatException e){
					System.out.println("Invalid numerator");
				}
			} while (n<0);
			do {
				System.out.print("Enter the lower limit: ");
				String lowerLimit = in.readLine();
				try {
					if (!lowerLimit.contains("/")){
						throw new NumberFormatException();
					}
					lowerLimNum = Integer.parseInt(lowerLimit.substring(0,lowerLimit.indexOf("/")));
					lowerLimDen = Integer.parseInt(lowerLimit.substring(lowerLimit.indexOf("/")+1));
					if (lowerLimNum < 0 || lowerLimDen < 0){
						throw new NumberFormatException();
					}
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
					if (!upperLimit.contains("/")){
						throw new NumberFormatException();
					}
					upperLimNum = Integer.parseInt(upperLimit.substring(0,upperLimit.indexOf("/")));
					upperLimDen = Integer.parseInt(upperLimit.substring(upperLimit.indexOf("/")+1));
					if (upperLimNum < 0 || upperLimDen < 0){
						throw new NumberFormatException();
					}

					if (upperLimNum > upperLimDen || upperLimDen == 0 || lowerLimNum*upperLimDen > lowerLimDen*upperLimNum) {
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
			int ans = mySet.subSet(lowerLimit,true, upperLimit,true).size();
			System.out.println("Total number of fractions: " + mySet.size());
			System.out.printf("Number of Fractions between %s and %s inclusive: %d", lowerLimit,upperLimit,ans);
		}else if (question == 3) {
			new Question3();
		}
		
	}
}


class Question3 extends JPanel implements ActionListener {
	Graphics offScreenBuffer;
	Image offScreenImage;
	int cardNum;
	JButton proveButton, submitButton, exitButton;
	JTextField inputField;
	boolean keepBottomRow = false;
	Image[] cards = new Image[25];
	int[] xCords = {50,150,250,350,450,550,650,750,850,950,1050,50,150,250,350,450,550,650,750,850,950,1050,50,150,250};
	int[] yCords = {100,100,100,100,100,100,100,100,100,100,100,200,200,200,200,200,200,200,200,200,200,200,300,300,300};
	Deque<Integer> dq = new LinkedList<>();

	//Constructor Method, initlize graphic properties as well as variables
	//Parameters: None
	//Return: None
	public Question3(){
		setPreferredSize(new Dimension(1200,800));
		setLayout(null);
		JFrame frame = new JFrame("Question3 Bonus");
		frame.add(this);
		frame.pack();
		frame.setVisible(true);

		//Get all the cardImages
		for (int i = 1; i<=25; i++){
			Image card = Toolkit.getDefaultToolkit().getImage("card"+i+".png");
			cards[i-1] = card.getScaledInstance(100,60,Image.SCALE_SMOOTH);
		}

		//Input the textField which takes in the number of cards
		inputField = new JTextField();
		inputField.setBounds(new Rectangle(350,10,100,40));

		//JLabel with the text "Card Number" next to the TextField
		JLabel cardNumberLabel = new JLabel("Card Number:");
		cardNumberLabel.setBounds(new Rectangle(200,7,150,50));
		cardNumberLabel.setFont(new Font("Arial", Font.BOLD, 18));

		//Submit button to submit Card number
		submitButton = new JButton("Submit");
		submitButton.setBounds(new Rectangle(500,10,100,40));
		submitButton.addActionListener(this);
		submitButton.setActionCommand("submit");

		//Prove button for the bonus
		proveButton = new JButton("Prove");
		proveButton.setBounds(new Rectangle(650,10,100,40));
		proveButton.addActionListener(this);
		proveButton.setActionCommand("prove");

		exitButton = new JButton("Exit");
		exitButton.setBounds(new Rectangle(800,10,100,40));
		exitButton.addActionListener(this);
		exitButton.setActionCommand("exit");

		//Adding JComponents
		this.add(inputField);
		this.add(cardNumberLabel);
		this.add(submitButton);
		this.add(proveButton);
		this.add(exitButton);

	}

	//Overridded paintComponent to draw on screen using graphics and offscreenbuffer
	//Parameters: Graphic g which is the class I use to draw
	//Return: Void
	public void paintComponent(Graphics g){


		//Set up the offscreen buffer the first time paint() is called
		if (offScreenBuffer == null){
			offScreenImage = createImage(this.getWidth(), this.getHeight());
			offScreenBuffer = offScreenImage.getGraphics();
		}
		super.paintComponent(g);
		if(!keepBottomRow){
			offScreenBuffer.setColor(Color.WHITE);
			offScreenBuffer.fillRect(0, 100, 1200, 700);
		}

		//Calls the overridden paintComponent to draw our labels and textFields.
		if (dq != null){
			Deque<Integer> copyDeque= new LinkedList<>(dq);
			for (int i = 0; i < dq.size(); i++){
				offScreenBuffer.drawImage(cards[copyDeque.pollFirst()-1],xCords[i],yCords[i],this);
			}

		}
		g.drawImage(offScreenImage, 0,0,this);
	}

	//This is the method that finds our original deck of cards but reversing the given algorithm
	//Parameters: None
	//Return: Void
	public void findOriginalDeck(){
		for (int i = cardNum; i>0;i--) {
			if (dq.size() > 1) {
				dq.offerFirst(dq.pollLast());
			}
			dq.offerFirst(i);
		}
		repaint();
	}

	//This method annimates the card as it goes down during the proof. THis method was SO SO SO HARD to write. To the point where I
	//Geuinely cannot find a way to annimate the card going from the front to the back of the deck, but I am not sure if
	//that is a requirement it just says animate cards
	//Parameters: None
	//Return: Void
		public void animateCards() {
			Graphics g = getGraphics();
			int completedCards = 0;

			// Store the final positions of static cards
			ArrayList<Integer> finalCards = new ArrayList<>();
			ArrayList<Point> finalCardPositions = new ArrayList<>();

			for (int i = 0; i < cardNum; i++) {
				// Get the moving card and update deque
				int movingCard = dq.pollFirst(); // Card to move
				int backingCard = 0;

				Deque<Integer> copyDeque = new LinkedList<>(dq); // Create a copy of dq for rendering

				// Rotate deque
				if (!dq.isEmpty()) {
					backingCard = dq.pollFirst();
				}

				// Start and end positions for the moving card
				int startX = xCords[0], startY = yCords[0];
				int endX = xCords[completedCards], endY = yCords[completedCards] + 350;

				// Animate the moving card
				while (startX != endX || startY != endY) {
					// Clear the canvas
					offScreenBuffer.setColor(Color.WHITE);
					offScreenBuffer.fillRect(0, 100, 1200, 700);

					// Redraw all completed cards
					for (int j = 0; j < finalCards.size(); j++) {
						int cardIndex = finalCards.get(j);
						Point position = finalCardPositions.get(j);
						offScreenBuffer.drawImage(cards[cardIndex - 1], position.x, position.y, this);
					}

					// Move the current card
					if (startX != endX) startX += (startX < endX) ? 5 : -5;
					if (startY != endY) startY += (startY < endY) ? 5 : -5;
					offScreenBuffer.drawImage(cards[movingCard - 1], startX, startY, this);

					// Redraw remaining cards in the deque
					try {
						if (!dq.isEmpty()) {
							int j = 0;
							for (Integer card : copyDeque) { // Use iterator instead of pollFirst
								offScreenBuffer.drawImage(cards[card - 1], xCords[j], yCords[j], this);
								j++;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					// Render the updated frame
					g.drawImage(offScreenImage, 0, 0, this);

					// Control animation speed
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// Start and end positions for the moving card
				startX = xCords[0]; startY = yCords[0];
				endX = xCords[dq.size()-1]; endY = yCords[dq.size()-1];

				// Animate the backing card moving from the front to the back of the deque
				startX = xCords[0];
				startY = yCords[0];
				endX = xCords[dq.size() - 1];
				endY = yCords[dq.size() - 1];

				while (startX != endX || startY != endY) {
					// Clear the canvas
					offScreenBuffer.setColor(Color.WHITE);
					offScreenBuffer.fillRect(0, 100, 1200, 700);

					// Redraw all completed cards
					for (int j = 0; j < finalCards.size(); j++) {
						int cardIndex = finalCards.get(j);
						Point position = finalCardPositions.get(j);
						offScreenBuffer.drawImage(cards[cardIndex - 1], position.x, position.y, this);
					}

					// Move the backing card
					if (startX != endX) startX += (startX < endX) ? 5 : -5;
					if (startY != endY) startY += (startY < endY) ? 5 : -5;
					offScreenBuffer.drawImage(cards[backingCard - 1], startX, startY, this);

					// Redraw remaining cards in the deque
					try {
						if (!dq.isEmpty()) {
							int j = 0;
							for (Integer card : copyDeque) { // Use iterator instead of pollFirst
								offScreenBuffer.drawImage(cards[card - 1], xCords[j], yCords[j], this);
								j++;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					// Render the updated frame
					g.drawImage(offScreenImage, 0, 0, this);

					// Control animation speed
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			// Add the backing card to the deque
				dq.addLast(backingCard);
				// Add the moving card to the list of static cards
				finalCards.add(movingCard);
				finalCardPositions.add(new Point(endX, endY));

				completedCards++;
			}

			// Final repaint to ensure all cards are in their correct positions
			repaint();
		}




	//Method overriden from the Actionlistner interface which waits for buttons to be pressed and acts accordingly
	//Parameters: Actionevent e which is just a variable that stores the event that occured
	//Return: Void
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("submit")){
			try{
				cardNum = Integer.parseInt(inputField.getText());
				inputField.setText("");
				if (cardNum>0 && cardNum <= 25) {
					dq.clear();
					keepBottomRow=false;
					findOriginalDeck();
				}else{
					throw new NumberFormatException();
				}
			}catch (NumberFormatException error) {
				JOptionPane.showMessageDialog(null, "Cannot get card num",
						"Card Num error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (command.equals("prove")){
			if (dq != null){
				submitButton.setEnabled(false);
				proveButton.setEnabled(false);
				animateCards();
				submitButton.setEnabled(true);
				proveButton.setEnabled(true);
			}else {
				JOptionPane.showMessageDialog(null, "No cards have been placed to prove",
						"Nothing to prove", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (command.equals("exit")){
			System.exit(0);
		}
	}
}
