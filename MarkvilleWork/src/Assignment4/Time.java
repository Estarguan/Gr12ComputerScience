package Assignment4;

public class Time {
	private int minutes;
	private int seconds;
	
	public Time(String time) {
		
		//3:03:35
		
		this.minutes = Integer.parseInt(time.substring(0,time.indexOf(':')));
		this.seconds = Integer.parseInt(time.substring(time.indexOf(':')+1));
		
	}
	
	public int getSeconds() {
		return this.seconds;
	}
	public int getMinutes() {
		return this.minutes;
	}

	
	public void getAverageTime(int songCount) {
		this.seconds += this.minutes*= 60;
		//Rounded to the nearest second
		seconds = Math.round(seconds/songCount);
		this.minutes = this.seconds /60;
		this.seconds %= 60;
	}
	
	public String toString() {
		return String.format("%d:%d",this.minutes,this.seconds);
	}
}
