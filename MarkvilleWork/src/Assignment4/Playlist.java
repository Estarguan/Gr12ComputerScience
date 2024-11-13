package Assignment4;
import java.util.*;
import java.io.*;

public class Playlist {
	private String playlistName;
	private int songNum;
	private ArrayList<Song> songs;
	private Time totalTime;
	//This classes constructor which initliazes all of the global/class variables
	//Parameters: String playtlistName, int numSongs, arraylist sojngs
	//Return: None
	public Playlist(String playlistName,int numSongs, ArrayList<Song>songs) {
		this.playlistName = playlistName;
		this.songNum = numSongs;
		this.songs = songs;

		int minutes = 0;
		int seconds = 0;
		//Getting the time
		for (Song song : songs) {
			minutes += song.getTime().getMinutes();
			seconds += song.getTime().getSeconds();
			if (seconds > 60) {
				minutes += seconds/60;
				seconds %= 60;
			}
		}

		this.totalTime = new Time(String.format("%d:%02d",minutes,seconds));
	}

	//I forgot if my getters need comments
	//Parameters: None
	//return, playistName the palylist we are looking for
	public String getPlaylistName() {
		return this.playlistName;
	}
	//Gets me the numbenr of songs in a given playlist
	//Parameters: None
	//Return, the number of Songs
	public int getSongNum(){ return this.songNum;}
	//Gets me the arrayof songs
	//Parameters: None
	//Returns: The arraylist onf songs
	public ArrayList<Song> getSongs(){return this.songs;}

	//Adds a song to the playlists song list, and then it adjusts the songNUm variable and the total time
	//Parameters: Song new Song the song to add
	//Return: Void
	public void addSong(Song newSong){
		totalTime.addTime(newSong);
		this.songs.add(newSong);
		this.songNum++;
	}
	//Removes a song from the song playlist and then we do some work
	//Parameters, int idx the index we want to remove
	//Return:Void
	public void removeSong(int idx){
		totalTime.removeTime(songs.get(idx));
		this.songs.remove(idx);
		this.songNum--;
	}


//	public void removeSong(int startIdx, int endIdx){
//		songs.delete
//	}

	//a Method that displays all the songs title only
	//Parameters: None
	//Return: String of all the songs combinedf together

	public String displayAllSongs(){
		String songTitles = "";
		for (int i = 0; i <songs.size();i++){
			songTitles += (i+1) + ": " + songs.get(i).getTitle() + "\n";
		}
		return songTitles;
	}
	//Returns the songs in the format Ms Wong want to see it overides the object class
	//Parameters: None
	//Return: The string to print out
	public String toString() {
		String totalTimeString = totalTime.toString();
//		totalTime.getAverageTime(songNum);
//		String averageTimeString = totalTime.toString();
		Time averageTime = totalTime.getAverageTime(songNum);
        return String.format("Playlist Name: %s%nNumber of songs: %d%nTotal Time of all songs:%s%nAverage Time per song:%s%n",playlistName,songNum,totalTimeString, averageTime.toString());
	}
}
