import java.util.*;

public class Queue_1<E> implements QueueInterface<E> {

    // new class to implement the Queue abstract data type
    
    // instance variables to indicate the front and back 
    // of the linked list, which stores the contents of the queue

    private ListNode<E> front, back;  // Initially contain null by default
    
    // constructor
    
    public Queue_1 () {
	front = null;  // Redundant since "front" is null by default
	back = null;  // Redundant since "back" is null by default
    }

    // instance methods
  
    public boolean empty () {
	return (front == null);
	// By design, queue is empty if "front" is empty
    }

    public void enQ (E newItem) {
	// adds a new item to the back of the queue
	if (empty()) {
	    // Enqueueing onto the empty list is a special case
	    front = new ListNode<E>(newItem, null);
	    back = front;
	} else {
	    back.next = new ListNode<E>(newItem, null);
	    back = back.next;
	}
    }

    public E deQ () throws QueueException {
	// removes and returns an item from the front of the queue
3	if (empty())
	    throw new QueueException("Attempt to deQ() empty queue");
	else {
	    E result = front.value;
	    front = front.next;
	    return result;
	}
    }

    public E front () throws QueueException {
	// returns item at the front of the queue without removing it
	if (empty())
	    throw new QueueException("Attempt to get front() of empty queue");
	else
	    return front.value;
    }

    public int size () {
	// returns the number of items currently on the queue
	int len = 0;
	for (ListNode<E> current = front; current != null; current = current.next)
	    len++;
	return len;
    }

    public void clear () {
	front = null;
    }

    public String toString () {
	// returns a String representation of the contents of the queue
	String S = "[";
	if (!empty()) {
	    S = S + front.value;  // Treat first element specially, no comma in front
	    for (ListNode<E> current = front.next; current != null; current = current.next)
		S = S + ", " + current.value;
	}
	return S + "]";
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
