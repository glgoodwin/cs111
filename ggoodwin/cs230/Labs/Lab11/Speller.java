/**    CS230
 *     Fall 2010
 *     Speller.java
 *     Simple hash table spell checker program
*/

import java.io.*;
import java.util.*;

public class Speller  {
    //instance variables
    private String[] lineWords;
    private LinkedList<String> misspelled;
    private int numWords;
    
    //constructor
    public Speller(){
	lineWords = new String[200];
	misspelled = new LinkedList<String>();
    }

    
    public void checkSpelling(String filename, HashDictionary diction){
	    	try{
	    Scanner reader = new Scanner(new File(filename));
	    System.out.println("Document "+filename);
	    while(reader.hasNext()){
		String line = reader.next();
		lineWords = line.split(" ");
		int i = 0;
		while(i < lineWords.length){
		    if(!diction.searchWord(lineWords[i].toLowerCase())){
			if(!misspelled.contains(lineWords[i].toLowerCase())){
			   misspelled.add(lineWords[i]);
			   numWords++;
			   i++;
			}
		    } i++;}
		i++;
	    }System.out.println("contains " + numWords + " misspelled words:");
		}	catch(FileNotFoundException ex){
		    System.out.println(ex);
		}
		System.out.println(misspelled);}
  
  public static void main (String[] args)  throws IOException {
    	HashDictionary bob = new HashDictionary("EnglishWords.txt");
	Speller fred = new Speller();
	fred.checkSpelling("AllYouNeedIsLove.txt",bob);
  }	
}
