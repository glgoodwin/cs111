import java.util.*;

public class MaxHeap<V> implements MaxHeapInterface<V> {

  // new class to implement the MaxHeap abstract data type

  // instance variable to store the contents of the MaxHeap
  // in a Vector

  private Vector<PQueueEntry<V>> items;
  
  // constructor

  public MaxHeap () {
    items = new Vector<PQueueEntry<V>> ();
  }

  // instance methods
  
  public boolean isEmpty () {
    // returns true if heap is empty
    return items.isEmpty();
  }

  public void insert (PQueueEntry<V> newItem) {
    // adds a new item to the heap

    // place the new item at the end of the heap
    items.add(newItem);
    // trickle new item up to its proper position
    int place = size() - 1;
    int parent = (place-1)/2;  // We subtract 1 because root is at index 0, not 1
    while ( (parent >= 0) && (items.get(place).priority().compareTo
			      (items.get(parent).priority()) > 0 )) {
      // swap item at index "place" with item at index "parent"
      PQueueEntry<V> temp = items.get(parent);
      items.set(parent, items.get(place));
      items.set(place, temp);
      place = parent;
      parent = (place-1)/2;
    }  // end while
  }

  public PQueueEntry<V> delete () throws RuntimeException {
    // removes and returns the root from the heap
    if (isEmpty())
      throw new RuntimeException("Attempt to delete() from empty heap");
    else {
      PQueueEntry<V> result = items.get(0);  // Remember root of heap
      items.set(0, items.get(size()-1));  // Move last element in heap to root
      items.remove(size()-1);  // Remove last element in heap
      heapify(0);
      return result;
    }
  }

  public PQueueEntry<V> max () throws RuntimeException {
    // returns root (without removing it) from the heap
    if (isEmpty())
      throw new RuntimeException("Attempt to get max() from empty heap");
    else
      return items.get(0);
  }

  public int size () {
    // returns the number of items currently in the heap
    return items.size();
  }

  public void clear () {
    // clears all current items from the heap
    items.clear();
  }

  public String toString () {
    // returns a String representation of the contents of the heap
    return items.toString();
  }

  private void heapify (int root) {
    int child = 2 * root + 1;  // index of root's left child, if any
    if ( child < size() ) {
      // root is not a leaf, so it has a left child 
      int rightChild = child + 1;  // index of right child, if any
      // if root has a right child, find larger child
      if ( (rightChild < size()) && (items.get(rightChild).priority().compareTo(items.get(child).priority()) > 0 )) {
        child = rightChild;    // index of larger child
      } // end if
      // if the root's value < the larger child’s value, swap values
      if (items.get(root).priority().compareTo(items.get(child).priority()) < 0) {
	PQueueEntry<V> temp = items.get(root);
        items.set(root, items.get(child));
	items.set(child, temp);
        // recursively transform the new subtree into a heap
        heapify(child);
      }  // end if
    }  // end if
    // if root is a leaf, do nothing
  }

  public static void main(String[] args) {
    MaxHeap<String> h = new MaxHeap<String>();
    h.insert(new PQueueEntry<String>(5, "Stella"));
    h.insert(new PQueueEntry<String>(4, "Brian"));
    h.insert(new PQueueEntry<String>(7, "Randy"));
    h.insert(new PQueueEntry<String>(1, "Sohie"));
    System.out.println(h);
    System.out.println("Max in heap\t" + h.max());
    System.out.println("Deleting max\t" + h.delete());
    System.out.println(h);
    h.insert(new PQueueEntry<String>(3, "Ellen"));
    System.out.println(h);
    System.out.println("Max in heap\t" + h.max());
    System.out.println("Deleting max\t" + h.delete());
    System.out.println(h);
    System.out.println("Size: " + h.size());
  }

}
