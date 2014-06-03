public class PrintJob {

  // class for representing a single print request

  // instance variables

  private String userName;
  private String fileName;
  private int pages;
  private int requestTime;   // time when print request is made
  
  // constructor
  
  public PrintJob (String user, String file, int size) {
    userName = user;
    fileName = file;
    pages = size;
    requestTime = 0;
  }
  
  // instance methods to access the instance variables
  
  public String getUser () {
    return userName;
  }
  
  public String getFile () {
    return fileName;
  }
  
  public int getPages () {
    return pages;
  }
  
  public int getTime () {
    return requestTime;
  }

  public String toString () {
    // returns a String representation of the contents of a PrintJob
    return userName + " " + fileName + " " + pages;
  }

  // instance method to set the time of a print request
  
  public void setTime (int time) {
    requestTime = time;
  }
  
}
