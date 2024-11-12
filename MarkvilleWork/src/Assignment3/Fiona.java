//Fiona Situ
//October 16, 2024
//Finds Ms. Wong pirated movies sorting program, puts all movies in a list and give you the movie you ask for sorted
package Assignment3;

import java.io.*;
import java.util.*;
//class
class Movies implements Comparable<Movies> {
    //instance variable
    private final double rating;
    private final String titleName;
    private final String genre;
    private int rank;

    //Static variables
    private static int allMovies;

    //constructor
    public Movies(Double rating, String Name, String genre, boolean real) {
        this.rating = rating;
        this.titleName = Name;
        this.genre = genre;
        //add to movie list
        if (real)
            allMovies++;
    }

    //getter and setter methods
    public double getRating() {
        return rating;
    }
    public String getTitleName() {
        return titleName;
    }
    public String getGenre() {
        return genre;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    //compareTo method, compares titles
    public int compareTo(Movies e) {
        return this.titleName.toLowerCase().compareTo(e.titleName.toLowerCase());
    }

    //toString method
    public String toString() {
        return String.format("Movie title: %s%nGenre: %s%nRating: %.1f%%%nRanking: %d out of %d", titleName, genre, rating, rank, allMovies);
    }
}
//compare method, compares by genre
class SortByGenres implements Comparator<Movies> {
    public int compare(Movies m1, Movies m2) {
        return (m1.getGenre().toLowerCase()).compareTo(m2.getGenre().toLowerCase());
    }
}

class SortByRank implements Comparator<Movies> {
    public int compare(Movies m1, Movies m2) {
        if (m1.getRating() < m2.getRating())
            return 1;
        else if (m1.getRating() > m2.getRating())
            return -1;
        else
            return 0;
    }
}

public class Fiona {
    //find all with the same genre
    //the list, where it found the genre, the left and right of the list
    //returns void
    public static void findGenre(ArrayList<Movies> list, int mid, int left, int right) {
        //find if genre is same on left
        while (left >= 0 && list.get(left).getGenre().equalsIgnoreCase(list.get(mid).getGenre())) {
            left--;
        }
        //find if genre is same on right
        while (right < list.size() && list.get(right).getGenre().equalsIgnoreCase(list.get(mid).getGenre())) {
            right++;
        }
        left++;
        right--;
        printAndSort(list, left, right, true);
    }

