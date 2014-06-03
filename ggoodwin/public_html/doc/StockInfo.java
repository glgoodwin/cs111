/**Gabe Goodwin
    Final Project
    StockQuote.java


This class mangages StockQuote objects. it has been taken from the problem set done earlier in the year.
**/
import java.io.*;
import java.util.*;
import java.net.*;

public class StockInfo{

    private String symbol;
    private LinkedList<StockQuote> quotes;
    private LinkedList<StockQuote> quotesByPrice;

    /** Constructor **/
    public StockInfo (String symbol){
	this.symbol = symbol;
	this.quotes = new LinkedList<StockQuote> ();
	String yahooString = 
	    "http://ichart.finance.yahoo.com/table.csv?s=" + symbol + 
	    "&a=0&b=1&c=1900&d=0&e=1&f=2020&g=d&ignore=csv";
	try{
	    Scanner reader = new Scanner((new URL(yahooString)).openStream());
	    String line = reader.nextLine();
	    while (reader.hasNext()){
		quotes.add(StockQuote.fromLine(reader.nextLine()));
	    }
	}catch (IOException ex){
	    throw new RuntimeException("There is no stock with symbol" + symbol);
	}
	sortQuotesByPrice();
    }
    /**Sorts the quotes by price rather than date**/
    private void sortQuotesByPrice(){
	for(int i=0; i<quotes.size(); i++){
	this.quotesByPrice.add(quotes.get(i));
	}

	for(int i=0; i<quotesByPrice.size()-1; i++){

	    int smallestIndex = i;
	    StockQuote smallestQuote = quotesByPrice.get(i);
	    for(int j=i+1; j<quotesByPrice.size(); j++){
		if(smallestQuote.compareTo(quotesByPrice.get(j)) >= 0){
		    smallestQuote = quotesByPrice.get(j);
		    smallestIndex = j;
		}
	    }

	    StockQuote tempQuote = quotesByPrice.get(i);
	    quotesByPrice.set(i, smallestQuote);
	    quotesByPrice.set(smallestIndex, tempQuote);
	}
    }

    /** returns the Symbol for this stock**/
    public String getSymbol(){
	return symbol;
    }
    /** returns the date of the first available stock quote**/
    public CS230Date firstDate(){
	if(quotes.size() == 0){
	    return CS230Date.MAX_DATE;
	}else{
	    return quotes.getLast().getDate();
	}
	
    }
    /**returns the number of quotes for the stock**/
    public int numQuotes(){
	return quotes.size();
    }
    /**Finds and returns a quote based on a date**/
    public StockQuote getQuote (CS230Date date){
	if((date.compareTo(firstDate())<0) || (date.compareTo(lastDate()) >0)){
	       return null;
	}else{
	    for(int i =0; i<quotes.size(); i++){
		return quotes.get(i);
	    }
	}
	return null;
    }
    /** Returns a LinkedList of the n largest quotes**/
    public LinkedList<StockQuote> getMaxQuotes(int n){
	int numMaxQuotes = Math.min(n, quotesByPrice.size());
	LinkedList<StockQuote> maxQuotes = new LinkedList<StockQuote>();
	for (int i = 0; i<numMaxQuotes; i++){
	    maxQuotes.add(quotesByPrice.get(quotesByPrice.size()-1-i));
	}
	return maxQuotes;
    }
    /**returns a LinkedList of the n smallest quotes**/
    public LinkedList<StockQuote> getMinQuotes(int n){
	int numMinQuotes = Math.min(n, quotesByPrice.size());
	LinkedList<StockQuote> minQuotes = new LinkedList<StockQuote>();
	for(int i = 0; i<numMinQuotes; i++){
	    minQuotes.add(quotesByPrice.get(i));
	}
	return minQuotes;
    }
    /** returns a String representation of the stock between 2 dates**/
    public String toString(){
	return "[StockInfo: (" + symbol + "), "
	    + numQuotes() + "quotes between "
	    + firstDate() + " and " + lastDate() + "]";
    }
    /** returns a string stating the range of dates available for this stock**/
    private String range(){
	return "[" + firstDate() + "," + lastDate() + "]";
    }
    /** Returns a CS230Date from an input String**/
    private static CS230Date stringToDate(String s){
	try{
	    return CS230Date.fromString(s);
	}catch (RuntimeException ex){
	    return null;
	}
    }
    /**gives user feedback in the linux window**/
    private static void usage (StockInfo info){
	System.out.println("Type one date (YYYY-MM-DD) in the range "
			   + info.range() + "\n to get the nearest quote for that date.\n"
			   + "Type \"max n\" (where n is an integer) to get the n largest"
			   + " daily closing prices.\n"
			   + "Type \"min n\" (where n is an integer) to get the n smallest"
			   + " daily closing prices.\n"
			   + "Type \"quit\" to exit this interactive program.");
    }
  /**gives user feedback in the linux window**/
    private static void usage_max(int n, StockInfo info){
	if(n < 0){
	    usage(info);
	}else{
	    LinkedList<StockQuote> maxQuotes = info.getMaxQuotes(n);
	    System.out.println("The " + maxQuotes.size() + " largest daily closing prices for " +
			       info.getSymbol() + " are:");
	    for(int i=0; i<maxQuotes.size(); i++){
		System.out.println(maxQuotes.get(i));
	    }
	}
    }

