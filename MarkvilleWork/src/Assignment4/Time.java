package Assignment4;

public class Time {
	private int minutes;
	private int seconds;

	//the constructor of this class to set up the number of minutes and hours
	//Params: STring time thje time of the song
	//Return: None
	public Time(String time) {
		
		//3:03:35
		String secondsString = time.substring(time.indexOf(':') + 1);
		this.minutes = Integer.parseInt(time.substring(0,time.indexOf(':')));
		if (secondsString.length() == 1) {
			this.seconds = Integer.parseInt(secondsString) * 10; // Convert single digit seconds to tens
		} else {
			if (secondsString.charAt(0)=='0'){
				this.seconds = Integer.parseInt(time.substring(time.indexOf(':')+1).charAt(1)+"");
			}else{
				this.seconds = Integer.parseInt(time.substring(time.indexOf(':')+1));
			}
		}


		
	}

	//Returns the seconds of the class
	//Parameters: None
	//Return: INt number of seconds
	public int getSeconds() {
		return this.seconds;
	}
	//Returns the minutes of the class
	//Parameters: None
	//Return: INt number of minutes
	public int getMinutes() {
		return this.minutes;
	}
	//Returns the length of the song
	//Parameters: None
	//Return: INt number of seconds
	public int getLength(){
		 return seconds+minutes*60;
	}

	//Adds some more time to an existing time bariavble either attached to a playlist as total time or a song
	//Paraters: the song we are adding
	//Return Void
	public void addTime(Song newSong){
		this.minutes += newSong.getTime().minutes;
		this.seconds += newSong.getTime().seconds;
		if (seconds >= 60) {
			minutes += seconds/60;
			seconds %= 60;
		}
	}

	//Removes some  time from the class
	//Parameters: Song newSong the song to remove time and stuff from
	//Return: Void
	public void removeTime(Song newSong){
		this.minutes -= newSong.getTime().minutes;
		this.seconds -= newSong.getTime().seconds;

		// If seconds are negative, borrow 1 minute (60 seconds)
		if (seconds < 0) {
			minutes -= 1;
			seconds += 60; // Add 60 seconds to make seconds non-negative
		}

		// If minutes are negative
//		if (minutes < 0) {
//			minutes = 0;
//			seconds = 0;
//		}

	}

	//Gets the average time of all the songs in a playlist
	//Parameters: int songCount, the number of songs
	//Return: The time for teh avertage time
	public Time getAverageTime(int songCount) {
		int tempSeconds = seconds;
		int tempMinutes = minutes;
		tempSeconds += tempMinutes*= 60;
		//Rounded to the nearest second
		tempSeconds = Math.round(tempSeconds/songCount);
		tempMinutes = tempSeconds /60;
		tempSeconds %= 60;
		return new Time(tempMinutes + ":" + tempSeconds);
	}

	//Returns the time in the format so theres always a colon inbetwen and 2s can become 02s or 20s depending on what they supp[ose dto be
	public String toString() {
		return String.format("%d:%02d", this.minutes, this.seconds);
	}

}
