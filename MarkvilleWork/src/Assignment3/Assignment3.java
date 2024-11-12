package Assignment3;
import java.util.*;
import java.io.*;

//Estar Guan - October 16, 2024
//A movie managing program that reads in a list of movies and stats from 
//a file called "assignment3input.txt" and allows for the user to search for certain
//movies by title or genre and also allows for duplicate titles where you can then
//sort by genre or rating.



class SortByRating implements Comparator<Movie>{
	
	//Compares two movie objects by their rating. Method is forced by the Comparator interface
	//Parameters: Movie m1, Movie m2 the two movies that need to be compared
	//Return: Int, positive 1 if movie 1's rating is greater otherwise -1
	public int compare(Movie m1, Movie m2) {
		
		if (m1.getRating() < m2.getRating()) return 1;
		if (m1.getRating() > m2.getRating()) return -1;
		return 0;
	}
}

class SortByGenre implements Comparator<Movie>{
	
	//Compares two movie objects by their genre. Method is forced by the Comparator interface
	//This sort also checks if two genres are the same then you need to one with the lower letter to go first
	//Parameters: Movie m1, Movie m2 the two movies that need to be compared
	//Return: Int, positive number if m1's unicode is greater than movie 2 and vice versa
	public int compare(Movie m1, Movie m2) {
		int difference = m1.getGenre().compareToIgnoreCase(m2.getGenre());
		if (difference == 0)return m1.getTitle().compareToIgnoreCase(m2.getTitle());
		return m1.getGenre().compareToIgnoreCase(m2.getGenre());
	}
}

class SearchByGenre implements Comparator<Movie>{
	
	//Compares two movie objects by their rating. Method is forced by the Comparator interface
	//For this search we don't check for the title letters we just check the genres
	//Parameters: Movie m1, Movie m2 the two movies that need to be compared
	//Return: Int, positive 1 if movie 1's rating is greater otherwise -1
	public int compare(Movie m1, Movie m2) {return m1.getGenre().compareToIgnoreCase(m2.getGenre());}
	
}
class Movie implements Comparable<Movie>{
	
	//Instance/Attribute Variables
	private String title; 
	private String genre;
	private double rating;
	private int ranking;
	
	//Static/Class variables
	private static int movieCount = 0;

	//Constructor of the class, defines the instances title, genre, and rating
	//If the rating is positive or 0 (all cases apart from making a random sort or search)
	//we need to increase the class varaible of movieCount.
	public Movie(String title, String genre, double rating) {
		this.title = title;
		this.genre = genre;
		this.rating = rating;
		if (rating >= 0) {
			movieCount++;			
		}
		
	}
	//Setters
	public void setRanking(int rank) {
		this.ranking = rank;
	}
	
	//Getters
	public double getRating() {
		return rating;
	}
	public String getGenre() {
		return genre;
	}
	public String getTitle() {
		return title;
	}
	//Implemented Method
	@Override
	//Compares two movie objects by their title. Method is forced by the Comparable interface
	//Parameters: Movie other the movies that need to be compared. The second movie is just this
	//Return: Int, positive 1 if movie 1's rating is greater otherwise -1
	public int compareTo(Movie other) {
        return this.title.compareToIgnoreCase(other.title);
    } 
	
	//Allows for the Movie object to get printed instead of the memory address
	//It's printed out in the format the assignment asks for with string.format
	//Parameters: None
	//Return: String, the string to be printed whenever we want to print a movie.
	public String toString() {
		return String.format("Movie title: %s%nGenre: %s%nRating: %.1f%%%nRanking: %d out of %d\n", title,genre,rating, ranking, movieCount);
	}

	
	
}

public class Assignment3 {
	
