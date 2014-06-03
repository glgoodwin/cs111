/* Gabrielle Goodwin
PSet 3
StockInfo 
Not quite complete*/



import java.io.*; //Java IO package
import java.util.*; //Allows use of linked List and Scanner
import java.net.*; //Allows use of URL



public class StockInfo{
    //instance variables
    private String symbol;
    private LinkedList<StockQuote> stocks;
    private int numQuotes;
    private LinkedList<StockQuote> sorted;
    
    //constructor
    public StockInfo(java.lang.String symbol)throws IOException{	
	this.symbol = symbol;
	stocks = getFromWeb(symbol);
	numQuotes = stocks.size();
	sorted = sortQuotes(symbol);
    }
    public LinkedList<StockQuote> getFromWeb(String symbol){// reads in from webpage and puts into a LL
	LinkedList<StockQuote> q = new LinkedList<StockQuote>();
      String url = "http://ichart.finance.yahoo.com/table.csv?s="+symbol+"&a=0&b=1&c=1900&d=0&e=1&f=2020&g=d&ignore=.csv"; 
	    try{
	    URL u = new URL(url);
	    Scanner reader = new Scanner(u.openStream());
	    reader.nextLine();//reads in first line of labels
	    int i = 0;
	    while(reader.hasNext()){
		StockQuote v = StockQuote.fromLine(reader.nextLine());
		      q.add(i,v);
		      i++;
		  }
		  reader.close();
		  return q;}
		catch(IOException ex ){
		    System.out.println(ex);
			}
	    return null;//if there is an exception I suppose
		     
    }

    public  LinkedList<StockQuote> sortQuotes(String symbol){//helper

	LinkedList<StockQuote> q = new LinkedList<StockQuote>();
      String url = "http://ichart.finance.yahoo.com/table.csv?s="+symbol+"&a=0&b=1&c=1900&d=0&e=1&f=2020&g=d&ignore=.csv"; 
	    try{
	    URL u = new URL(url);
	    Scanner reader = new Scanner(u.openStream());
	    reader.nextLine();//reads in first line of labels
	    int i = 0;
	    while(reader.hasNext()){
		StockQuote v = StockQuote.fromLine(reader.nextLine());
		if(q.size()==0){
		    q. add(0,v);}
		else{
		    int j = 0;
		int r =  v.compareTo(q.get(j));
		if( r==1){
		    j++;
		} else if(j==q.size()){
		      q.addLast(v);
		  }else{
		    q.add(j,v);
		}}}
		  reader.close();
		  return q;}
   	catch(IOException ex ){
		    System.out.println(ex);
			}
	    return null;//if there is an exception I suppose
    }
		
    public java.lang.String getSymbol(){
	return symbol;
    }

    public CS230Date firstDate(){
	StockQuote l = stocks.getLast();
	return l.getDate();
    }

    public CS230Date lastDate(){
	StockQuote f = stocks.getFirst();
	return f.getDate();
    }
    
    public int numQuotes(){
	return numQuotes;
    }

    public StockQuote getQuote(CS230Date date){	
	if((date.compareTo(this.lastDate())>0)) return null;// returns null if before 1sr date
	else if ((date.compareTo(this.firstDate())<0)) return null;//or if after last
	else for(int i = 0; (date.compareTo(stocks.get(i).getDate())<=0); i++)
	    return stocks.get(i-1);//either returns the same date, or one right before
	return stocks.getLast();//if the date before is the last in the list
    }

    public java.util.LinkedList<StockQuote> getMaxQuotes(int n){
	LinkedList<StockQuote> max = new LinkedList<StockQuote>(); //creates a new LL
	if(n>sorted.size()){ 
	    return sorted;
	}else{
	    for(int i= 0;i<n; i++){
		StockQuote g = sorted.get(i);
		max.addLast(g); //stores highest #s in back
	    }return max;
	}}
       
    public java.util.LinkedList<StockQuote> getMinQuotes(int n){ //create new LL
	LinkedList<StockQuote> min= new LinkedList<StockQuote>();
	if(n>sorted.size()){
	    return sorted;
	}else{
	     int m = n;
	    for(int i = (sorted.size()-1); i>=(sorted.size()-m); i--){
		min.addFirst(sorted.get(i));//puts smallest #s in front
	    }return min;
	    }

	//helper method for dates
	public void getDates(CS230Date start,CS230Date end){
	    int i = stocks.get(getQuote(start));
	    while(i != stocks.get(getQuote(end))){
		system.out.println(stocks.get(i));
		i++;
	    }}
       
    public java.lang.String toString(){
	
	return "StockInfo: " + symbol + " " + numQuotes + " between " + this.firstDate() +" and " + this.lastDate();

		    
    }

    public static void main(java.lang.String[] args)throws IOException{

	StockInfo phil = new StockInfo("GOOG");
	System.out.println(phil);
	System.out.println(phil.getMaxQuotes(75));
	
	Scanner reader = new Scanner(System.in);
	String line = "";
	System.out.println("GOOG> ");
	line = scan.nextLine(); //read in user input
	
	while(!(line.equals("quit")){
		  if(line.equals( "max " + int n)){
		      System.out.println(phil.getMaxQuotes(n));
		  }else if(line.equals("min " +int n)){
		      System.out.prontln(phil.getMinQuotes(n));
		  }else if(line.equals(CS230Date.fromString(line)){
			       phil.getQuote(CS230Date.fromString(line));
			   }else if(line.equals(CS230Date.fromString(line)+ " " +CS230Date.fromString(line))){
			       System.out.println(phil.getDates(start,end));
			   }else System.out.print("Type one date (YYYY-MM-DD) in the range [2004-08-19, 2010-10-01]\n
                                                   to get the nearest quote for that date.\n
                                                   Type two dates to get a listing of all quotes between those dates\n.
                                                   Type "max n" (where n is an integer) to get the n largest daily closing prices.\n
                                                   Type "min n" (where n is an integer) to get the n smallest daily closing prices.\n
                                                   Type "quit" to exit this interactive program.\n");
			   }}
			       
			       
				   
			       
	



    }

}
			 

   