    //find all with the same title
    //the list, where it found the title, the left and right of the list
    //return void
    public static void findTitle(ArrayList<Movies> list, int mid, int left, int right) {
        //find if title is same on left
        while (left >= 0 && list.get(left).getTitleName().equalsIgnoreCase(list.get(mid).getTitleName())) {
            left--;
        }
        //find if title is same on right
        while (right < list.size() && list.get(right).getTitleName().equalsIgnoreCase(list.get(mid).getTitleName())) {
            right++;
        }
        left++;
        right--;

        printAndSort(list, left, right, false);
    }
    //prints out
    public static void printAndSort(ArrayList<Movies> list, int left, int right, boolean genre) {
        String type;
        //if only 1 item
        if (right - left == 0) {
            System.out.println("\n" + list.get(left) + "\n");
        } else {
            ArrayList<Movies> list2 = new ArrayList<>();
            //add to list
            for (int i = left; i <= right; i++) {
                list2.add(list.get(i));
            }
            //ask how you want to sort by
            String num = "0";
            int allSame = 1;
            if (genre) {
                //if all the same title
                type = "Title";
                for (int r = 1; r < list2.size(); r++) {
                    if (list2.get(0).getTitleName().equalsIgnoreCase(list2.get(r).getTitleName())) {
                        allSame++;
                    }
                }
            } else {
                //if all the same genre
                type = "Genre";
                for (int y = 1; y < list2.size(); y++) {
                    if (list2.get(0).getGenre().equalsIgnoreCase(list2.get(y).getGenre())) {
                        allSame++;
                    }
                }
            }
            if (allSame == list2.size()) {
                num = "2";
            }
            allSame = 1;
            //if all the same rating
            for (int z = 1; z < list2.size(); z++) {
                if (list2.get(0).getRating() == (list2.get(z).getRating())) {
                    allSame++;
                }
            }
            if (allSame == list2.size()) {
                num = "1";
            }
            //ask how you want to sort
            Scanner in = new Scanner(System.in);
            if (num.equals("0")) {
                while (!num.equals("1") && !num.equals("2")) {
                    System.out.print("How do you want to sort by: 1 (" + type + ") or 2 (Rating): ");
                    num = in.nextLine();
                }
            }
            //sorting
            if (num.equals("1") && type.equals("Title")) {
                //sort by title
                Collections.sort(list2);
            } else if (num.equals("1")) {
                //sort by genre
                Collections.sort(list2, new SortByGenres());
            } else {
                //sort by rank
                Collections.sort(list2, new SortByRank());
            }
            //print out all movies with the same genre in a sorted order
            for (Movies movies : list2) {
                System.out.println(movies + "\n");
            }
        }
    }
    //main
    public static void main(String[] args) throws IOException {
        try {
    		BufferedReader inFile = new BufferedReader(new FileReader("assignment3Input.txt"));
            Scanner in = new Scanner(System.in);
            //title list
            ArrayList<Movies> titleList = new ArrayList<>();
            String line;
            //grab line  from file and add to list
            while ((line = inFile.readLine()) != null) {
                try {
                    //if there are invalid lines
                    while (!line.contains(" ") || line.indexOf(" ") == line.lastIndexOf(" ") || !line.contains("%") || Double.parseDouble(line.substring(0, line.indexOf("%"))) > 100 || Double.parseDouble(line.substring(0, line.indexOf("%"))) < 0) {
                        line = inFile.readLine();
                        if (line == null)
                            break;
                    }
                    if (line == null)
                        break;
                    //lines are valid
                    line = line.trim();
                    Movies m1 = new Movies(Double.parseDouble((line.substring(0, line.indexOf("% "))).trim()), line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" ")).trim(), line.substring(line.lastIndexOf(" ") + 1).trim(), true);
                    titleList.add(m1);
                } catch (NumberFormatException e) {
                }
            }
            String name = "";
            //if the array is empty
            if (titleList.isEmpty()){
                name = "exit all";
            }
            ArrayList<Movies> ratingList = new ArrayList<>(titleList);

            inFile.close();
            System.out.println();

            //list sorting
            Collections.sort(titleList);
            Collections.sort(ratingList, new SortByRank());
            //giving rating to the title
            for (int f = 0; f < titleList.size(); f++) {
                for (int o = titleList.size() - 1; o > -1; o--) {
                    if (titleList.get(f).getRating() == ratingList.get(o).getRating()) {
                        titleList.get(f).setRank(o + 1);
                    }
                }
            }
            //arrays
            ArrayList<Movies> genreList = new ArrayList<>(titleList);
            Collections.sort(genreList, new SortByGenres());

            String genre;
            int realItem;
            //main menu
            while (!name.equalsIgnoreCase("exit all")) {
                genre = "";
                System.out.print("Choose 1 (Movie title) or 2 (Genre): ");
                name = in.nextLine();
                //if you want to search by movie name
                if (name.equals("1")) {
                    while (!name.equalsIgnoreCase("exit")) {
                        System.out.print("Title: ");
                        name = in.nextLine();
                        System.out.println();
                        //search by title
                        realItem = Collections.binarySearch(titleList, new Movies(0.0, name, "", false));
                        //finds it
                        if (realItem >= 0) {
                            findTitle(titleList, realItem, realItem - 1, realItem + 1);
                            //no find
                        } else if (!name.equalsIgnoreCase("exit")) {
                            System.out.println("No Movie with that title\n");
                        }
                    }
                    //search by genre
                } else if (name.equals("2")) {
                    while (!genre.equalsIgnoreCase("exit")) {
                        System.out.print("Genre: ");
                        genre = in.nextLine();
                        //search by genre
                        realItem = Collections.binarySearch(genreList, new Movies(0.0, "", genre, false), new SortByGenres());
                        //finds it
                        if (realItem >= 0) {
                            findGenre(genreList, realItem, realItem - 1, realItem + 1);
                            //no find
                        } else if (!genre.equalsIgnoreCase("exit")) {
                            System.out.println("No Movie with that Genre\n");
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
