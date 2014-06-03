/** Gabe Goodwin
    11/30/2010
    Lab 12
    Task.java
**/


public class Task implements Comparable<Task>{

    //instance variables
    private String name;
    private int timeNeeded;
    private int timeIn;
    private int priority;
  

    //constructor
    public Task(String name, int timeNeeded, int priority){
	this.name = name;
	this.timeNeeded = timeNeeded;
	this.priority = priority;
	this.timeIn = 0; //initialize time in to 0, will be set by Scheduler class
    }//end constructor


    public int getPriority(){
	return priority;
    }

    public int getTimeNeeded(){
	return timeNeeded;
    }

    public int getTimeIn(){
	return timeIn;
    }

    public void setTimeIn(int w){
	timeIn = w;
    }
    
    public String getName(){
	return name;
    }
	

	//compareTo
    public int compareTo(Task t){
	if(this.getPriority() == t.getPriority()){
	    return 0;
	}else if( this.getPriority() > t.getPriority()){
	    return 1;
	}else{
	    return -1;
	}}


    public static void main(String args[]){
	Task a = new Task("a", 2, 1);
	Task b = new Task("b", 2, 3);
	System.out.println(a.compareTo(b));//return -1 
	System.out.println(b.compareTo(a));//return 1
	System.out.println(b.compareTo(b));//return 0
    }
    
    public String toString(){
	return (name + " -" + getTimeNeeded() + " -priority " + getPriority());
    }

}//end Task class