/** Gabe Goodwin
MidTerm 2
WellsAir Problem
*/

import java.util.*;
import java.io.*;
import java.net.*;

public class Flights{
       //instance variables
    private String toCity;
    private String fromCity;
    private int flightNum;
    private int price;
    private LinkedList<String> neighbors;
    private LinkedList<GraphNode> flights;

    //constructor
    public Flights(String fromCity, String toCity, int flightNum, int price){
	this.toCity = toCity;
	this.fromCity = fromCity;
	this.flightNum = flightNum;
	this.price = price;
	neighbors = new LinkedList<String>();

    }

    //instance methods
    public String getToCity(){
	return toCity;
    }

    public LinkedList<String> getNeighbors(){
	return neighbors;
    }

    public String getFromCity(){
	return fromCity;
    }


    public int getFlightNum(){
	return flightNum;
    }

    public int getPrice(){
	return price;
    }
    public GraphNode addNewNode( String fCity, String tCity){
	neighbors.addFirst(tCity);
	GraphNode flight = new GraphNode(fCity, neighbors);
	return flight;
    }
    
    public GraphNode  addNeighbor(String neighbor){
	neighbors.addFirst(neighbor);//stores neighbor on list
	GraphNode flight = new GraphNode(fromCity, neighbors);
	return flight;//returns an updated node
    }

    public static Flights fromLine(String Line){
	String[] temp = Line.split(" ");
	String fCity = temp[0];
	String tCity = temp[1];
	int number = Integer.decode(temp[2]);
	int cost = Integer.decode(temp[3]);
	return new Flights(fCity,tCity,number,cost);
    }
    
    public boolean matchFlight(String start, String goal){
	if(start.equals(this.getFromCity())&& goal.equals(this.getToCity())){
	    return true;
	}else{ return false;
	}}
	
	
     public String toString(){
	 return "WA" + flightNum + " from " +WellsAir. matchACity(fromCity)+ " to " +WellsAir. matchACity(toCity) +" ";
    } 

    //  public LinkedList<GraphNode> getFlights(String fileName) throws FileNotFoundException{






    //	}
}
