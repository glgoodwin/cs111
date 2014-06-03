/** Gabe Goodwin
    11/30/2010
    Lab 12
    SchedulerDriver.java
**/

public class SchedulerDriver{
    



    public static void main(String args[]){
	Scheduler s = new Scheduler();
	s.add(new Task("Watch TV", 60, 4));
	s.add(new Task("Work", 300, 3));
	s.add(new Task("Eat Dinner", 30, 2));
	s.runTask();
	s.runTask();
	s.add(new Task("Do Homework", 120, 5));
	s.add(new Task("Clean Room", 30, 1));
	s.runTask();
	s.runTask();
	s.add(new Task("Drink Coffee", 15, 5));
	s.runTask();
	s.runTask();
	s.runTask(); 

    }
		
	      



}//end SchedulerDriver