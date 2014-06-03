/** Gabe Goodwin
    11/30/2010
    Lab 12
    Scheduler.java
**/

public class Scheduler{

    private PriorityQueue<Task> tasks;
    private int clock;

    public Scheduler(){
	tasks = new PriorityQueue<Task>();
	clock = 0; 

    }

    public void setClock(int i){
	clock = i;
    }


    public void add(Task t){
	t.setTimeIn(clock);
	tasks.enqueue(t);
    }


    public void runTask(){
	if(!tasks.isEmpty()){
	    System.out.println("Task Queue Contents: "+ tasks);
	    Task r = tasks.dequeue();
	    System.out.println("running "+ r.getName() + " for " +
			       r.getTimeNeeded() + " minutes.");
	    clock = clock + r.getTimeNeeded();
	    System.out.println(r.getName() + " was in the system for " + 
			       (clock - r.getTimeIn()) + " cycles.");
	    System.out.println("The clock now reads " + clock + " cycles.");
	    System.out.println("********");
	}
    else{
	System.out.println("There are no more tasks left to run.");

	
    }
    }

}
    
