package Assignment4;
import java.util.*;
import java.io.*;

public class Playlist {
	private String playlistName;
	private int songNum;
	private ArrayList<Song> songs = new ArrayList<Song>();
	public Playlist(String playlistName,int numSongs, ArrayList<Song>songs) {
		this.playlistName = playlistName;
		this.songNum = numSongs;
		this.songs = songs;
	}
	
	public String getPlaylistName() {
		return this.playlistName;
	}
	public String toString() {
		int minutes = 0;
		int seconds = 0;
		//Getting the time
		for (Song song : songs) {
			minutes += song.getTime().getMinutes();
			seconds += song.getTime().getSeconds();
			if (seconds >= 60) {
				minutes += seconds/60;
				seconds %= 60;
			}
		}
		Time totalTime = new Time(minutes+":"+seconds);
		String totalTimeString = totalTime.toString();
		totalTime.getAverageTime(songNum);
		String averageTimeString = totalTime.toString();
		String returnString = String.format("Playlist Name: %s%nNumber of songs: %d%nTotal Time of all songs:%s%nAverage Time per song:%s%n",playlistName,songNum,totalTimeString, averageTimeString);
		return returnString;
	}
}
