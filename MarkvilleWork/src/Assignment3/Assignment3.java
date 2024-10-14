package Assignment3;
import java.util.*;
import java.io.*;


class SortByRating implements Comparator<Movie>{
	
	public int compare(Movie m1, Movie m2) {
		
		if (m1.getRating() < m2.getRating()) return 1;
		if (m1.getRating() > m2.getRating()) return -1;
		return 0;
	}
}

class SortByGenre implements Comparator<Movie>{
	public int compare(Movie m1, Movie m2) {
		int difference = m1.getGenre().compareTo(m2.getGenre());
		if (difference == 0)return m1.getTitle().compareTo(m2.getTitle());
		return m1.getGenre().compareTo(m2.getGenre());
	}
}

class SearchByGenre implements Comparator<Movie>{
	public int compare(Movie m1, Movie m2) {return m1.getGenre().compareTo(m2.getGenre());}
	
}
class Movie implements Comparable<Movie>{
	
	//Instance Variables
	private String title; 
	private String genre;
	private double rating;
	private int ranking;
	
	//Static variables
	private static int movieCount = 0;

	
	public Movie(String title, String genre, double rating) {
		this.title = title;
		this.genre = genre;
		this.rating = rating;
		if (rating >= 0) {
			movieCount++;			
		}
		
	}
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
	public int compareTo(Movie other) {
        return title.compareTo(other.title);
    } 
	
	
	public String toString() {
		return String.format("Movie title: %s%nGenre: %s%nRating: %.2f%nRanking: %d out of %d\n", title,genre,rating, ranking, movieCount);
	}

	
	
}

public class Assignment3 {
	
	public static void titleSearch(ArrayList<Movie> movieList, String title) {
		int movieIdx =  Collections.binarySearch(movieList, new Movie(title, "",-1.0));
		if (movieIdx < 0) System.out.println("Movie Not Found");
		else {
			int left = movieIdx; 
			int right = movieIdx;
			ArrayList <Movie> miniList = new ArrayList<>();
			miniList.add(movieList.get(movieIdx));
			double searchRating = movieList.get(movieIdx).getRating();
			String searchGenre = movieList.get(movieIdx).getGenre();
			boolean genreSame = true;
			boolean ratingSame = true;
			while((left-1 >= 0 && movieList.get(left-1).getTitle().equals(title)) || (right+1 < movieList.size() && movieList.get(right+1).getTitle().equals(title))){
				if (movieList.get(left-1).getTitle().equals(title)) {
					left--;
					miniList.add(movieList.get(left-1));
				};
				if (movieList.get(right+1).getTitle().equals(title)) {
					right++;
					miniList.add(movieList.get(right+1));
				}
				if (movieList.get(left-1).getRating() != searchRating || movieList.get(right+1).getRating() != searchRating) {
					ratingSame = false;
				}if (!movieList.get(left-1).getGenre().equals(searchGenre) || !movieList.get(right+1).getGenre().equals(searchGenre)) {
					genreSame = false;
				}

			}
			if (right-left > 0) {
				System.out.println("Supposed to do bonus");
				if (ra)
				
			}else {
				System.out.println(movieList.get(movieIdx));
			}
		}
		
	}
	public static void genreSearch(ArrayList<Movie> movieList, String genre) {
		Collections.sort(movieList, new SortByGenre());
		int movieIdx = Collections.binarySearch(movieList, new Movie("", genre, -1.0), new SearchByGenre());
		if (movieIdx < 0) {
			System.out.println("Genre not found");
		}else {
			int left = movieIdx; 
			int right = movieIdx;
			
			while((left-1 >= 0 && movieList.get(left-1).getGenre().equals(genre)) || (right+1 < movieList.size() && movieList.get(right+1).getGenre().equals(genre))){
				if (movieList.get(left-1).getGenre().equals(genre)) left--;
				if (movieList.get(right+1).getGenre().equals(genre)) right++;
			}
			for (int i = left; i <= right;i++) {
				System.out.println(movieList.get(i));
			}
		
			
		}
	}
	
	public static void main(String[] args)throws IOException {
		//To Do: 
		//Check my error checking for inputs
		//Make everything case insenstiive
		//Rating rounding
		
		//When I do genre, nature. I get plant and another plant because there are two plant titles with the nature genre. Correct or wrong? ask tmrw
		
		//Variables
		BufferedReader inFile = new BufferedReader(new FileReader("assignment3Input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		String line;
		
		
		while ((line = inFile.readLine()) != null) {
			if (line.indexOf('%') == -1)continue;
			if (line.indexOf(' ') == 	line.lastIndexOf(' '))continue;	
			if (line.indexOf('%') == 0)continue;
			Double rating = Double.parseDouble(line.substring(0,line.indexOf('%')));
			String title = line.substring(line.indexOf(' ')+1, line.lastIndexOf(' '));
			String genre = line.substring(line.lastIndexOf(' ')+1);
			if (rating < 0 || rating > 100)continue;

			movieList.add(new Movie(title, genre, rating));
		}
		
		Collections.sort(movieList,new SortByRating());
		int rank = 1;
		for (int i = 0; i < movieList.size();i++) {
			if (i== 0)movieList.get(i).setRanking(rank);
			else if (movieList.get(i).getRating() < movieList.get(i-1).getRating()) {
				movieList.get(i).setRanking(rank+1);
				rank++;
			}
			else {
				movieList.get(i).setRanking(rank);
			}
		}
		
		Collections.sort(movieList);
		ArrayList<Movie> titleList = new ArrayList<>(movieList);
		Collections.sort(movieList,new SortByGenre());
		ArrayList<Movie> genreList = new ArrayList<>(movieList);
		
		boolean gettingSearch = true;
		while (gettingSearch) {
			
			
			System.out.print("Press 1 to search by title and 2 for genre: ");
			String input = in.readLine();
			if (input.equals("1")) {
				while (true) {
					System.out.println("Movie Title: ");
					
					String movieTitle = in.readLine();
					if (movieTitle.equalsIgnoreCase("exit")) break;
					titleSearch(titleList, movieTitle);
					
				}
			}else if (input.equals("2")) {
				while (true) {
					System.out.println("Genre: ");
					String genre = in.readLine();
					if (genre.equalsIgnoreCase("exit"))break;
					genreSearch(genreList,genre);
				}
			
			}else if (input.equalsIgnoreCase("exit")) {
				System.out.println("Code ended");
				gettingSearch = false;
			}
			else {
				System.out.println("Invalid input");
			}
		}
		
		
		//Close the reader
		in.close();

	}

}
