/** Gabe Goodwin and Elise Dong
 * CS230 Assignment 5
 * QueueStacks.java
 * October 28th, 2010
*/

/** 
    defines a class of course objects to be used in the student class*/

public class Courses{
   protected String name;
   protected LinkedList<Students> enrolledStudents;  
   protected LinkedList<String> meetingTimes;
   protected int maxEnroll;
   protected LinkedList<Students> waitList;
   protected int enrollment;

    public Courses(String course){
	/** 
	    sets the first part of the line to the name, the second part
	    to the maxEnroll and the last 2 or 3 to the meetingTimes. EnrolledStudents is empty and enrollment is 0*/}

    public boolean conflict(Course one, Course two){
	/** Checks the meeting times for the two courses and sees if they
	    over lap returns an error message if there is a course conflict*/

    }
    
    public String getName(){
	/** returns name of the course */
    }
    public LinkedList<Students> getEnrolledStudents(){
	/** returns the list of students in the class*/
    }
    public int enrollment(){
	/** returns number of students enrolled in the class*/

    public LinkedList<String> getmeeingTimes(){
	/** returns the meeting times for the course */
    }

    public int getMaxEnroll(){
	/** returns the max enrollment for the class*/
    }

    public boolean isFull(Course course){
	/** returns true if the course is full*/
    }
    
    public LinkedList<Students> getWaitList(Course course){
	/** returns who is on the waiting list for the givein course */
    }
    public void addStudent(Students bob){
	/** adds bob to this class. Name is added to the enrolledSTudents 
	    linked List and enrollment is incremented by 1*/

    }

    public String toString() {
	/**Returns string representation of course */
    }
}


	