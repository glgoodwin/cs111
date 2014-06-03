/**  CS230 
  *   FILE: Webpage.java
  *   WRITTEN BY: Takis Metaxas 
  *   MODIFIED  Sohie  

/** A simple Webpage object, keeps track of the webpage's URL and the number
 * of lines in the webpage.
 */
public class Webpage {
	
    // instance variables
    private String URLname;
    private int linecounter;
    
    // constructor 
    public Webpage (String URLname) {
	this.URLname = URLname;	
	this.linecounter = 0;
    }
    
    // getters and setters
    public String getURLName () {
	return this.URLname;
    }
    
    
    public int getLineCounter () {
	return linecounter;	 	    
    }
    
    public void setURLName (String name) {
	URLname = name;
    }
    
    public void setLineCounter (int value) {
	linecounter = value;	 	    
    }
    
    public String toString () {
	String S =  URLname + "\t : " + linecounter + "\n";
	return S;
    }
    
    public static void main(String args[]){
	// some simple tests
	Webpage x = new Webpage("google");
	x.setLineCounter(100);
	System.out.println(x);
	
	Webpage y = new Webpage("wellesley");
	y.setLineCounter(250);
	System.out.println(y);
    }
    
}
