package assignment1;
import java.io.*;
import java.nio.Buffer;
import java.util.*;
public class test {

    public static void main (String[] args) throws IOException,NumberFormatException{
        String s = "programming is so coool";
        String[] list = s.split("o");
        System.out.println(list.length);
        for (String sn : list){
            System.out.println(sn);
        }
    }
}
