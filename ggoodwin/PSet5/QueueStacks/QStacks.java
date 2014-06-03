/** Gabe Goodwin and Elise Dong
 * CS230 Assignment 5
 * QueueStacks.java
 * October 28th, 2010
*/

import java.util.*;

public class QStacks<E>{

    //instance variables
    Stack<E> firstHalf;
    Stack<E> secHalf;
    int size;

    //constructor
    public QStacks(){
	firstHalf = new Stack<E>();
	secHalf = new Stack<E>();
	size = 0;
    }
    
    public boolean empty(){
	return firstHalf.isEmpty() && secHalf.isEmpty();

    }

    public void enQ (E newItem){
	secHalf.push(newItem);
	size++;
    }

    public E deQ() throws QueueException{
	if(firstHalf.isEmpty()){
	    while (!secHalf.isEmpty()) {
		firstHalf.push(secHalf.pop());
	    }
	}
	size--;
	return firstHalf.pop();


    }

    public E front() throws QueueException{
	if(firstHalf.isEmpty()){
	    while (!secHalf.isEmpty()) {
		firstHalf.push(secHalf.pop());
	    }
	}
	    return firstHalf.peek();
    }

    public int size() {
	return size;
    }

    public void clear() {
	firstHalf = new Stack<E>();
	secHalf = new Stack<E>();
	size = 0;
    }

    public static void main(String[] args) {
	QueueStacks<String> george = new QueueStacks<String>();
	george.enQ("1");
	george.enQ("2");
	george.enQ("3");
	System.out.println(george.deQ()); // returns 1
	System.out.println(george.front()); // returns 2
	System.out.println(george.size()); // returns 2
	System.out.println(george.deQ()); //returns 2
	george.clear();
	System.out.println(george.size()); // returns 0
    }
    

}