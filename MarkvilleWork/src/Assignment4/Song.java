package Assignment4;

public class Song {
	private String title;
	private String artist;
	private String genre;
	private double rating;
	private Time songTime;
	
	public Song(String title, String artist, String genre, double rating, Time songTime) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.rating = rating;
		this.songTime = songTime;
	}
	
	public Time getTime() {
		return this.songTime;
	}
	public String toString() {
		return String.format("Song title: %s%nSong Artist: %s%nSong Genre: %s%nSong Rating: %.2f%nSong Time: %d:%d%n",
				title,artist,genre,rating,songTime.getMinutes(),songTime.getSeconds());
	}
	
}
