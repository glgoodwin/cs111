/** Gabe Goodwin
    MidTerm 2
    WellsAir problem
*/
import java.util.*;
import java.io.*;
import java.net.*;

public class Cities{
    //instance variables
    private String Abbr;
    private String City;
    private String State;
    private LinkedList<Cities> cities;
  
  

    public Cities(String Abbr, String City, String State){
	cities = new LinkedList<Cities>();
	this.Abbr = Abbr;
	this.City = City;
	this.State = State;
	
    }

    //instance methods

    public String getAbbr(){
	return Abbr;
    }

    public String getCity(){
	return City;
    }

    public String getState(){
	return State;
    }
    
   
    public static  Cities fromLine(String Line){
	String[] temp = Line.split(" ");
       	String abbre = temp[0];
	String cityName = temp[1];
	String cityState= temp[2];


	return new Cities(abbre, cityName, cityState);
    }
    
    public String toString(){
	return City + ", " + State;
    }
    
    public String toString(String Abbr){
	return City + "," + State;
    }
	    
    public boolean  matchCity(String abbrev){
	if(abbrev.equals(Abbr))
	    return true;
    else
	return false;
    }



  

	

}
