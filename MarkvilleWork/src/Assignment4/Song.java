package Assignment4;

public class Song implements Comparable <Song>{
	private String title;
	private String artist;
	private String genre;
	private double rating;
	private Time songTime;

	//the constructor of this class, initliaizes all the class' variables
	public Song(String title, String artist, String genre, double rating, Time songTime) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.rating = rating;
		this.songTime = songTime;
	}

	//Returns the title for this song
	//Parametners : None
	// return: The string title
	public String getTitle(){return this.title;}

	//Returns the amount of thime the song holds
	//Parameters: None
	//Return: The time of the song
	public Time getTime() {
		return this.songTime;
	}

	//Return the artist of the song
	//Paramters: none
	//Retujrn: String of the artist name
	public String getArtist(){return this.artist;}

	//Returns the info of the songs but properly formaterd
	//Parameters:None
	//Return: The string to rpint
	public String toString() {
		return String.format("Song title: %s%nSong Artist: %s%nSong Genre: %s%nSong Rating: %.2f%nSong Time: %d:%02d%n",
				title,artist,genre,rating,songTime.getMinutes(),songTime.getSeconds());
	}

	//Returns true if the songs title anre the same otehrwise false
	//Pararmeters: Objet O forced, to show  match objhect class
	//Retuyrn boolean true or false
	public boolean equals (Object o) {
		Song s = (Song)o;
		//Apparently this should be case Insensitive??
		return this.title.equalsIgnoreCase(s.title);
	}
	//Returns the difference between the song Titles to sort and search alphabetically by title
	@Override
	public int compareTo(Song o) {
		return this.title.compareToIgnoreCase(o.title);
	}
}
