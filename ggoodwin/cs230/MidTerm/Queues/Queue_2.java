import java.util.*;

public class Queue_2<E> implements QueueInterface<E> {

  // new class to implement the Queue abstract data type

  // instance variable to store the contents of the Queue
  // in a LinkedList

  private LinkedList<E> items;
  
  // constructor

  public Queue_2 () {
    items = new LinkedList<E> ();
  }

  // instance methods
  
  public boolean empty () {
    // returns true if queue is empty
    return items.isEmpty();
  }

  public void enQ (E newItem) {
    // adds a new item to the back of the queue
    items.addLast(newItem);
  }

  public E deQ () throws QueueException {
    // removes and returns an item from the front of the queue
    if (empty())
      throw new QueueException("Attempt to deQ() empty queue");
    else
      return items.removeFirst();
  }

  public E front () throws QueueException {
    // returns item at the front of the queue without removing it
    if (empty())
      throw new QueueException("Attempt to get front() of empty queue");
    else
      return items.getFirst();
  }

  public int size() {
    // returns the number of items currently on the queue
    return items.size();
  }

  public void clear () {
    // clears all current items from the queue
    items.clear();
  }

  public String toString () {
    // returns a String representation of the contents of the queue
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