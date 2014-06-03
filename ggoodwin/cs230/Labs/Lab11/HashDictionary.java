/**
 * Reads a dictionary from the input file, into a hash table. 
 * Each line in the file contains only one word.
 * The input file should not contain empty lines. 
 * An empty line signals the end of the file.
 * 
 */
import java.util.*;
import java.io.*;


public class HashDictionary implements Dictionary {
   
     Hashtable<String,String> hashDictionary;
    private int numWords;
    
    //constructor
    public HashDictionary(String filename){
	System.out.println("Dicitionary" + filename +" loaded");
	hashDictionary = new Hashtable<String,String>(50000);
	    try{
	    Scanner reader = new Scanner(new File(filename));
	    while(reader.hasNext()){
		String word = reader.next();
	       word =	word.trim();
	       word = word.toLowerCase();
		addWord(word);
		numWords++;
	    }
	    System.out.println("containing " + numWords);
	    reader.close();
	    }
	    catch(FileNotFoundException ex){
		
		System.out.println(ex);

		    }
	    
		

    }
    public int getNumWords(){
	return numWords;
    }
    
    public void addWord(String word){

	hashDictionary.put(word,word);
}
  
    public boolean searchWord(String word){
	return hashDictionary.containsKey(word);
	    
       
    }
    public static void main(String[] args) {
	HashDictionary bob = new HashDictionary("EnglishWords.txt");

  }
  
}