    private static void usage_min(int n, StockInfo info){
	if(n < 0){
	    usage(info);
	}else{
	    LinkedList<StockQuote> minQuotes = info.getMinQuotes(n);
	    System.out.println("The "+ minQuotes.size() + " smallest daily closing prices for " +
			       info.getSymbol() + " are:");
	    for (int i=0; i< minQuotes.size(); i++){
		System.out.println(minQuotes.get(i));
	    }
	}
    }
  /**gives user feedback in the linux window**/
    private static void usage_OneDate (String s, StockInfo info) {
	CS230Date date = stringToDate(s);
	if(date == null){
	    usage(info);
	}else{
	    StockQuote q = info.getQuote(date);
	    if(q==null){
		System.out.println("The date " + date + "is outside the range "
				   + info.range() + " of known quotes.");
	    }else{
		System.out.println(q);
	    }
	}
    }
  /**gives user feedback in the linux window**/
    private static void usage_TwoDates(String s1, String s2, StockInfo info){
	CS230Date start = stringToDate(s1);
	CS230Date stop = stringToDate(s2);
	if((start == null) || (stop == null)){
	    usage(info);
	}else{
	    for(CS230Date date = start; date.compareTo(stop)<= 0; date = date.next()){
		StockQuote q = info.getQuote(date);
		if((q != null) && (q.getDate().compareTo(date) == 0)){
		    System.out.println(q);
			}
	    }
	}
    }

    /** Returns the average for a range of dates **/
    public double getAverage(CS230Date date1, int i){
	//will return the average of the stocks from the start date to i days 
	//before the given date
	//I can use this method to build upon and create my MACD method
	double totalPrices;
	int numStocks = i;
	while(i != 0){
	   StockQuote q = getQuote(date1);
	   totalPrices = totalPrices + q.getPrice();
	   date1 = date1.prev();
	   i--;
	}
	//when all the prices have been added together
	double average = (totalPrices/i);
	return average;

    }
	
    /** REturns an int that will be used to determine whether the stock is a buy or sell. 1 = buy, -1 = sell**/
    public int getSignal(CS230Date date){
	//returns an int representing the buy signal for a given date
	//uses MACD to calculate this signal.
	//MACD: (12-day EMA - 26-day EMA) 
	//Signal Line: 9-day EMA of MACD
	double signalLine = getAverage(date,9);
	double twelveDay = getAverage(date,12);
	double twentySixDay = getAverage(date,26);

	double MACD = (twelveDay - twentySixDay);

	if(MACD > signalLine){
	    return 1; //if the signal should be buy
	} else if( MACD <= signalLine ){
	    return -1; //if the signal should be sell
	}
    }
	


}


		



		    















