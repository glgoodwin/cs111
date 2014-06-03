public interface MaxHeapInterface<V> {

  public boolean isEmpty ();
    // returns true if heap is empty
    
  public void insert (PQueueEntry<V> newItem);
      // adds a new item to the heap

  public PQueueEntry<V> delete() throws RuntimeException;
    // removes and returns the root of the heap

  public PQueueEntry<V> max() throws RuntimeException;
    // returns root (without removing it) from the heap

  public int size ();
    // returns the number of items currently in the heap

  public void clear ();
    // clears all items from the heap

  public String toString ();
  
}
