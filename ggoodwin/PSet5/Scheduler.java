/** Gabe Goodwin and Elise Dong
 * CS230 Assignment 5
 * QueueStacks.java
 * October 28th, 2010
*/

public class Scheduler{
    /** defines a class that reads student names and courses in from a file and
	tries to enroll them in the courses.*/
    
    LinkedList<Students> requests;
    LinkedList<Courses> classes;

    public Scheduler(String filenameStudents, String fileNameCourses) throws FileNotFoundException{
	/** while the reader has a next line for the first file it makes them
 into new Student objects and stores them in requests.
 Reads all courses from the text file of course catalog and stores in classes.*/
    }

    public static void main(String[] args) {
	/**Enrolls all students in requests in all available classes.
	   Uses methods in Students class to check for any problems
	   Then uses methods in Courses to enroll the student.

	   In order to print out students in a given course, use loop to print
           out Student info (using Students' toString method) for all
	   Students in enrolledStudents.

	   In order to print out courses a given student is taking,
	   print out the courses instance variable in Students, as it will
	   not contain any conflicting/full classes.*/
    }

}