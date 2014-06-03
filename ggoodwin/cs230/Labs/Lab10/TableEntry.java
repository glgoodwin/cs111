public class TableEntry<V> {
	
  // class definition for creating an instance of an entry of the Table
	
  // instance variables
	
  private String key;
  private V value;
  
  // constructors
	
  public TableEntry (String S) {
    this.key = S;
    this.value = null;
  }
	
  public TableEntry (String S,  V val) {
    this.key = S;
    this.value = val;
  }
	
  // instance methods to access the instance variables
	
  public String key () {
    return key;
  }
  
  public V value () {
    return value;
  }
  
  public void setValue (V newValue) {
    value = newValue;
  }
  
  public String toString () {
    return "key: " + key + "  value: " + value.toString();
  }

}
