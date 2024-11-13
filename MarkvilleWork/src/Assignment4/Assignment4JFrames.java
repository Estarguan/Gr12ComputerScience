//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.*;
//import java.util.*;
//import java.awt.*;
//import javax.swing.*;
//
//public class Assignment4JFrames extends JFrame implements ActionListener{
//    //Global variables and JComponents
//    public static ArrayList <Playlist> playlists = new ArrayList<>();
//    public static String title, artist, genre, time;
//    public static int playlistNumber,  subPlaylistChoice = -1, rating = -1, addSongCounter = 1, removeSongChoice = -1, sortChoice = -1;
//    public static JFrame frame;
//    public static JPanel mainMenuPanel, submenuOnePanel, submenuTwoChoosePlaylist, submenuTwoPanel;
//    public static JLabel mainTitle, sub1Title, output1, sub2Title, output2, playlistList;
//    public static JScrollPane outputPane1, outputPane2;
//    public static JTextField inputField1, inputField2, choosePlaylist;
//    public static JButton mainB1, mainB2, mainB3, sub1B1, sub1B2, sub1B3, sub1B4, sub1B5, sub1B6, sub1B7, sub1B8 ,sub2B1, sub2B2, sub2B3, sub2B4, sub2B5, sub2B6, confirmPlaylist;
//    //actionPerformed method from ActionListener interface
//    public void actionPerformed(ActionEvent e) {
//        //Turn ActionEvent into String
//        String event = e.getActionCommand();
//        switch (event) {
//            case "Main1" -> {
//                //Swap panels inside frame
//                frame.getContentPane().removeAll();
//                frame.getContentPane().add(submenuOnePanel);
//                frame.getContentPane().revalidate();
//                frame.getContentPane().repaint();
//            }
//            case "Exit" -> System.exit(0); //End program
//            case "Sub11" -> output1.setText(displayAllPlaylists()); //Displays all playlists
//            case "Sub12" -> {
//                //Get user input
//                int index = getInputP(inputField1);
//                //Display playlist
//                if (index!=-1) {
//                    displayPlaylist(index);
//                }
//            }
//            case "Sub13" -> {
//                //Try and catch in case file does not exist or is corrupted
//                try {
//                    //Get user input and add it to playlists
//                    addPlaylist(inputField1.getText());
//                } catch (IOException invalid) {
//                    inputField1.setText("File not found. ");
//                }
//            }
//            case "Sub14" -> {
//                //Get user input
//                int index = getInputP(inputField1);
//                //If input is valid remove playlist
//                if (index!=-1) {
//                    removePlaylist(index);
//                }
//            }
//            case "Sub15" -> {
//                //Get user input
//                int index = getInputP(inputField1);
//                //If index is valid, make a copy of that playlist
//                if (index != -1) {
//                    copyPlaylist(index);
//                }
//            }
//            case "Sub16" -> {
//                //Get user input and trim to help with validating input
//                String input = inputField1.getText().trim();
//
//                //If this is user's first time clicking the button
//                if (subPlaylistChoice == -1) {
//                    try {
//                        //Get user input of which playlist they want to make a sub copy for
//                        subPlaylistChoice = Integer.parseInt(input)-1;
//                        //Check if this number is valid
//                        if (subPlaylistChoice<0||subPlaylistChoice>=playlists.size()) {
//                            throw new NumberFormatException();
//                        }
//                        //Ask for next input
//                        inputField1.setText("Replace this text with 2 song numbers separated by a space inclusive of the range you want to make sub playlist of and then press 6) again: ");
//                        //Display songs in the playlist
//                        output1.setText(playlists.get(subPlaylistChoice).displayAllSongs());
//                    } catch (NumberFormatException invalid) {
//                        //If user input is invalid tell them to try again
//                        inputField1.setText("Invalid input, try again. ");
//                        //Reset number choice
//                        subPlaylistChoice = -1;
//                    }
//                } else { //If this is user's second time pressing the button after choosing a playlist they want to copy
//                    //Initialize 2 variables for indexes of songs they want to remove
//                    int index1, index2;
//                    try {
//                        //Check if user input has only one space
//                        if (input.indexOf(" ") != input.lastIndexOf(" ") || !input.contains(" ")) {
//                            throw new NumberFormatException();
//                        }
//                        //Store numbers in variables and subtract 1 because ArrayList starts from 0 not 1
//                        index1 = Integer.parseInt(input.substring(0, input.indexOf(" ")))-1;
//                        index2 = Integer.parseInt(input.substring(input.indexOf(" ")+1))-1;
//                        //Check if first index is less than or equal to second and if both indexes are within the range of songs in the playlist
//                        if (index1 <= index2 && index1>=0&&index1<playlists.get(subPlaylistChoice).getSongs().size() &&index2<playlists.get(subPlaylistChoice).getSongs().size()) {
//                            //If true create a sub playlist
//                            createSubPlaylist(subPlaylistChoice, index1, index2);
//                            //Reset choice of sub playlist
//                            subPlaylistChoice = -1;
//                        } else throw new NumberFormatException(); //If false throw error
//                    } catch (NumberFormatException invalid) {
//                        //Tell user their input is invalid
//                        inputField1.setText("Invalid input, try again.");
//                    }
//                }
//            }
//            case "Sub17" -> {
//                //Trim user input
//                String input = inputField1.getText().trim();
//
//                //Initialize 2 index int variables
//                int index1, index2;
//                try {
//                    //Check if user input has exactly 1 space
//                    if (input.indexOf(" ") != input.lastIndexOf(" ") || !input.contains(" ")) {
//                        throw new NumberFormatException();
//                    }
//                    //Store numbers in user input into variables and subtract 1 because of how indexes work
//                    index1 = Integer.parseInt(input.substring(0, input.indexOf(" ")))-1;
//                    index2 = Integer.parseInt(input.substring(input.indexOf(" ")+1))-1;
//                    //Check if playlist choices aren't the same and both are in the range of playlists added
//                    if (index1 != index2 && index1 >= 0 && index1 < playlists.size() && index2 >= 0 && index2 < playlists.size()) {
//                        //If true list common songs between the two playlists
//                        output1.setText(listCommonSongs(playlists.get(index1), playlists.get(index2)));
//                    } else throw new NumberFormatException(); //If false tell user to try again
//                } catch (NumberFormatException invalid) {
//                    inputField1.setText("Invalid input, try again.");
//                }
//            }
//            case "Sub18" -> {
//                //Swap panels inside frame
//                mainB2.setText("2) Accessing within a particular playlist");
//                frame.getContentPane().removeAll();
//                frame.getContentPane().add(mainMenuPanel);
//                frame.getContentPane().revalidate();
//                frame.getContentPane().repaint();
//            }
//            case "Main2" -> {
//                if (playlists.isEmpty()) {
//                    mainB2.setText("No playlists currently added.");
//                } else {
//                    playlistList.setText(displayAllPlaylists());
//                    frame.getContentPane().removeAll();
//                    frame.getContentPane().add(submenuTwoChoosePlaylist);
//                    frame.getContentPane().revalidate();
//                    frame.getContentPane().repaint();
//                }
//            }
//            case "Confirm" -> {
//                playlistNumber = getInputP(choosePlaylist);
//                if (playlistNumber != -1) {
//                    frame.getContentPane().removeAll();
//                    frame.getContentPane().add(submenuTwoPanel);
//                    frame.getContentPane().revalidate();
//                    frame.getContentPane().repaint();
//                }
//            }
//            case "Sub21" -> output2.setText(playlists.get(playlistNumber).displayAllSongs());
//            case "Sub22" -> {
//                int index = getInputS(inputField2);
//                if (index >= 0 && index < playlists.get(playlistNumber).getSongs().size()) {
//                    output2.setText(playlists.get(playlistNumber).displaySongInfo(index));
//                }
//            }
//            case "Sub23" -> {
//                if (addSongCounter == 1) {
//                    inputField2.setText("Enter title of song and press 3) again (delete this part): ");
//                    addSongCounter++;
//                } else if (addSongCounter == 2) {
//                    title = inputField2.getText();
//                    inputField2.setText("Enter artist of song and press 3) again (delete this part): ");
//                    addSongCounter++;
//                } else if (addSongCounter == 3) {
//                    artist = inputField2.getText();
//                    inputField2.setText("Enter genre of song and press 3) again (delete this part): ");
//                    addSongCounter++;
//                } else if (addSongCounter == 4) {
//                    genre = inputField2.getText();
//                    inputField2.setText("Enter rating of song 1-5 and press 3) again (without this part): ");
//                    addSongCounter++;
//                } else if (addSongCounter == 5) {
//                    try {
//                        rating = Integer.parseInt(inputField2.getText());
//                    } catch (NumberFormatException invalid) {
//                        inputField2.setText("Invalid rating, try again. ");
//                    }
//                    if (rating > 0 && rating <= 5) {
//                        inputField2.setText("Enter time of song and press 3) again (without this part): ");
//                        addSongCounter++;
//                    } else {
//                        inputField2.setText("Invalid rating, try again. ");
//                    }
//                } else if (addSongCounter == 6) {
//                    try {
//                        time = inputField2.getText();
//                        if (!time.contains(":")) throw new NumberFormatException();
//                        if (Integer.parseInt(time.substring(0, time.indexOf(":"))) < 0) throw new NumberFormatException();
//                        int seconds = Integer.parseInt(time.substring(time.indexOf(":")+1));
//                        if (time.substring(time.indexOf(":")+1).length() != 2 || seconds >= 60 || seconds < 0) throw new NumberFormatException();
//                        playlists.get(playlistNumber).addSong(new Song(title, artist, genre, rating, time));
//                        addSongCounter = 1;
//                        rating = -1;
//                        time = "";
//                        inputField2.setText("Song has been added");
//                        output2.setText(playlists.get(playlistNumber).displayAllSongs());
//                    } catch (NumberFormatException invalid) {
//                        time = "00:000";
//                        inputField2.setText("Invalid input.");
//                    }
//                }
//            }
//            case "Sub24" -> {
//                if (removeSongChoice == -1) {
//                    inputField2.setText("How do you want to remove songs from this playlist (delete this part)? 1. Song number 2. Title 3. First song 4. Range of songs?");
//                    removeSongChoice++;
//                } else if (removeSongChoice == 0) {
//                    try {
//                        removeSongChoice = Integer.parseInt(inputField2.getText());
//                        if (removeSongChoice < 1 || removeSongChoice > 4) throw new NumberFormatException();
//                        if (removeSongChoice == 1) {
//                            inputField2.setText("What is the number of the song you want to remove (delete this part)?");
//                            removeSongChoice = 11;
//                        } else if (removeSongChoice == 2) {
//                            inputField2.setText("What is the title of the song you want to remove from this playlist (delete this part, all songs with that title will be removed)? ");
//                            removeSongChoice = 22;
//                        } else if (removeSongChoice == 3) {
//                            playlists.get(playlistNumber).removeFirstSong();
//                            removeSongChoice = -1;
//                            output2.setText(playlists.get(playlistNumber).displayAllSongs());
//                            inputField2.setText("First song has been removed!");
//                        } else {
//                            inputField2.setText("Enter two numbers in the form \"N N\" where the first number is smaller than the second (delete this part):");
//                            removeSongChoice = 44;
//                        }
//                    } catch (NumberFormatException invalid) {
//                        removeSongChoice = -1;
//                        inputField2.setText("Invalid input, try again.");
//                    }
//                } else if (removeSongChoice == 11) {
//                    playlists.get(playlistNumber).removeSong(getInputS(inputField2));
//                    removeSongChoice = -1;
//                    output2.setText(playlists.get(playlistNumber).displayAllSongs());
//                    inputField2.setText("Song has been removed!");
//                } else if (removeSongChoice == 22) {
//                    playlists.get(playlistNumber).removeSong(inputField2.getText());
//                    removeSongChoice = -1;
//                    output2.setText(playlists.get(playlistNumber).displayAllSongs());
//                    inputField2.setText("All songs with this title have been removed!");
//                } else {
//                    int n1, n2;
//                    String input = inputField2.getText();
//                    try {
//                        n1 = Integer.parseInt(input.substring(0, input.indexOf(" ")))-1;
//                        n2 = Integer.parseInt(input.substring(input.indexOf(" ")+1))-1;
//                        if (n1>n2) throw new NumberFormatException();
//                        if (n1 < 0 || n1 >= playlists.get(playlistNumber).getSongs().size() || n2 >= playlists.get(playlistNumber).getSongs().size()) throw new NumberFormatException();
//                        playlists.get(playlistNumber).removeSong(n1, n2);
//                        removeSongChoice = -1;
//                        output2.setText(playlists.get(playlistNumber).displayAllSongs());
//                        inputField2.setText("All songs in this range have been removed!");
//                    } catch (NumberFormatException invalid) {
//                        inputField2.setText("Invalid input, try again.");
//                    }
//                }
//            }
//            case "Sub25" -> {
//                if (sortChoice == -1) {
//                    inputField2.setText("How do you want to sort songs from this playlist (delete this part)? 1. Title 2. Artist 3. Time");
//                    sortChoice++;
//                } else if (sortChoice == 0) {
//                    try {
//                        sortChoice = Integer.parseInt(inputField2.getText());
//                        if (sortChoice < 1 || sortChoice > 3) throw new NumberFormatException();
//                        if (sortChoice == 1) {
//                            Collections.sort(playlists.get(playlistNumber).getSongs());
//                            sortChoice = -1;
//                            inputField2.setText("Songs have been sorted by title!");
//                            output2.setText(playlists.get(playlistNumber).displayAllSongs());
//                        } else if (sortChoice == 2) {
//                            playlists.get(playlistNumber).getSongs().sort(new SortByArtist());
//                            sortChoice = -1;
//                            inputField2.setText("Songs have been sorted by artist!");
//                            output2.setText(playlists.get(playlistNumber).displayAllSongs());
//                        } else {
//                            playlists.get(playlistNumber).getSongs().sort(new SortByTime());
//                            sortChoice = -1;
//                            inputField2.setText("Songs have been sorted by time!");
//                            output2.setText(playlists.get(playlistNumber).displayAllSongs());
//                        }
//                    } catch (NumberFormatException invalid) {
//                        inputField2.setText("Invalid input, try again");
//                        sortChoice = 0;
//                    }
//                }
//            }
//            case "Sub26" -> {
//                frame.getContentPane().removeAll();
//                frame.getContentPane().add(mainMenuPanel);
//                frame.getContentPane().revalidate();
//                frame.getContentPane().repaint();
//            }
//        }
//    }
//    public Assignment4JFrames() {
//        //Create frame
//        frame = new JFrame ("Sastrify");
//        frame.setLocation(200, 200);
//
//
//        //MAIN MENU PANEL:
//        mainMenuPanel = new JPanel ();
//        mainMenuPanel.setLayout (new GridLayout (4,1,10,5));
//        mainMenuPanel.setBorder (BorderFactory.createEmptyBorder (50,50,50,50));
//        mainMenuPanel.setPreferredSize(new Dimension(1500, 800));
//
//        //Initialize JComponents
//        mainTitle = new JLabel("Sastrify");
//        mainB1 = new JButton ("1) Accessing your playlists");
//        mainB2 = new JButton ("2) Accessing within a particular playlist");
//        mainB3 = new JButton ("3) Exit");
//
//        //Setting ActionCommands
//        mainB1.setActionCommand("Main1");
//        mainB2.setActionCommand("Main2");
//        mainB3.setActionCommand("Exit");
//
//        //Adding ActionListeners
//        mainB1.addActionListener(this);
//        mainB2.addActionListener(this);
//        mainB3.addActionListener(this);
//
//        //Adding JComponents to panel
//        mainMenuPanel.add(mainTitle);
//        mainMenuPanel.add(mainB1);
//        mainMenuPanel.add(mainB2);
//        mainMenuPanel.add(mainB3);
//
//        //SUB MENU 1 PANEL:
//        submenuOnePanel = new JPanel();
//        submenuOnePanel.setLayout(new GridLayout(12, 1, 10, 5));
//        submenuOnePanel.setBorder(BorderFactory.createEmptyBorder (50,50,50,50));
//        submenuOnePanel.setPreferredSize(new Dimension(1500, 800));
//
//        //Initializing JComponents:
//        sub1Title = new JLabel("---------  SUB-MENU #1  ----------");
//        sub1B1 = new JButton("1) Display all of your playlists");
//        sub1B2 = new JButton("2) Display info on a particular playlist");
//        sub1B3 = new JButton("3) Add a playlist");
//        sub1B4 = new JButton("4) Remove a playlist");
//        sub1B5 = new JButton("5) Copy a playlist");
//        sub1B6 = new JButton("6) Create a sub-playlist");
//        sub1B7 = new JButton("7) List songs in common between two playlists");
//        sub1B8 = new JButton("8) Return back to main menu.");
//        output1 = new JLabel("Output: ");
//        outputPane1 = new JScrollPane(output1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        inputField1 = new JTextField("Enter inputs: ");
//
//
//        //Setting ActionCommands
//        sub1B1.setActionCommand("Sub11");
//        sub1B2.setActionCommand("Sub12");
//        sub1B3.setActionCommand("Sub13");
//        sub1B4.setActionCommand("Sub14");
//        sub1B5.setActionCommand("Sub15");
//        sub1B6.setActionCommand("Sub16");
//        sub1B7.setActionCommand("Sub17");
//        sub1B8.setActionCommand("Sub18");
//
//
//        //Adding ActionListeners
//        sub1B1.addActionListener(this);
//        sub1B2.addActionListener(this);
//        sub1B3.addActionListener(this);
//        sub1B4.addActionListener(this);
//        sub1B5.addActionListener(this);
//        sub1B6.addActionListener(this);
//        sub1B7.addActionListener(this);
//        sub1B8.addActionListener(this);
//
//        //Adding JComponents to panel
//        submenuOnePanel.add(sub1Title);
//        submenuOnePanel.add(sub1B1);
//        submenuOnePanel.add(sub1B2);
//        submenuOnePanel.add(sub1B3);
//        submenuOnePanel.add(sub1B4);
//        submenuOnePanel.add(sub1B5);
//        submenuOnePanel.add(sub1B6);
//        submenuOnePanel.add(sub1B7);
//        submenuOnePanel.add(sub1B8);
//        submenuOnePanel.add(outputPane1);
//        submenuOnePanel.add(inputField1);
//
//        //SUB MENU 2 PANEL CHOOSING PLAYLIST:
//        submenuTwoChoosePlaylist = new JPanel();
//        submenuTwoChoosePlaylist.setLayout (new GridLayout (3,1,10,5));
//        submenuTwoChoosePlaylist.setBorder (BorderFactory.createEmptyBorder (50,50,50,50));
//        submenuTwoChoosePlaylist.setPreferredSize(new Dimension(1500, 800));
//
//        //Initialize JComponents
//        playlistList = new JLabel();
//        choosePlaylist = new JTextField("Enter playlist number you want to operate on: ");
//        confirmPlaylist = new JButton("Confirm");
//
//        //Setting ActionCommands
//        confirmPlaylist.setActionCommand("Confirm");
//
//        //Adding ActionListeners
//        confirmPlaylist.addActionListener(this);
//
//        //Adding JComponents to panel:
//        submenuTwoChoosePlaylist.add(playlistList);
//        submenuTwoChoosePlaylist.add(choosePlaylist);
//        submenuTwoChoosePlaylist.add(confirmPlaylist);
//
//        //SUB MENU 2 PANEL:
//        submenuTwoPanel = new JPanel();
//        submenuTwoPanel.setLayout (new GridLayout (9,1,10,5));
//        submenuTwoPanel.setBorder (BorderFactory.createEmptyBorder (50,50,50,50));
//        submenuTwoPanel.setPreferredSize(new Dimension(1500, 800));
//
//        //Initialize JComponents
//        sub2Title = new JLabel("---------  SUB-MENU #2  ----------");
//        sub2B1 = new JButton("1) Display all songs (in the last sorted order) ");
//        sub2B2 = new JButton("2) Display info on a particular song ");
//        sub2B3 = new JButton("3) Add song");
//        sub2B4 = new JButton("4) Remove Song (4 options)");
//        sub2B5 = new JButton("5) Sort songs (3 options)");
//        sub2B6 = new JButton("6) Return back to main menu");
//        output2 = new JLabel("Output: ");
//        outputPane2 = new JScrollPane(output2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        inputField2 = new JTextField("Enter inputs");
//
//        //Setting ActionCommands
//        sub2B1.setActionCommand("Sub21");
//        sub2B2.setActionCommand("Sub22");
//        sub2B3.setActionCommand("Sub23");
//        sub2B4.setActionCommand("Sub24");
//        sub2B5.setActionCommand("Sub25");
//        sub2B6.setActionCommand("Sub26");
//
//        //Adding ActionListeners
//        sub2B1.addActionListener(this);
//        sub2B2.addActionListener(this);
//        sub2B3.addActionListener(this);
//        sub2B4.addActionListener(this);
//        sub2B5.addActionListener(this);
//        sub2B6.addActionListener(this);
//
//        //Adding JComponents to panel
//        submenuTwoPanel.add(sub2Title);
//        submenuTwoPanel.add(sub2B1);
//        submenuTwoPanel.add(sub2B2);
//        submenuTwoPanel.add(sub2B3);
//        submenuTwoPanel.add(sub2B4);
//        submenuTwoPanel.add(sub2B5);
//        submenuTwoPanel.add(sub2B6);
//        submenuTwoPanel.add(outputPane2);
//        submenuTwoPanel.add(inputField2);
//
//        //Adding panel to frame
//        frame.getContentPane().add(mainMenuPanel);
//        //Packing frame
//        frame.pack ();
//        //Set frame to visible
//        frame.setVisible (true);
//    }
//    public static void main(String[] args) throws IOException {
//        //Create new instance of class
//        new Assignment4JFrames();
//    }
//    //Method displayAllPlaylists takes no input and returns a String.
//    //Using a StringBuilder, the method combines all song titles into one StringBuilder and returns it
//    static String displayAllPlaylists() {
//        StringBuilder str = new StringBuilder();
//        if (playlists.isEmpty()) {
//            return "No playlists currently added.";
//        } else {
//            for (int i = 0; i < playlists.size(); i++) {
//                str.append(i + 1).append(". ").append(playlists.get(i).getTitle()).append(" ");
//            }
//        }
//        return str.toString();
//    }
//    //Method displayPlaylist takes in an int index and returns void
//    //This method changes submenu 1's output to display the information on a specific playlist chosen by the user using the index
//    static void displayPlaylist(int index) {
//        output1.setText(playlists.get(index).toString());
//    }
//    //Method addPlaylist takes in a String fileName and returns void
//    //
//    static void addPlaylist(String fileName) throws IOException {
//        try {
//            BufferedReader inputFile = new BufferedReader(new FileReader(fileName+".txt"));
//            String playlistTitle = inputFile.readLine();
//            int songNum = Integer.parseInt(inputFile.readLine());
//            playlists.add(new Playlist(playlistTitle, songNum));
//            Time totalTime = new Time("00:00");
//            for (int i = 0; i < songNum; i++) {
//                playlists.getLast().getSongs().add(new Song(inputFile.readLine(), inputFile.readLine(), inputFile.readLine(), Integer.parseInt(inputFile.readLine()), inputFile.readLine()));
//                totalTime.addTime(playlists.getLast().getSongs().getLast().getTime());
//            }
//            playlists.getLast().setTotalTime(totalTime);
//            inputField1.setText("Playlist has been added!");
//            inputFile.close();
//        } catch (FileNotFoundException e) {
//            inputField1.setText("File does not exist. ");
//        }
//        output1.setText(displayAllPlaylists());
//    }
//    static void removePlaylist(int index) {
//        playlists.remove(index);
//        inputField1.setText("Playlist has been removed!");
//        output1.setText(displayAllPlaylists());
//    }
//    static void copyPlaylist(int index) {
//        int songNum = playlists.get(index).getSongs().size();
//        playlists.add(new Playlist("Copy of " + playlists.get(index).getTitle(), songNum));
//        for (int i = 0; i < songNum; i++) {
//            playlists.getLast().getSongs().add(new Song(playlists.get(index).getSongs().get(i).getTitle(), playlists.get(index).getSongs().get(i).getArtist(), playlists.get(index).getSongs().get(i).getGenre(), playlists.get(index).getSongs().get(i).getRating(), playlists.get(index).getSongs().get(i).getTime().toString()));
//        }
//        playlists.getLast().setTotalTime(playlists.get(index).getTotalTime());
//        output1.setText(displayAllPlaylists());
//        inputField1.setText("Playlist has been copied!");
//    }
//    static void createSubPlaylist(int playlistIndex, int index1, int index2) {
//        playlists.add(new Playlist("Sub playlist of " + playlists.get(playlistIndex).getTitle(), index2-index1+1));
//        Time totalTime = new Time("00:00");
//        for (int i = index1; i <= index2; i++) {
//            playlists.getLast().getSongs().add(new Song(playlists.get(playlistIndex).getSongs().get(i).getTitle(), playlists.get(playlistIndex).getSongs().get(i).getArtist(), playlists.get(playlistIndex).getSongs().get(i).getGenre(), playlists.get(playlistIndex).getSongs().get(i).getRating(), playlists.get(playlistIndex).getSongs().get(i).getTime().toString()));
//            totalTime.addTime(playlists.getLast().getSongs().getLast().getTime());
//        }
//        playlists.getLast().setTotalTime(totalTime);
//        output1.setText(displayAllPlaylists());
//        inputField1.setText("Sub playlist has been created!");
//    }
//    static String listCommonSongs(Playlist p1, Playlist p2) {
//        StringBuilder str = new StringBuilder();
//        Playlist commonSongs = new Playlist("", 0);
//        for (int i = 0; i < p1.getSongs().size(); i++) {
//            Song curSong = new Song(p1.getSongs().get(i).getTitle(), "", "", 0, "00:00");
//            z
//            if (p2.getSongs().contains(curSong) &&  index < 0) {
//                commonSongs.getSongs().add(-index-1, curSong);
//            }
//        }
//        if (commonSongs.getSongs().isEmpty()) {
//            return "These 2 playlists have no songs in common with each other.";
//        }
//        for (int i = 0; i < commonSongs.getSongs().size(); i++) {
//            str.append(i + 1).append(". ").append(commonSongs.getSongs().get(i).getTitle()).append(" ");
//        }
//        return str.toString();
//    }
//    static int getInputP(JTextField input) {
//        int index;
//        try {
//            index = Integer.parseInt(input.getText())-1;
//            if (index>=0&&index<playlists.size()) {
//                return index;
//            } else throw new NumberFormatException();
//        } catch (NumberFormatException invalid) {
//            input.setText("Invalid input.");
//        }
//        return -1;
//    }
//    static int getInputS(JTextField input) {
//        int n1 = -1;
//        try {
//            n1 = Integer.parseInt(input.getText())-1;
//            if (n1<0||n1>=playlists.get(playlistNumber).getSongs().size()) throw new NumberFormatException();
//        } catch (NumberFormatException e){
//            input.setText("Invalid input.");
//        }
//        return n1;
//    }
//}
