public interface QueueInterface<E> {

  public boolean empty ();
    // returns true if queue is empty
    
  public void enQ (E newItem);
      // adds a new item to the back of the queue

  public E deQ() throws QueueException;
    // removes and returns an item from the front of the queue

  public E front() throws QueueException;
    // returns item at the front of the queue without removing it

  public int size ();
    // returns the number of items currently on the queue

  public void clear ();
    // clears all current items from the queue

  public String toString ();
  
}
