/*Gabrielle Goodwin
 * 4/12/10
 * task 2*/
import java.io.*;
import java.util.*;

public class MusicLibrary extends Song{
//instance variables
   Song [] music;
  int number;
//constructor
public MusicLibrary(java.lang.String fileName)
  throws java.io.FileNotFoundException{
//open the file for reading
  
Scanner reader = new Scanner(new File(PicOps.getFolder()+fileName));
  //create an array to contain all the strings in the file
   music = new Song[20]; //max size 20;

}
public static int getSize(String fileName){
  Scanner reader = new Scanner(System.in);
  //create an array to contain all the strings in the file
   Song [] music = new Song[20]; //max size 20;

  int i = 0;
  String the_title;
  String the_artist;
  String the_genre;
  
  
  while (reader.hasNext()){//continue until we reach end of file
    the_title = reader.nextLine();
    the_artist = reader.nextLine();
    the_genre = reader.nextLine();
    music[i] = new Song(the_title,the_artist,the_genre);
    i++;
  }
  reader.close();
  int number = i;
  return i;
}
  public void printSongsByArtist(java.lang.String artist){
    if(artist.equals(getArtist()));
       System.out.println(music);
    }
  
  public void printSongsOfGenre(java.lang.String genre){
     if(genre.equals(getGenre())){
      System.out.println(music);
     }
  }
  public static void main(String[] args) 
  {
    Scanner reader = new Scanner(System.in);
    System.out.println("please enter the name of a file containing song information: ");
    String line = reader.nextLine();
    System.out.println("Songs in Library: "+ getSize(line));
    System.out.println("Search music Library for an artist: ");
    String line2= reader.nextLine();
    System.out.println(line2.printSongsByArtist());
    System.out.println("Search music library for a genre: ");
    String line3= reader.nextLine();
    System.out.println(line3.printSongsOfGenre());
  
  reader.close();
}// Could not get this code to compile :( 
}
    


     