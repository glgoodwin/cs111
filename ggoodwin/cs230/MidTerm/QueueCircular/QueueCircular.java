/** Gabe Goodwin
    MidTerm 2
    Queue Circular.java
*/


public class QueueCircular<E> implements QueueInterface<E> {
    // a circular LinkedList is used to store a queue


    //instance variable
    private ListNode<E> back; //null by default

    // static variables
    private int size;
    //constructor
    public QueueCircular(){
	size = 0;
	back = null; 
    }

    //Instance methods
    public boolean empty(){
	return (back == null);
    }
    
    public void enQ(E newItem){
	if(empty()){
	    E first = newItem;
	    back = new ListNode(newItem,back);
	    size = size +1;
	}else{
	    back.next = new ListNode(newItem, back.next);
	    back = back.next;
	    size = size +1;
	}}

    public E deQ()throws QueueException{
	if(empty()){
	    throw new QueueException("Attempt to deQ()empty queue");
	}else{
	E first = back.next.value;
	back.next.next = back.next;
	size = size -1;
	return first;
	}}
    
    public E front()throws QueueException{
	if(empty()){
	    throw new QueueException("Attempt to get front of empty Queue");
	}else{
	    return back.next.value;
	}
		  
    }

    public int size(){
	return size;
    }

    public void clear(){
	back.next = null;
	back = null;
    }

    public String toString(){
	    if (size() > 0) {
      String S = "[" + front();
      enQ(deQ());
      for (int i = 1; i < size(); i++) {
	S = S + ", " + front();
	enQ(deQ());
      }
      return S + "]";
    }
    return "[]";
  }
    }


// A class to represent one item in a list
class ListNode<E> {

    // Simplify things by using public instance variables
    public E value;
    public ListNode<E> next;
    
    public ListNode(E value, ListNode<E> next) {
	this.value = value;
	this.next = next;
    }
}
