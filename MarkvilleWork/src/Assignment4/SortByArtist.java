package Assignment4;

import java.util.Comparator;

public class SortByArtist implements Comparator<Song> {

    @Override
    public int compare(Song o1, Song o2) {
        return o1.getArtist().compareToIgnoreCase(o2.getArtist());
    }
}
