/** Gabe Goodwin and Elise Dong
 * CS230 Assignment 5
 * QueueStacks.java
 * October 28th, 2010
*/

/** defines s class of Student objects to be used in the Scheduler class*/

public class Students{
    String name;
    LinkedList<Courses> courses;
    


    public Students(String student){
	/** creates a new student object form the String read in the Scheduler
	    class.Splits string by spaces and creates a linked List of their crequested classes. */
    }

    public String getName(){
	/** returns the name of the student*/
    }

    public LinkedList<Courses> getCourses(){
	/** returns the courses that the student requested*/
    }

    public void areFull(){
	/** takes each class from the linked list and checks to see if they are     full using the isFull method in Courses.
	    if a class is full, takes the class off of the linked List and 
	    returns  the new list.Adds them to the waitlist changes the courses instance variable*/}
  
    public void checkConflicts(){
	/** uses the conflict method 3 times in courses to check if the courses conflict each other. if conflict is true, takes the second course off of the list and checks the others. changes the courses instance variables*/
    }
    public void enrollInCourses(){
	/** uses addStudent to enroll the student in each of the classes on their linked list*/
}
    
 
    public String toString() {
	/**Returns string representation of Student */
    }
}
    

		      