package Assignment4;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class Driver implements ActionListener, ListSelectionListener{
    // To Do:
    // - I might hard code the locations of the stuff in input field to make it centered
    // - If I have no playlists we cant access any playlists
    // - make the globalvaraibles poublic later
    // - I am assuming I dont need to worry if the song TIme goes over 60 minutes because it just says (mm:ss) on the handout
    // - I am assuming the file Input will always have rating < 5

    //Estar Guan
    //November 12, 2024
    //This program allows for users to manage, view, access, and do many other cool features to a collection of music.
    //This program gives the user access to a list of many playlists all with their own properties as well as a list of songs with their own properies
    //Does Ms Wong Actually read this?
    public static ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    public static JTextField inputField = new JTextField(), numberField1 = new JTextField("0"), numberField2 = new JTextField("0");
    public static JButton submitButton = new JButton("Submit"), confirmButton = new JButton("Confirm");
    public static int option = 0;

    public static JPanel panel = new JPanel();
    public static JPanel menuPanel = new JPanel();
    public static JPanel inputPanel = new JPanel();
    public static JPanel displayPanel = new JPanel();
    public static JScrollPane scrollPanel = new JScrollPane(displayPanel);

    //static DefaultListModel<String> listModel = new DefaultListModel<>();
    public static DefaultListModel<String> listModel = new DefaultListModel<>(), songListModel = new DefaultListModel<>();
    public static JList<String> playlistList = new JList<>(listModel), songList = new JList<>(songListModel);
    public static JScrollPane playlistScrollPane = new JScrollPane(playlistList), songScrollPane = new JScrollPane(songList);

    public static JTextField songTitle = new JTextField("Enter Song Title");
    public static JTextField songGenre = new JTextField("Enter Song Genre");
    public static JTextField songArtist = new JTextField("Enter Song Artst");
    public static JTextField songRating= new JTextField("Enter the song rating (as a double)");
    public static JTextField songTime = new JTextField("Enter Time (mm:ss)");
    public static JButton confirmAddSong = new JButton("Confirm");
    public static JFrame addSongFrame = new JFrame("Add Song Frame");
    public static JPanel addSongPanel = new JPanel();;
    public static int selectedList = 0;

    //Making a global varaible to know which playlist we are accessing for subMenuTwo
    static Playlist menuTwoList;

    //Constructor of my class, runs when Driver is made
    //Parameters: None
    //Return: None
    public Driver() {

        // Setting up frames and panels
        JFrame frame = new JFrame("Playlist Manager Frame");
        panel.setPreferredSize(new Dimension(1200, 800));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        //These are the other panels for both subMenus
        menuPanel.setLayout(new GridLayout(8, 1, 0, 0));
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        menuPanel.setPreferredSize(new Dimension(1200/3,800));
        inputPanel.setPreferredSize(new Dimension(1200/3,800));
        displayPanel.setPreferredSize(new Dimension(1200/3,800));
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        //This is for my secondSubMenu to ensure that the actionLisnter calls don't repeat
        confirmAddSong.addActionListener(this);
        confirmAddSong.setActionCommand("confirmAddSong");
        submitButton.addActionListener(this);
        submitButton.setActionCommand("submit");
        confirmButton.addActionListener(this);
        confirmButton.setActionCommand("confirm");

        //Adding the submenuPanel2 panels adn frames
        addSongPanel.setPreferredSize(new Dimension(300,500));
        addSongFrame.add(addSongPanel);
        addSongFrame.pack();
        addSongFrame.setVisible(false);

        mainMenu();

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    //SubMenuOne first option, displays a list of all your playlists on the right of the screen
    //Parameters:None
    //Return Void
    public void displayAllLists() {
        displayPanel.removeAll();

        // Loop through each playlist and add its name to a JTextArea
        String playlistNames = "";
        for (int i = 0; i <playlists.size();i++){
            playlistNames += (i+1) + ": " + playlists.get(i).getPlaylistName()+"\n";
        }
        JTextArea textArea = new JTextArea(playlistNames);
        textArea.setEditable(false);  // Make it non-editable
        textArea.setLineWrap(true);   // Wrap lines if text is too long
        textArea.setWrapStyleWord(true);  // Wrap at word boundaries
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        displayPanel.add(textArea);
        displayPanel.add(Box.createVerticalStrut(10));

        // Remove the old scroll panel if present and add the updated scrollPanel
//        panel.removeAll();
//        panel.add(menuPanel);
//        panel.add(inputPanel);
//        panel.add(scrollPanel);

        // Refresh panel to apply updates
        panel.revalidate();
        panel.repaint();
    }

    //SubMenuOne option 2, dispalys more detailed data of one single playlist on the right of your screen
    //Params: None
    //Return: Void
    public void displayOneList() {

        int displayIndex = playlistList.getSelectedIndex();
        if (displayIndex == -1){
            JOptionPane.showMessageDialog(null, "No Playlist Selected",
                    "Can't Display", JOptionPane.ERROR_MESSAGE);
        }else{
            displayPanel.removeAll();
            JTextArea textArea = new JTextArea(playlists.get(displayIndex).toString());
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            displayPanel.add(textArea);
            displayPanel.add(Box.createVerticalStrut(10)); // Add spacing between playlists

        }


//        panel.removeAll();
//        panel.add(menuPanel);
//        panel.add(inputPanel);
//        panel.add(scrollPanel);

        // Refresh panel to apply updates
        panel.revalidate();
        panel.repaint();
    }

    //SubMenuOne Option 3, Allows you to add a playlist which can be managed later on
    //Parameters: String fileName, the name of the file we want to add must be closed after
    //Return: Void
    public void addList(String fileName) throws IOException {
        boolean gettingFile = true;
        ArrayList<Song> songs = new ArrayList<>();

        BufferedReader inFile = null;
        do {
            try {
                inFile = new BufferedReader(new FileReader(fileName));
                gettingFile = false;
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File Does Not Exist",
                        "File Error", JOptionPane.ERROR_MESSAGE);
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

        //Updating our JList

        listModel.addElement(playlists.size() + ": "  + title);

        playlistList.revalidate();
        playlistList.repaint();

        inFile.close();
    }

    //SubMenuOne Option 4, Removes a playlist and its data permanently so we can't use it anymore
    //Parameters: None
    //Return: Void
    public void removeList(){
        int removeIdx = playlistList.getSelectedIndex();
        if (removeIdx == -1){
            JOptionPane.showMessageDialog(null, "No Playlist Selected",
                    "Can't Remove", JOptionPane.ERROR_MESSAGE);
        }else{
            playlists.remove(removeIdx);
            listModel.clear();
            //This is to reupdate the indexes in front of each list element
            for (int i = 0; i <playlists.size();i++){
                listModel.addElement( (i+1) + ": " + playlists.get(i).getPlaylistName());
            }
            playlistList.revalidate();
            playlistList.repaint();
        }
    }

    //SubMenuOne Option 5, makes a new playlist with the word copy in front. Copys the data from anotehr playlists exactly
    //Parameters:None
    //Return: Void
    public void copyList(){
        int copyIndex = playlistList.getSelectedIndex();
        if (copyIndex == -1) {
            JOptionPane.showMessageDialog(null, "No Playlist Selected",
                    "Can't Copy", JOptionPane.ERROR_MESSAGE);
        }else{
            Playlist copiedList = new Playlist("copy" + playlists.get(copyIndex).getPlaylistName(),playlists.get(copyIndex).getSongNum(),playlists.get(copyIndex).getSongs());
            playlists.add(copiedList);
            listModel.addElement(playlists.size() + ": copy"  + playlists.get(copyIndex).getPlaylistName());
            playlistList.revalidate();
            playlistList.repaint();
        }
    }
    //SubMenuOne: Option6, makes a new playlist with the word sub in front. The palylist includes a range of songs from a differebnt playlist
    //Parameters:None
    //Return: Void
    public void subList(){
        int subListIndex = playlistList.getSelectedIndex();
        if (subListIndex == -1) {
            JOptionPane.showMessageDialog(null, "No Playlist Selected",
                    "Can't Make Sublist", JOptionPane.ERROR_MESSAGE);
        }else{
            numberField2.setText("0");
            numberField1.setText("0");
            String allSongs = playlists.get(subListIndex).displayAllSongs();
            displayPanel.removeAll();
            //System.out.println(allSongs);
            JTextArea textArea = new JTextArea(allSongs);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            displayPanel.add(textArea);
            displayPanel.add(Box.createVerticalStrut(10)); // Add spacing between playlists
            panel.revalidate();
            panel.repaint();

            numberField1.setVisible(true);
            numberField2.setVisible(true);
            confirmButton.setVisible(true);
        }
    }

    //An addon to subList(), this method runs after the confirm button is hit to finalize our subplaylists
    //Parameters: None
    //Return: Void
    public void confirmSub(){
        int subListIndex = playlistList.getSelectedIndex();
        int startIdx=0,endIdx=0;
        try {
            startIdx = Integer.parseInt(numberField1.getText());
            endIdx = Integer.parseInt(numberField2.getText());
        } catch (NumberFormatException ex) {}
        if (startIdx <= 0 || startIdx > playlists.get(subListIndex).getSongNum() || endIdx <= 0 || endIdx > playlists.get(subListIndex).getSongNum() || endIdx<startIdx){
            JOptionPane.showMessageDialog(null, "Invalid Numbers",
                    "Can't Make Sublist", JOptionPane.ERROR_MESSAGE);
        }else{

            ArrayList<Song> subListSongs = new ArrayList<>();
            for (int i = startIdx - 1; i < endIdx; i++) {
                subListSongs.add(playlists.get(subListIndex).getSongs().get(i));
            }
            playlists.add(new Playlist("sub" + playlists.get(subListIndex).getPlaylistName(), subListSongs.size(), subListSongs));
            listModel.addElement(playlists.size() + ": " + "sub" + playlists.get(subListIndex).getPlaylistName());
            playlistList.revalidate();
            playlistList.repaint();
            numberField1.setVisible(false);
            numberField2.setVisible(false);
            confirmButton.setVisible(false);
        }
        numberField1.setText("0");
        numberField2.setText("0");
    }

    //SubMenuOne: Option 7, checks what songs are common between two playlists and displays all the songs on the right of your screen
    //Parameters: None
    //Return: Void
    public void commonList(){
        int commonIndex = playlistList.getSelectedIndex();
        if (commonIndex == -1){
            JOptionPane.showMessageDialog(null, "No Playlist Selected",
                    "Can't find common songs", JOptionPane.ERROR_MESSAGE);
        }else{
            numberField1.setVisible(true);
            numberField1.setText("0");
            confirmButton.setVisible(true);
        }
    }

    //Add on to commonList(), runs when the confiurm button is pressed to finalize our check common songs method
    //Parameters: None
    //Return: Void
    public void confirmCommon(){
        int commonIndex1 = playlistList.getSelectedIndex();
        int commonIndex2 = 0;
        try {
            commonIndex2 = Integer.parseInt(numberField1.getText());
        } catch (NumberFormatException ex) {}
        if (commonIndex2 <= 0 || commonIndex2 > playlists.size()){
            JOptionPane.showMessageDialog(null, "Invalid Numbers",
                    "Can't Make Sublist", JOptionPane.ERROR_MESSAGE);
        }else{
            String sameSongs = "";
            ArrayList<Song> songList1;
            ArrayList<Song> songList2;
            //Ensure I loop the longer list
            if (playlists.get(commonIndex1).getSongNum() > playlists.get(commonIndex2-1).getSongNum()) {
                songList1 = playlists.get(commonIndex1).getSongs();
                songList2 = playlists.get(commonIndex2-1).getSongs();

            }else{
                songList1 = playlists.get(commonIndex2-1).getSongs();
                songList2 = playlists.get(commonIndex1).getSongs();
            }
            int num = 1;
            for (int i = 0; i < songList1.size(); i++){
                Song curSong = new Song(songList1.get(i).getTitle(),"","",0.0,new Time("0:00"));

                if (songList2.contains(curSong)){
                    sameSongs += num + ": " + songList1.get(i).getTitle() + "\n";
                    num+=1;
                }
//                int index = Collections.binarySearch(songList2, curSong);
//                System.out.println(index);
//                if (index>=0) {
//                    sameSongs += num + ": " + songList1.get(i).getTitle() + "\n";
//                }
            }
            if (sameSongs.isEmpty())sameSongs = "No Equal Songs";
            displayPanel.removeAll();

            JTextArea textArea = new JTextArea(sameSongs);
            textArea.setEditable(false);  // Make it non-editable
            textArea.setLineWrap(true);   // Wrap lines if text is too long
            textArea.setWrapStyleWord(true);  // Wrap at word boundaries
            textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            displayPanel.add(textArea);
            displayPanel.add(Box.createVerticalStrut(10));
            panel.revalidate();
            panel.repaint();
        }
    }

    //SubMenuTwo option one displays all the songs in a playlist on the right of your screen
    //Parameters: None
    //Return: Void
    public void displayAllSongs(){
        displayPanel.removeAll();
        JTextArea textArea = new JTextArea(menuTwoList.displayAllSongs());
        textArea.setEditable(false);  // Make it non-editable
        textArea.setLineWrap(true);   // Wrap lines if text is too long
        textArea.setWrapStyleWord(true);  // Wrap at word boundaries
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.add(textArea);
        displayPanel.add(Box.createVerticalStrut(10));
        panel.revalidate();
        panel.repaint();
    }

    //SubMenuTwo: option two displays more detailed information of just one song on the right
    //Parameters: None
    //Return: Void
    public void displayOneSong(){
        displayPanel.removeAll();
        int displayIndex = songList.getSelectedIndex();
        if (displayIndex == -1){
            JOptionPane.showMessageDialog(null, "No Song Selected",
                    "Can't access Song", JOptionPane.ERROR_MESSAGE);
        }else{
            JTextArea textArea = new JTextArea(menuTwoList.getSongs().get(displayIndex).toString());
            textArea.setEditable(false);  // Make it non-editable
            textArea.setLineWrap(true);   // Wrap lines if text is too long
            textArea.setWrapStyleWord(true);  // Wrap at word boundaries
            textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            displayPanel.add(textArea);
            displayPanel.add(Box.createVerticalStrut(10));
            panel.revalidate();
            panel.repaint();
        }
    }

    //SubMenu Two Option 3. Allows us to add a song to an existing playlist, this song can be seen from other submenus too
    //Parameters: None
    //Return: Void
    public void addSong(){
        addSongPanel.removeAll();
        addSongPanel.setLayout(new BoxLayout(addSongPanel,BoxLayout.Y_AXIS));
        addSongPanel.add(songTitle);
        addSongPanel.add(songArtist);
        addSongPanel.add(songGenre);
        addSongPanel.add(songRating);
        addSongPanel.add(songTime);
        addSongPanel.add(confirmAddSong);

        addSongPanel.revalidate();
        addSongPanel.repaint();
        addSongFrame.setVisible(true);
    }

    //SubMenu two, option 4. ALlows us to delete a song from a playlist 4 different ways. Changes will affect other subMenus
    //Parameters: None
    //Return: Void
    public void removeSong(){
        addSongPanel.removeAll();
        addSongPanel.setLayout(new GridLayout(1,1));
        //addSongPanel.setLayout(new GridLayout(2,2,10,10));
        String[] options = {"Song Number", "Song Title", "First Song", "Song Range"};
        JList<String> removeOptions = new JList<>(options);
        removeOptions.setFont(new Font("Arial", Font.PLAIN, 30));
        removeOptions.addListSelectionListener(this);
        removeOptions.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black, 1), new EmptyBorder(30, 30, 30, 30)));
        addSongPanel.add(removeOptions);
        addSongPanel.revalidate();
        addSongPanel.repaint();
        addSongFrame.setVisible(true);
//        JButton remove1 = new JButton("Song Number #");
//
//        JButton remove2 = new JButton("Song Title");
//        JButton remove3 = new JButton("First Song");
//        JButton remove4 = new JButton("Song Range");

    }

    //SubMenu two option, allows us to sort the songs of a playlist 3 different ways. Changes will affect other submenus
    //Parameters: None
    //Return: Void
    public void sortSong(){
        addSongPanel.removeAll();
        addSongPanel.setLayout(new GridLayout(1,1));
        String[] options = {"By Title", "By Artist", "By Time"};
        JList<String> sortOptions = new JList<>(options);
        sortOptions.setFont(new Font("Arial", Font.PLAIN, 30));
        sortOptions.addListSelectionListener(this);
        sortOptions.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black, 1), new EmptyBorder(30, 30, 30, 30)));
        addSongPanel.add(sortOptions);
        addSongPanel.revalidate();
        addSongPanel.repaint();
        addSongFrame.setVisible(true);
    }
    //Add on to the addSong(), runs when button confirm is pressed to finalize added song
    //Parameters: None
    //Return: void
    public void addSongConfirm(){
        String title = "",genre="",artist="",time="";
        double rating=0.0;
        try{
            title = songTitle.getText();
            artist = songArtist.getText();
            genre = songGenre.getText();
            rating = Double.parseDouble(songRating.getText());
            time = songTime.getText();

            int colonIndex = time.indexOf(":");
            if (colonIndex == -1 || time.indexOf(":", colonIndex + 1) != -1) {
                throw new NumberFormatException();
            }
            int minutes = Integer.parseInt( time.substring(0, colonIndex));
            int seconds = Integer.parseInt(time.substring(colonIndex + 1));
            if (minutes < 0 || minutes >= 60 || seconds < 0 || seconds >= 60 || (minutes == 0&&seconds == 0)) {
                throw new NumberFormatException("Minutes or seconds are out of valid range (0-59).");
            }
            if (rating <= 5 && rating >= 0 && !title.isEmpty() && !genre.isEmpty() && !artist.isEmpty() && !(minutes == 0 &&seconds == 0)){
                Song newSong = new Song(title,artist,genre,rating,new Time(time));
                menuTwoList.addSong(newSong);

                songListModel.clear();
                for (int i = 0; i < menuTwoList.getSongNum();i++){
                    songListModel.addElement((i+1) + ": " + menuTwoList.getSongs().get(i).getTitle());
                }
                panel.revalidate();
                panel.repaint();
            }else{
                throw new NumberFormatException();
            }
            songTitle.setText("Enter Song Title");
            songArtist.setText("Enter Song Artist");
            songGenre.setText("Enter Song Genre");
            songRating.setText("Enter the song rating (as a double)");
            songTime.setText("Enter Time (mm:ss");
            addSongFrame.setVisible(false);
        }catch (NumberFormatException ex){

            JOptionPane.showMessageDialog(null, "Invalid Inputs",
                "Invalid inputs", JOptionPane.ERROR_MESSAGE);


            songTitle.setText("Enter Song TItle");
            songArtist.setText("Enter Song Artist");
            songGenre.setText("Enter Song Genre");
            songRating.setText("Enter the song rating (as a double)");
            songTime.setText("Enter Time (mm:ss)");
            addSongFrame.setVisible(false);
        }
    }

    //Adjusts the JPanels and Frames to show the stuff needed for the main Menu
    //Paratmers: NOne
    //Return: Void
    public void mainMenu(){

        //Clear all panels before entering subMenus
        menuPanel.removeAll();
        inputPanel.removeAll();
        displayPanel.removeAll();


        JButton subMenu1 = new JButton("Accessing your playlists");
        subMenu1.addActionListener(this);
        subMenu1.setActionCommand("SubMenu1");
        JButton subMenu2 = new JButton("Accessing within a particular playlist");
        subMenu2.addActionListener(this);
        subMenu2.setActionCommand("SubMenu2");
        JButton exit = new JButton("Exit");
        exit.addActionListener(this);
        exit.setActionCommand("Exit");
        panel.add(subMenu1);
        panel.add(subMenu2);
        panel.add(exit);

        panel.revalidate();
        panel.repaint();
    }

    //ADjusts the panel to let you choose a playlist before subMenu2 begins
    //Paramters: None
    //Return: None
    public void chooseListPanel(){
        panel.add(playlistList);
        JButton chooseListButton = new JButton("Confirm List");
        chooseListButton.addActionListener(this);
        chooseListButton.setActionCommand("ListConfirm");
        JButton backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setActionCommand("Back");
        panel.add(chooseListButton);
        panel.add(backButton);
        panel.revalidate();
        panel.repaint();
    }

    //Adjusts the panels and frames to display subMenuTwo
    //Paramters:None
    //Return: Void
    public void subMenuTwoPanel(){
        panel.removeAll();

        //Adding the buttons
        subMenuTwo();


        //Adding the hidden buttons
        inputField.setVisible(false);
        submitButton.setVisible(false);
        inputPanel.add(inputField);
        inputPanel.add(submitButton);
        //Adding the song list

        songListModel.clear();
        for (int i = 0; i < menuTwoList.getSongNum();i++){
            songListModel.addElement((i+1) + ": " + menuTwoList.getSongs().get(i).getTitle());
        }

        songScrollPane.setPreferredSize(new Dimension(400,600));
        inputPanel.add(songScrollPane);

        // Adding a border around my panels
        panel.setBorder(new LineBorder(Color.black, 1));
        menuPanel.setBorder(new LineBorder(Color.black, 1));
        inputPanel.setBorder(new LineBorder(Color.black, 1));
        displayPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black, 1), new EmptyBorder(10, 10, 10, 10)));

        panel.add(menuPanel);
        panel.add(inputPanel);
        panel.add(scrollPanel);

        panel.revalidate();
        panel.repaint();
    }

    //Adjusts the frames and panels to show SubMenuOne
    //Parameters: None
    //Return: Void
    public void subMenuOnePanel(){
        // Making the menu panel options
        subMenuOne();

        // Making the input panel options
        inputField.setPreferredSize(new Dimension(300, 50));
        inputField.setVisible(false); // Initially set inputField to be invisible
        inputPanel.add(inputField);

        //Adding the button for adding songs
        submitButton.setVisible(false);
        inputPanel.add(submitButton);

        // Create a JList with playlist names
        //playlistScrollPane = new JScrollPane(playlistList);

        playlistList.setModel(listModel); // Ensure model is updated
        playlistScrollPane.setViewportView(playlistList); // Make sure JList is added to JScrollPane

        playlistScrollPane.setPreferredSize(new Dimension(400,600));
        //playlistList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);    This thing never worked
        inputPanel.add(playlistScrollPane);

        //Add my number adjusters for my Sub Panel option
        numberField1.setPreferredSize(new Dimension(50,50));
        numberField2.setPreferredSize(new Dimension(50,50));


        numberField1.setVisible(false);
        numberField2.setVisible(false);
        confirmButton.setVisible(false);
        inputPanel.add(numberField1);
        inputPanel.add(numberField2);
        inputPanel.add(confirmButton);


        // Adding a border around my panels
        panel.setBorder(new LineBorder(Color.black, 1));
        menuPanel.setBorder(new LineBorder(Color.black, 1));
        inputPanel.setBorder(new LineBorder(Color.black, 1));
        displayPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black, 1), new EmptyBorder(10, 10, 10, 10)));

        panel.add(menuPanel);
        panel.add(inputPanel);
        panel.add(scrollPanel);

        panel.revalidate();
        panel.repaint();
    }

    //Adjusts panels and frames to show subMenu two and mainly add all its buttons
    //Parameters: None
    //Return Void
    public void subMenuTwo(){
        JButton opt9 = new JButton("Display all songs (in the last sorted order)");
        opt9.addActionListener(this);
        opt9.setActionCommand("opt9");
        JButton opt10 = new JButton("Display information on a particular song");
        opt10.addActionListener(this);
        opt10.setActionCommand("opt10");
        JButton opt11 = new JButton("Add a song");
        opt11.addActionListener(this);
        opt11.setActionCommand("opt11");
        JButton opt12 = new JButton("Remove a song (4 options)");
        opt12.addActionListener(this);
        opt12.setActionCommand("opt12");
        JButton opt13 =  new JButton("Sort songs (3 options)");
        opt13.addActionListener(this);
        opt13.setActionCommand("opt13");
        JButton opt14 = new JButton("Return back to main menu");
        opt14.addActionListener(this);
        opt14.setActionCommand("opt14");
        menuPanel.add(opt9);
        menuPanel.add(opt10);
        menuPanel.add(opt11);
        menuPanel.add(opt12);
        menuPanel.add(opt13);
        menuPanel.add(opt14);
    }

    //Adjusts frames and panels to show subMenuOne mainly adding the buttons
    //Parameters: None
    //Return: None
    public void subMenuOne() {
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

    //Main Method, ruins the constructor
    //Parameters: String[] args, its always there
    //Return: Void
    public static void main(String[] args) throws IOException {
        new Driver();
    }

    //Implmeneted method for actionListner reacts whjen I press my buttons and behaves accordingly
    //Parameters: ActionEvent e, the actual event that happens carries teh action Command in it
    //Reuturn: Void
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("SubMenu1")){
            panel.removeAll();
            subMenuOnePanel();
        }
        else if (command.equals("SubMenu2")){
            panel.removeAll();
            chooseListPanel();
            //subMenuTwoPanel();
        }else if (command.equals("Exit")){
            System.exit(0);
        }else if (command.equals("Back")){
            panel.removeAll();
            mainMenu();
        }
        else if (command.equals("ListConfirm")){
            int confirmIndex = playlistList.getSelectedIndex();
            if (confirmIndex == -1){
                JOptionPane.showMessageDialog(null, "No Playlist Selected",
                        "Can't access playlist", JOptionPane.ERROR_MESSAGE);
            }else{
                String name = playlists.get(confirmIndex).getPlaylistName();
                int songNum = playlists.get(confirmIndex).getSongNum();
                ArrayList<Song> songs = playlists.get(confirmIndex).getSongs();
                selectedList = confirmIndex;
                menuTwoList = new Playlist(name,songNum,songs);
                subMenuTwoPanel();
            }
        }
        else if (command.equals("opt1")) {
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
        }else if (command.equals("opt4")){
            //To make sure you dont try to sub or common from a deleted list
            option = 0;
            numberField1.setText("0");
            numberField2.setText("0");
            numberField1.setVisible(false);
            numberField2.setVisible(false);
            confirmButton.setVisible(false);
            removeList();
        }else if (command.equals("opt5")){
            copyList();
        }
        else if (command.equals("opt6")){
            option = 6;
            subList();
        }else if (command.equals("opt7")){
            option = 7;
            numberField2.setVisible(false);
            commonList();
        }else if (command.equals("opt8")){
            panel.removeAll();
            option = 0;
            mainMenu();
        }

        else if (command.equals("opt9")){
            displayAllSongs();
        }
        else if (command.equals("opt10")){
            displayOneSong();
        }
        else if (command.equals("opt11")){
            addSong();
        }else if (command.equals("opt12")){
            removeSong();
        }
        else if (command.equals("opt13")){
            sortSong();
        }else if (command.equals("opt14")){
            panel.removeAll();
            option = 0;
            playlists.set(selectedList,menuTwoList);
            mainMenu();
        }
        else if (command.equals("submit")) {
            if (option == 3) {
                try {
                    addList(inputField.getText());
                    inputField.setText("");
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
        }else if (command.equals("confirm")){
            if (option == 6){
                confirmSub();
            }else if (option == 7){
                confirmCommon();
            }
        }else if (command.equals("confirmAddSong")){
            addSongConfirm();
        }
    }

    //    JTextField songTitle = new JTextField("Enter Song Title");
//    JTextField songGenre = new JTextField("Enter Song Genre");
//    JTextField songArtist = new JTextField("Enter Song Artst");
//    JTextField songRating= new JTextField("Enter the song rating (as a double)");
//    JTextField time = new JTextField("Enter Time (mm:ss)");
//    JButton confirmAddSong = new JButton("Confirm");

    //Implemented method from the ListSelectionLisntner interface, reacts when I click a list item/button
    //Parameters: ListSelectionEvent e, thje actual event that took place when I pressed the button
    //Return: VOid
    @Override
    public void valueChanged(ListSelectionEvent e) {

        //I stole this off the internet again Idk if this is how it should be done
        // Check if the event is not part of an adjustment sequence
        if (!e.getValueIsAdjusting()) {
            // Cast the source of the event to JList
            JList<?> list = (JList<?>) e.getSource();

            // Get the selected value from the list
            String selectedOption = (String) list.getSelectedValue();


            if(selectedOption.equals("Song Number")){
                String input1 = JOptionPane.showInputDialog(null, "Enter the song number:",
                        "Song Number Input", JOptionPane.PLAIN_MESSAGE);
                if (input1!=null){
                    int songIdx = 0;
                    try{
                        songIdx = Integer.parseInt(input1);
                        menuTwoList.removeSong(songIdx-1);
                        //displayAllSongs();
                        songListModel.clear();
                        for (int i = 0; i < menuTwoList.getSongNum();i++){
                            songListModel.addElement((i+1) + ": " + menuTwoList.getSongs().get(i).getTitle());
                        }
                        panel.revalidate();
                        panel.repaint();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Numbers",
                                "Can't Remove", JOptionPane.ERROR_MESSAGE);
                    }
                }
                addSongFrame.setVisible(false);

            }else if (selectedOption.equals("Song Title")) {

                String input2 = JOptionPane.showInputDialog(null, "Enter the song title:",
                        "Song Number Input", JOptionPane.PLAIN_MESSAGE);
                if (input2!= null) {
                    ArrayList<Song> searchList = new ArrayList<>(menuTwoList.getSongs());
                    Collections.sort(searchList);
                    int idx = Collections.binarySearch(searchList, new Song(input2, "", "", 0.0, new Time("00:00")));
                    while (idx >=0 ){
                        menuTwoList.removeSong(menuTwoList.getSongs().indexOf(new Song(input2, "", "", 0.0, new Time("00:00"))));
                        searchList.remove(idx);
                        //menuTwoList.removeSong(idx);
                        songListModel.clear();
                        for (int i = 0; i < menuTwoList.getSongNum(); i++) {
                            songListModel.addElement((i + 1) + ": " + menuTwoList.getSongs().get(i).getTitle());
                        }
                        panel.revalidate();
                        panel.repaint();
                        addSongFrame.setVisible(false);
                        idx = Collections.binarySearch(searchList, new Song(input2, "", "", 0.0, new Time("00:00")));
                    }
                    //displayAllSongs();
                }
                addSongFrame.setVisible(false);

            }else if (selectedOption.equals("First Song")) {
                System.out.println("ran");
                menuTwoList.removeSong(0);
                //displayAllSongs();
                songListModel.clear();
                for (int i = 0; i < menuTwoList.getSongNum(); i++) {
                    songListModel.addElement((i + 1) + ": " + menuTwoList.getSongs().get(i).getTitle());
                }

                panel.revalidate();
                panel.repaint();
                addSongFrame.setVisible(false);
            }else if (selectedOption.equals("Song Range")) {
                //Once again stole this off the internet.... Got a lil desperate but it works
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                // Create the first input field and label
                JLabel label1 = new JLabel("Enter first input:");
                JTextField field1 = new JTextField(10);
                panel.add(label1);
                panel.add(field1);

                // Create the second input field and label
                JLabel label2 = new JLabel("Enter second input:");
                JTextField field2 = new JTextField(10);
                panel.add(label2);
                panel.add(field2);
                // Show the JOptionPane with the panel
                int result = JOptionPane.showConfirmDialog(null, panel, "Dual Input Dialog",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try{
                        int start = Integer.parseInt(field1.getText());
                        int end = Integer.parseInt(field2.getText());

                        if (start <= 0 || start > menuTwoList.getSongs().size() || end <= 0 || end > menuTwoList.getSongs().size() || end<start){
                            throw new NumberFormatException();
                        }
                        for (int i = end - 1; i >= start-1; i--) {
                            menuTwoList.removeSong(i);
                        }
                        songListModel.clear();
                        for (int i = 0; i < menuTwoList.getSongNum(); i++) {
                            songListModel.addElement((i + 1) + ": " + menuTwoList.getSongs().get(i).getTitle());
                        }
                        panel.revalidate();
                        panel.repaint();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Range",
                                "Can't Remove", JOptionPane.ERROR_MESSAGE);
                    }

                }
                addSongFrame.setVisible(false);

            }else if (selectedOption.equals("By Title")){
                Collections.sort(menuTwoList.getSongs());
                //displayAllSongs();
                songListModel.clear();
                for (int i = 0; i < menuTwoList.getSongNum();i++){
                    songListModel.addElement((i+1) + ": " + menuTwoList.getSongs().get(i).getTitle());
                }
                panel.revalidate();
                panel.repaint();
                addSongFrame.setVisible(false);
            }else if (selectedOption.equals("By Artist")){
                Collections.sort(menuTwoList.getSongs(),new SortByArtist());
                displayAllSongs();
                songListModel.clear();
                for (int i = 0; i < menuTwoList.getSongNum();i++){
                    songListModel.addElement((i+1) + ": " + menuTwoList.getSongs().get(i).getTitle());
                }
                panel.revalidate();
                panel.repaint();
                addSongFrame.setVisible(false);
            }else if (selectedOption.equals("By Time")){
                Collections.sort(menuTwoList.getSongs(),new SortByTime());
                displayAllSongs();
                songListModel.clear();
                for (int i = 0; i < menuTwoList.getSongNum();i++){
                    songListModel.addElement((i+1) + ": " + menuTwoList.getSongs().get(i).getTitle());
                }
                panel.revalidate();
                panel.repaint();
                addSongFrame.setVisible(false);
            }
        }
    }
}
