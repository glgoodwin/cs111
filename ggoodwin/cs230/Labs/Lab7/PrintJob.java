/** Defines a class, PrintJob, to represent a print request to a printer
 *
 *
 */
public class PrintJob {

    private String userName;
    private String fileName;
    private int pages;
    private int requestTime;   // time when print request is made


  /** 
    * Constructs a PrintJob object
    *
    * @param String user is a string representation of the user requesting the print job
    * @param String file is the name of the file to be printed in the simulation
    * @param int size is the integer number of pages in the simulated item to be printed
    *
    */
    public PrintJob (String user, String file, int size) {
	userName = user;
	fileName = file;
	pages = size;
	requestTime = 0;
    }

  /** 
   *  Returns the username of the person submitting the print request
   *
   * @return String representation of the user submitting the print request (e.g. slee)
   */
    public String getUser () {
	return userName;
    }

  /** 
   *  Returns the name of the file to be printed
   *
   * @return String representation of the name of the file to be printed (e.g. Deck.java)
   */
    public String getFile () {
	return fileName;
    }

  /** 
   *  Returns the integer count of the number of pages in document to be printed
   *
   * @return integer count of the number of pages in document to be printed (e.g. 4)
   */
    public int getPages () {
	return pages;
    }

  /** 
   *  Returns the integer time cycle when the simulated print request reaches the printer
   *
   * @return  the integer time cycle when the simulated print request reaches the printer
   */
    public int getTime () {
	return requestTime;
    }

    // instance method to set the time of a print request

  /** 
   *  This method sets the time of a print request (where time is in counted in steps
   *  with one simulation step equivalent to the time it takes to print one page)
   *
   * @param int time  The time step of the simulation, which ranges from 0 to a positive integer (e.g. 200)
   */
    public void setTime (int time) {
	requestTime = time;
    }

}
