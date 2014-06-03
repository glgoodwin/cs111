/** 
Gabe Goodwin
MidTerm 2
WellsAir problem*/

/** reads in all the information and creates the neccesary ADTs for the process to work*/
import java.util.*;
import java.io.*;
import java.net.*;

public class WellsAir{
    //instance variables

    private static LinkedList<GraphNode> flightMap;
    private static LinkedList<Requests> requests;
    private static LinkedList<Flights> flights;
    private static LinkedList<Cities> cities;

    //constructor
    public WellsAir(){
	flightMap = new LinkedList<GraphNode>();
	requests = new LinkedList<Requests>();
	flights = new LinkedList<Flights>();
	cities = new LinkedList<Cities>();
	
    }

    public void readCities(String fileName)throws FileNotFoundException{
		System.out.println("getting cities");
	
	Scanner reader = new Scanner(new File(fileName));
	while(reader.hasNext()){
	    Cities g = Cities.fromLine(reader.nextLine());
	    cities.add(g);
    }
	reader.close();
    }

    public void readRequests(String fileName)throws FileNotFoundException{
       	Scanner reader = new Scanner(new File(fileName));
	while(reader.hasNext()){
	     System.out.println("getting requests");
	    String request = reader.nextLine();
	    Requests t = Requests.fromLine(request);
	    requests.add(t);
		}

	reader.close();
    }
    public void readFlights(String fileName)throws FileNotFoundException{
	/**stores FLight objects on a Linked List*/
       	Scanner reader = new Scanner(new File(fileName));
	LinkedList<String> nodes = new LinkedList<String>();
		System.out.println("getting flights");
	while(reader.hasNext()){
	    Flights p = Flights.fromLine(reader.nextLine());
	    flights.addFirst(p);
	}
	reader.close();
    }
    public static Flights  matchAFlight(String start, String goal){
	int cost = 0;
       	int i = 0;
	while(i< flights.size()-1){
      	    if(!flights.get(i).matchFlight(start,goal)){
			i++;}
	    else{
		break;}
	}
	return flights.get(i);
    }


    public static String matchACity(String Abbr){
	int i = 0;
	while(i< cities.size()-1){
      	    if(!cities.get(i).matchCity(Abbr)){
		i++;}
	    else{
		break;}
	}return(cities.get(i).getCity() + cities.get(i).getState());
    }

    public void createFlightMap(LinkedList<Flights> flights){
	System.out.println("creating FlightMap");
	LinkedList<String> nodes = new LinkedList<String>();
	int i = 0;
	while(i<flights.size()){
	    Flights p = flights.get(i); 
       if(nodes.contains(p.getFromCity())){//if this city already has a node need to find its  graphnode in the flights  list, and add a neighbor to it
	   System.out.println("there is already a node for this city");
	        int j = 0;
		GraphNode m = new GraphNode(null,null);
		while(i< flights.size()){
		    if(( flightMap.get(j).getLabel().equals(p.getFromCity()))){
			m = flightMap.get(j); //sets m to the GraphNode for that city
			System.out.println(j);
			break;
		    }else{ 
			   j++;
		    }
		}
		System.out.println(p.getToCity());
		GraphNode s = m.addNeighbor(p.getToCity()); // adds the new neighbor
		flightMap.set(j,s);//replaces the old graphnode with the new one with more neighbors
		System.out.println(s);
			System.out.println(s.getNeighbors());
		i++;
       }else{//if there is not a node for that city yet
		GraphNode r =  p.addNewNode(p.getFromCity(),p.getToCity());//creates a new graph node
		nodes.add(p.getFromCity());// adds the from city to the list of cities with graph nodes
		flightMap.add(r);// adds the graph node to the linked list
		i++;
			System.out.println(r);
       }}}

	public static void main(String[]args)throws FileNotFoundException{
	    WellsAir bob = new WellsAir();
	    bob.readFlights("flights.txt");
	    bob.readCities("cities.txt");
	    bob.readRequests("requests.txt");
	    bob.createFlightMap(flights);
	    int i = 0;
	    while(i<requests.size()){
		FlightSearch.searchGraph(flightMap,(requests.get(i).getFromCity()),(requests.get(i).getToCity()));
		i++;
	    }
										 
										 
	  
	    

	}}