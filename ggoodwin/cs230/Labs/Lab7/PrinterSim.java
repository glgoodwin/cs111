/**
 * PrinterSim.java
 * 
 */
import java.io.*;
import java.util.*;

public class PrinterSim {

    // code to simulate a printer queue

    // instance variables

    private double probJob;      // probability of request arriving
    private int totalTime;       // total time *steps* in simulation
    private LinkedList<PrintJob> jobs; // LinkedList to hold print job objects
    private int  totalWait;  //keeps track of the total time that has been waited
    private int numJobs;     // number of jobs processed
    private int waitTime;    //wait time for a particular job
    private int timeLeft;    //time left to print out a particular job
    private int currentTime;
    private Queue<PrintJob> jobsQ; //printier Q
    private int arrivalTime;  //when the job arrives to the Q
   

  /**
   * Creates a PrinterSim object to run a simulation of a printer queue
   *
   * @param fileName  the text file containing items for the printer queue
   * @param probability the probability that a new item will be added to the queue (0-1)
   * @param time the number of time cycles to run the simulation (here, once cycle = 5 secs)
   * @return a PrinterSim object
   *
   */
    public PrinterSim (String fileName, double probability, int time) {
	probJob = probability;
	totalTime = time;
	jobs = new LinkedList<PrintJob>();
	totalWait = 0;
	numJobs = 0;
	waitTime = 0;
	timeLeft = 0;
	currentTime = 0;
	jobsQ = new Queue<PrintJob)();
        arrivalTime = 0;


	// Read data from file into memory


    }


  /**  
   *
   * Reads in the printer queue items from a text file.  Each line in the
   * text file is assumed to be of the form "username filename pages".
   * This method uses split() to parse the string, so it is important that
   * each word in the file is separated by a single space.
   *
   *  @param fileName  the name of the text file containing the printer queue items
   */
  private void readData (String fileName) {
    // reads information about print requests from a text file and places
    // the information in a data structure in memory
    
    try {             // set up file for reading line by line
      FileReader fr = new FileReader(fileName);
      BufferedReader br = new BufferedReader(fr);
      String S = br.readLine();      // get first line in the file
      String [] printInfo = null;

      while (S != null) {
	// if S is not empty, assume it contains user, file and pages
	if (!S.equals(""))  {
	  printInfo = S.split(" ");	  
	  jobs.addLast(new PrintJob(printInfo[0],printInfo[1],Integer.parseInt(printInfo[2])));
	}
	S = br.readLine();        // read next line of file and create
      }
      fr.close();    // close file
    }
    catch (IOException e) {
      System.out.println("Error in reading data file: " +fileName);
    }
    
  }
    public void printFiles(){
    	if(jobsQ.isEmpty()){
	    this.timeStep();
	}else if(timeLeft = 0){
	    PrintJob b = jobsQ.deQ();
	    waitTime = (currentTime- arrivalTime)+ b.getPages();
	    totalWait = totalWait + waitTime;
	    //print out first page
	    this.timeStep();
	}else{// timeleft >0
	    //print next page of curren doc
	    this.timeStep();
	}}
	    

    }

    public void timeStep(){

	int a = math.random();
	if(a < probJob){// new printer job
	    PrintJob p = jobs.removeFirst();
	    jobsQ.enQ(p); // adds the printjob to the queue
	    arrivalTime = currentTime;
	    numJobs = numJobs++;
	  		}
	totalTime = totalTime--;
	currentTime = currentTime++;

    }
  
  
} // PrinterSim
 