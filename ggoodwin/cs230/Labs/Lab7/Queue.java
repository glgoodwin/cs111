/** Defines a class, Queue, to implement the Queue abstract data type
 *
 */
import java.util.*;

public class Queue<E> {
  // MODIFICATION:  Sohie  Fall07
  //   added javadoc comments

  // instance variable to store the contents of the Queue
  private LinkedList<E> items;
  
  /** 
   *  Constructs a Queue
   *    using a linked list
   */
  public Queue () {
    items = new LinkedList<E> ();
  }

  /**
   * Determines if the queue is empty
   *
   *@return    true if queue is empty; false otherwise
   */
  public boolean empty () {
    return items.isEmpty();
  }

  /**
   *  Adds a new item to the back of the queue
   *  
   *@param  E newItem is the object of type E added to the back of the queue
   */
  public void enQ (E newItem) {
    items.addLast(newItem);
  }

  /**
   *  Removes and returns an item from the front of the queue
   *  
   *@return  the object of type E from the front of the queue
   *
   *@exception exception thrown if there are problems deq from the queue
   */
  public E deQ () {
    if (empty())
      throw new RuntimeException("Attempt to deQ() empty queue");
    else
      return items.removeFirst();
  }

  /**
   *  Returns an item from the front of the queue
   *  without removing it
   *  
   *@return  the object of tyep E from the front of the queue
   *
   *@exception exception thrown if there are queue problems 
   */
  public E front () {
    if (empty())
      throw new RuntimeException("Attempt to get front() of empty queue");
    else
      return items.getFirst();
  }

  /**
   *  Returns the number of items currently on the queue
   *  
   *@return the count of items currently on the queue (an integer)
   *
   */
  public int size () {
    return items.size();
  }

  /**
   *  Clears all current items from the queue
   *  
   */
  public void clear () {
    items.clear();
  }

  /**
   *  Returns a string representation of the contents of the queue
   * @return a string representation of the contents of the queue
   */
  public String toString () {
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