	//Searches for a certain movie within our movie arraylist and then checks to see if the title has duplicates
	//If there are it asks how the duplicates should be sorted; either rating or genre.
	//Parameters: ArrayList movielist which stores all the movies, string title the movie to find, in the reader to do the bonus inputs.
	//Return: void
	public static void titleSearch (ArrayList<Movie> movieList, String title,BufferedReader in) throws IOException {
		int movieIdx =  Collections.binarySearch(movieList, new Movie(title, "",-1.0));
		if (movieIdx < 0) System.out.println("Movie Not Found");
		else {
			//Checking to see if the title has any duplicates
			int left = movieIdx; 
			int right = movieIdx;
			ArrayList <Movie> miniList = new ArrayList<>();
			miniList.add(movieList.get(movieIdx));
			double searchRating = movieList.get(movieIdx).getRating();
			String searchGenre = movieList.get(movieIdx).getGenre();
			boolean genreSame = true;
			boolean ratingSame = true;
			while((left-1 >= 0 && movieList.get(left-1).getTitle().equalsIgnoreCase(title)) || (right+1 < movieList.size() && movieList.get(right+1).getTitle().equalsIgnoreCase(title))){
				if (movieList.get(left-1).getTitle().equalsIgnoreCase(title)) {
					left--;
					miniList.add(movieList.get(left));
				};
				if (movieList.get(right+1).getTitle().equalsIgnoreCase(title)) {
					right++;
					miniList.add(movieList.get(right));
				}
				if (movieList.get(left).getRating() != searchRating || movieList.get(right).getRating() != searchRating) {
					ratingSame = false;
				}if (!movieList.get(left).getGenre().equalsIgnoreCase(searchGenre) || !movieList.get(right).getGenre().equalsIgnoreCase(searchGenre)) {
					genreSame = false;
				}

			}
			//The bonus. Checking if any field has everything same
			if (right-left > 0) {
				if (!(ratingSame && genreSame))
				if (ratingSame) {
					Collections.sort(miniList,new SortByGenre());
				}else if (genreSame) {
					Collections.sort(miniList, new SortByRating());
				}else {
					String input = "";
					do {
						System.out.println("Sort by genre or ranking?");
						input = in.readLine();
					}while(!input.equalsIgnoreCase("genre") && !input.equalsIgnoreCase("ranking"));
					if (input.equalsIgnoreCase("genre")) {
						Collections.sort(miniList, new SortByGenre());
					}else {
						Collections.sort(miniList, new SortByRating());
					}
	
				}
				for (int i = 0; i < miniList.size(); i++) {
					System.out.println(miniList.get(i));
				}
				
			}else {
				System.out.println(movieList.get(movieIdx));
			}
		}
		
	}
	//Searches for a certain movie within our movie arraylist and then checks to see if the title has duplicates
	//If there are it asks how the duplicates should be sorted; either rating or title.
	//Parameters: ArrayList movielist which stores all the movies, string title the movie to find, in the reader to do the bonus inputs.
	//Return: void
	public static void genreSearch(ArrayList<Movie> movieList, String genre,BufferedReader in) throws IOException {
		Collections.sort(movieList, new SortByGenre());
		int movieIdx = Collections.binarySearch(movieList, new Movie("", genre, -1.0), new SearchByGenre());
		if (movieIdx < 0) {
			System.out.println("Genre not found");
		}else {
			
			//Checking to see if the genre has duplicates
			int left = movieIdx; 
			int right = movieIdx;
			double searchRating = movieList.get(movieIdx).getRating();
			String searchTitle = movieList.get(movieIdx).getTitle();
			boolean titleSame = true;
			boolean ratingSame = true;
			ArrayList <Movie> miniList = new ArrayList<>();
			miniList.add(movieList.get(movieIdx));
			
			while((left-1 >= 0 && movieList.get(left-1).getGenre().equalsIgnoreCase(genre)) || (right+1 < movieList.size() && movieList.get(right+1).getGenre().equalsIgnoreCase(genre))){
				if (movieList.get(left-1).getGenre().equalsIgnoreCase(genre)) {
					left--;
					miniList.add(movieList.get(left));
				}

				if (movieList.get(right+1).getGenre().equalsIgnoreCase(genre)) {
					right++;
					miniList.add(movieList.get(right));
				}
				if (movieList.get(left).getRating() != searchRating || movieList.get(right).getRating() != searchRating) {
					ratingSame = false;
				}if (!movieList.get(left).getTitle().equalsIgnoreCase(searchTitle) || !movieList.get(right).getTitle().equalsIgnoreCase(searchTitle)) {
					titleSame = false;
				}
				
			}
		
			//The bonus. Checking if any field has everything same
			if (right-left > 0) {
				if (!(ratingSame && titleSame))
				if (ratingSame) {
					Collections.sort(miniList,new SortByGenre());
				}else if (titleSame) {
					Collections.sort(miniList, new SortByRating());
				}else {
					String input = "";
					do {
						System.out.println("Sort by title or ranking?");
						input = in.readLine();
					}while(!input.equalsIgnoreCase("title") && !input.equalsIgnoreCase("ranking"));
					if (input.equalsIgnoreCase("title")) {
						Collections.sort(miniList);
					}else {
						Collections.sort(miniList, new SortByRating());
					}
	
				}
				for (int i = 0; i < miniList.size(); i++) {
					System.out.println(miniList.get(i));
				}
				
			}else {
				System.out.println(movieList.get(movieIdx));
			}	
			
		}
	}
	
