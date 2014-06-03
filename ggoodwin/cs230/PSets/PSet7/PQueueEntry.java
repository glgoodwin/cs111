public class PQueueEntry<V> {
	
  // class definition for creating an instance of an entry in a Priority Queue
	
  // instance variables
	
  private Integer priority;
  private V value;
  
  // constructors
	
  public PQueueEntry (Integer priority) {
    this.priority = priority;
    this.value = null;
  }
	
  public PQueueEntry (Integer priority, V val) {
    this.priority = priority;
    this.value = val;
  }
	
  // instance methods to access the instance variables
	
  public Integer priority () {
    return priority;
  }
  
  public V value () {
    return value;
  }
  
  public void setValue (V newValue) {
    value = newValue;
  }
  
  public String toString () {
    return priority.toString() + ":" + value.toString();
  }

}
