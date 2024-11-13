package Assignment4;

import java.util.Comparator;

public class SortByTime implements Comparator<Song> {
    @Override
    public int compare(Song o1, Song o2) {
        return o1.getTime().getLength()-o2.getTime().getLength();
    }
}