	public static void main(String[] args)throws IOException {
		//To Do: 
		//Check my error checking for inputs
		//Extra spaces inputs don't work properly
		//Make everything case insenstiive (Should be fixed)
		//Rating rounding
		
		//When I do genre, nature. I get plant and another plant because there are two plant titles with the nature genre. Correct or wrong? ask tmrw
		
		//Variables
		BufferedReader inFile = new BufferedReader(new FileReader("assignment3Input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		String line;
		
		//Reading in lines from the text file and checking for errors 
		while ((line = inFile.readLine()) != null) {
			line = line.trim();
			int firstSpace = line.indexOf(' ');
			int lastSpace = line.lastIndexOf(' ');
			try {
				if (firstSpace != -1 && firstSpace != lastSpace) {
					String ratingStr = line.substring(0, firstSpace - 1).trim();
					String title = line.substring(firstSpace + 1,lastSpace).trim();
					String genre = line.substring(lastSpace+1).trim();
					if (ratingStr.isEmpty()||title.isEmpty()||genre.isEmpty()||line.charAt(firstSpace-1) != '%' || Double.parseDouble(ratingStr) < 0 || Double.parseDouble(ratingStr) > 100) {
						throw new NumberFormatException();
					}
					double rating = Math.round((Double.parseDouble(ratingStr))*10)/10.0;
					movieList.add(new Movie(title, genre,rating));

				}
			}catch(NumberFormatException e) {}
			
		}
		
		//Updating the movie's rankings
		Collections.sort(movieList,new SortByRating());
		int rank = 1;
		int rankIncrease = 1;
		for (int i = 0; i < movieList.size();i++) {
			if (i== 0)movieList.get(i).setRanking(rank);
			else if (movieList.get(i).getRating() < movieList.get(i-1).getRating()) {
				rank += rankIncrease;
				rankIncrease = 1;
				movieList.get(i).setRanking(rank);
			}
			else {
				movieList.get(i).setRanking(rank);
				rankIncrease++;
			}
		}
		
		//Making a list for just titles and another for just genre to minimize sorting
		Collections.sort(movieList);
		ArrayList<Movie> titleList = new ArrayList<>(movieList);
		Collections.sort(movieList,new SortByGenre());
		ArrayList<Movie> genreList = new ArrayList<>(movieList);
		
		//Taking input
		boolean gettingSearch = true;
		while (gettingSearch) {
			
			
			System.out.print("Press 1 to search by title and 2 for genre: ");
			String input = in.readLine();
			if (input.equals("1")) {
				while (true) {
					System.out.println("Movie Title: ");
					
					String movieTitle = in.readLine();
					if (movieTitle.equalsIgnoreCase("exit")) break;
					titleSearch(titleList, movieTitle,in);
					
				}
			}else if (input.equals("2")) {
				while (true) {
					System.out.println("Genre: ");
					String genre = in.readLine();
					if (genre.equalsIgnoreCase("exit"))break;
					genreSearch(genreList,genre, in);
				}
			
			}else if (input.equalsIgnoreCase("exit")) {
				System.out.println("Code ended");
				gettingSearch = false;
			}
			else {
				System.out.println("Invalid input");
			}
		}
		
		
		//Close the scanners and readers
		in.close();
		inFile.close();
		}

}
