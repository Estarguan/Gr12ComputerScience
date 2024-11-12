package Assignment4;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Driver implements ActionListener {
    // To Do:
    // - I might hard code the locations of the stuff in input field to make it centered
    static ArrayList<Song> songs = new ArrayList<Song>();
    static ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    static JTextField inputField = new JTextField();
    static JButton submitButton = new JButton("Submit");
    static int option = 0;

    static JPanel panel = new JPanel();
    static JPanel menuPanel = new JPanel();
    static JPanel inputPanel = new JPanel();
    static JPanel displayPanel = new JPanel();
    static JScrollPane scrollPanel = new JScrollPane(displayPanel);
    static JScrollPane playlistScrollPane = new JScrollPane();
    
    //static DefaultListModel<String> listModel = new DefaultListModel<>();
    static ArrayList<String> listModel = new ArrayList<String>();
    
    static JList<String> playlistList;

    public Driver() {
    	
        // Setting up frames and panels
        JFrame frame = new JFrame("Playlist Manager Frame");
        frame.setPreferredSize(new Dimension(1200, 800));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        menuPanel.setLayout(new GridLayout(8, 1, 0, 0));

        // Making the menu panel options
        subMenuOne(menuPanel);

        // Making the input panel options
        inputField.setPreferredSize(new Dimension(300, inputField.getPreferredSize().height));
        inputField.setVisible(false); // Initially set inputField to be invisible
        inputPanel.add(inputField);
        
        //Adding the button for adding songs
        submitButton.setVisible(false);
        submitButton.addActionListener(this);
        submitButton.setActionCommand("submit");
        inputPanel.add(submitButton);

        for (int i = 0; i < playlists.size(); i++) {
        	listModel.add(playlists.get(i).getPlaylistName());
        }

        // Create a JList with playlist names
        String[] listToAdd = (String[]) listModel.toArray();
        JList<String> playlistList = new JList<>(listToAdd);
        playlistList.setPreferredSize(new Dimension(300,playlistList.getPreferredSize().height)	);
        playlistScrollPane = new JScrollPane(playlistList);
        inputPanel.add(playlistScrollPane);
        
        // Adding a border around my panels
        panel.setBorder(new LineBorder(Color.black, 1));
        menuPanel.setBorder(new LineBorder(Color.black, 1));
        inputPanel.setBorder(new LineBorder(Color.black, 1));
        displayPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black, 1), new EmptyBorder(10, 10, 10, 10)));
               
        panel.add(menuPanel);
        panel.add(inputPanel);
        panel.add(scrollPanel);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("opt1")) {
            displayAllLists();
        } else if (command.equals("opt2")) {
            displayOneList();
        } else if (command.equals("opt3")) {
            option = 3;
            inputField.setVisible(true); // Make the inputField visible
            inputField.setText(""); 
            submitButton.setVisible(true);
            inputPanel.revalidate(); // Apparently this does something
            inputPanel.repaint(); 
        } else if (command.equals("submit")) {
            if (option == 3) {
                try {
                    addList(inputField.getText());  
                    inputField.setText(""); 
                    JOptionPane.showMessageDialog(null, "Added Successfully",
                            "Playlist Added", JOptionPane.INFORMATION_MESSAGE);
                    inputField.setVisible(false); // Make the inputField visible
                    inputField.setText(""); 
                    submitButton.setVisible(false);
                    inputPanel.revalidate(); // Apparently this does something
                    inputPanel.repaint(); 
                } catch (IOException error) {
                    JOptionPane.showMessageDialog(null, "You somehow got an IOException IDK how you did that",
                            "File Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void displayAllLists() {
        displayPanel.removeAll();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        // Loop through each playlist and add its name to a JTextArea
        String playlistNames = "";
        for (Playlist playlist : playlists) {
            playlistNames += playlist.getPlaylistName()+"\n";
        }
        JTextArea textArea = new JTextArea(playlistNames);
        textArea.setEditable(false);  // Make it non-editable
        textArea.setLineWrap(true);   // Wrap lines if text is too long
        textArea.setWrapStyleWord(true);  // Wrap at word boundaries
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        displayPanel.add(textArea);
        displayPanel.add(Box.createVerticalStrut(10));  
        // Wrap displayPanel in a scroll pane and update panel layout
        scrollPanel = new JScrollPane(displayPanel);  

        // Remove the old scroll panel if present and add the updated scrollPanel
        panel.removeAll();
        panel.add(menuPanel);
        panel.add(inputPanel);
        panel.add(scrollPanel);

        // Refresh panel to apply updates
        panel.revalidate();
        panel.repaint();
    }


    public void displayOneList() {
        // Clear existing content in displayPanel
        displayPanel.removeAll();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        // Add updated playlist details directly to displayPanel
        for (Playlist playlist : playlists) {
            JTextArea textArea = new JTextArea(playlist.toString());
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            displayPanel.add(textArea);
            displayPanel.add(Box.createVerticalStrut(10)); // Add spacing between playlists
        }

        // Wrap displayPanel in a scroll pane and update panel layout
        scrollPanel = new JScrollPane(displayPanel); // Update scrollPanel

        // Remove the old scroll panel if present and add the updated scrollPanel
        panel.removeAll(); 
        panel.add(menuPanel); 
        panel.add(inputPanel);
        panel.add(scrollPanel); 

        // Refresh panel to apply updates
        panel.revalidate();
        panel.repaint();
    }

    public void addList(String fileName) throws IOException {
        boolean gettingFile = true;
        BufferedReader inFile = null;
        do {
            try {
                inFile = new BufferedReader(new FileReader(fileName));
                gettingFile = false;
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File Does Not Exist",
                        "File Error", JOptionPane.ERROR_MESSAGE);
                inFile.close();
                return; // Exit if file doesn't exist
            }
        } while (gettingFile);
        String title = inFile.readLine();
        int songNum = Integer.parseInt(inFile.readLine());
        for (int i = 0; i < songNum; i++) {
            String songTitle = inFile.readLine();
            String artist = inFile.readLine();
            String genre = inFile.readLine();
            double rating = Double.parseDouble(inFile.readLine());
            Time songTime = new Time(inFile.readLine());
            songs.add(new Song(songTitle, artist, genre, rating, songTime));
        }
        playlists.add(new Playlist(title, songNum, songs));
        for (int i = 0; i < 50;i++) {
        	listModel.add(title);        	
        }
        

        // Create a JList with playlist names
        String[] listToAdd = (String[]) listModel.toArray();
        playlistList.setListData(listToAdd);
        //playlistScrollPane =  new JScrollPane(playlistList);
        playlistList.revalidate();
        playlistList.repaint();
        
        inFile.close();
    }

    public void subMenuOne(JPanel menuPanel) {
        JButton opt1 = new JButton("Display a list of all your playlists");
        opt1.addActionListener(this);
        opt1.setActionCommand("opt1");
        JButton opt2 = new JButton("Display information of a particular playlist");
        opt2.addActionListener(this);
        opt2.setActionCommand("opt2");
        JButton opt3 = new JButton("Add a new playlist");
        opt3.addActionListener(this);
        opt3.setActionCommand("opt3");
        JButton opt4 = new JButton("Remove a playlist");
        opt4.addActionListener(this);
        opt4.setActionCommand("opt4");
        JButton opt5 = new JButton("Copy playlist");
        opt5.addActionListener(this);
        opt5.setActionCommand("opt5");
        JButton opt6 = new JButton("Sub playlist");
        opt6.addActionListener(this);
        opt6.setActionCommand("opt6");
        JButton opt7 = new JButton("Common Songs");
        opt7.addActionListener(this);
        opt7.setActionCommand("opt7");
        JButton opt8 = new JButton("Return to main menu");
        opt8.addActionListener(this);
        opt8.setActionCommand("opt8");
        menuPanel.add(opt1);
        menuPanel.add(opt2);
        menuPanel.add(opt3);
        menuPanel.add(opt4);
        menuPanel.add(opt5);
        menuPanel.add(opt6);
        menuPanel.add(opt7);
        menuPanel.add(opt8);
    }

    public static void main(String[] args) throws IOException {
        new Driver();
    }
}
