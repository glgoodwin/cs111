/***Gabe Goodwin
    Final Project
    StockQuote.java
 

This class reads in the Stocks from the web and creates a StockQuote object. This class is taken from the Problem Set done earlier in the year.
**/

import java.util.*;
import java.io.*;
import java.net.*;

public class StockQuote{

    private CS230Date date;
    private double price;

    /** creates a new StockQuote with a date and price associated with it **/
    public StockQuote (CS230Date date, double price) {
	this.date = date;
	this.price = price;
    }

    /** Returns the date **/
    public CS230Date getDate(){
	return date;
    }

    /** Returns the price**/
    public double getPrice(){
	return price;
    }

    /** Returns true if the stock has the same date and price as the input stock**/

    public boolean equals (StockQuote q){
	return( q.getDate().equals(date)) 
	    &&(q.price == price);
    }



    /**Compares two stocks by price, and then by date**/
    public int compareTo (StockQuote q){
	if(this.price < q.getPrice()){
	    return -1;
	}else if(this.price > q.getPrice()){
	    return 1;
	}else{
	    return this.getDate().compareTo(q.getDate());
	}
    }

    /** Returns string representation of quote **/
    public String toString(){
	return date.toString() + "\n" + price;
    }


    /** creates a StockQuote object froma line of text **/
    public static StockQuote fromLine (String line){
	String[] columns = line.split(",");
	CS230Date stockDate = CS230Date.fromString(columns[0]);
	double acp = Double.parseDouble(columns[columns.length - 1]);

	return new StockQuote(stockDate, acp);
    }
}
    
