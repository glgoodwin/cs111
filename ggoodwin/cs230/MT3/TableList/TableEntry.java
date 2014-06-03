public class TableEntry<V> {
	
  // class definition for creating an instance of an entry of the Table
	
  // instance variables
	
  private String key;
  private V value;
  
  // constructors
	
  public TableEntry (String key) {
    this.key = key;
    this.value = null;
  }
	
  public TableEntry (String key, V val) {
    this.key = key;
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
    return "key: " + key.toString() + "\t\t" + "value: " + value.toString();
  }

}
