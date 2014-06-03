/** Gabe Goodwin
MidTerm 2
WellsAir problem
requests
*/
import java.util.*;
import java.io.*;
import java.net.*;

public class Requests{
    //instance variables
    private String fromCity;
    private String toCity;
    private LinkedList<String> requests; 

    public Requests(String fromCity, String toCity){
	this.fromCity = fromCity;
	this.toCity = toCity;
    }
    public String getFromCity(){
	return fromCity;
    }

    public String getToCity(){
	return toCity;
    }

    public static Requests fromLine(String Line){
	String[] temp = Line.split(" ");
	String fromCity = temp[0];
	String toCity = temp[1];

	return new Requests(fromCity,toCity);
    }

    public LinkedList<String> getRequests(String fileName)throws FileNotFoundException{
