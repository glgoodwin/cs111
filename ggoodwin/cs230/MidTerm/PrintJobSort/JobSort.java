import java.util.*;

public class JobSort {

  // new class to maintain an ordered collection of PrintJob objects,
  // in order of increasing size, using both an array and a list

  private PrintJob[] jobsArray;     // array of PrintJob objects
  private int numJobs;              // number of PrintJob objects
                                           // stored in the array
  private LinkedList<PrintJob> jobsList;      // LinkedList of PrintJob objects

  // constructor

  public JobSort (int arraySize) {
    jobsArray = new PrintJob[arraySize];
    numJobs = 0;
    jobsList = new LinkedList<PrintJob>();
  }

  public void addJobArray (PrintJob newJob) {
    // adds a new PrintJob to jobsArray. Assumes that jobsArray initially
    // contains numJobs PrintJob objects ordered by size, and adds the new 
    // Printjob so that all of the PrintJob objects are still ordered
    if (numJobs == jobsArray.length) 
      System.out.println("array is full");
    else {
      int i = 0, j;
      int size = newJob.getPages();
      while ((i < numJobs) && (jobsArray[i].getPages() < size))
	i++;
      for (j = numJobs; j > i; j--)
	jobsArray[j] = jobsArray[j-1];
      jobsArray[i] = newJob;
      numJobs++;
    }
  }

  public void addJobList (PrintJob newJob) {
    // adds a new PrintJob to jobsList. Assumes that jobsList initially
    // contains PrintJob objects ordered by size, and adds the new 
    // Printjob so that all of the PrintJob objects are still ordered
    int i = 0;
    int size = newJob.getPages();
    while ((i < jobsList.size()) && (jobsList.get(i).getPages() < size))
      i++;
    if (i == jobsList.size())
      jobsList.add(newJob);
    else
      jobsList.add(i, newJob);
  }

  // main() method that contains testing code for the above methods

  public static void main (String[] args) {
    PrintJob P1 = new PrintJob("joe", "Queue.java", 10);
    PrintJob P2 = new PrintJob("jane", "Stack.java", 6);
    PrintJob P3 = new PrintJob("sally", "PrintJob.java", 8);
    PrintJob P4 = new PrintJob("betty", "GUIDemo.java", 2);
    PrintJob P5 = new PrintJob("jack", "Country.java", 12);
    JobSort test = new JobSort(10);
    test.addJobArray(P1);          // add jobs to jobsArray
    test.addJobArray(P2);
    test.addJobArray(P3);
    test.addJobArray(P4);
    test.addJobArray(P5);
    test.addJobList(P1);           // add jobs to jobsList
    test.addJobList(P2);
    test.addJobList(P3);
    test.addJobList(P4);
    test.addJobList(P5);
    int i;                         
    // print contents of jobsArray and jobsList
    System.out.println("Array contents:");
    for (i = 0; i < test.numJobs; i++)
      System.out.println(test.jobsArray[i]);
    System.out.println();
    System.out.println("List contents:");
    for (i = 0; i < test.jobsList.size(); i++)
      System.out.println(test.jobsList.get(i));
  }

}

