/** cs230 Lab 4
    Gabe Goodwin
    9/28/2010
**/
import java.util.*;
import java.io.*;
import java.net.*;

public class CyberSpace{

    //instance variables
    private LinkedList<Webpage> pages;
    private int numURLs;


    //constructor
    public CyberSpace(){
	pages = new LinkedList<Webpage>();
	numURLs = 0;
    }

    public String toString(){
	String webString = "Results from visiting " + numURLs + " websites.\n";
	Webpage page;
	for(int i = 0; i<pages.size();i++){
	    page = pages.get(i);
	    webString += page;
	}
	return webString;
    }

    public void readURLS(){//throws IOException{

	//	try{
	    System.out.println("Please enter URLs (without spaces) below; end your list with ctrl-D:"); 
	    Scanner reader = new Scanner(System.in);
	    while(reader.hasNext()){
		Webpage a = new Webpage(reader.nextLine());
		pages.addLast(a);
		numURLs++;
	    }
	    reader.close();
    }
	    //	}
	    //	catch (IOException ex){
	    //    System.out.println(ex);
	    //			}
          
    

    public void fetchAndCount(){
       	for(int i=0; i<pages.size();i++){
	    Webpage a = pages.get(i);
	    a.getLineCounter();
	}
    }

    public static void main(String[]args){
	CyberSpace bob = new CyberSpace();
	bob.readURLS();  
	System.out.print(bob);
	

    }


}