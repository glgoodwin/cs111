import java.util.Hashtable;  // Hashtable class
import java.util.Enumeration;  // enumeration

public class TableHash<V> implements TableInterface<V> {
	
  // implements the basic Table methods using a Hash Table to store 
  // the contents of the table
    
  private Hashtable<String,V> entries;
	
  public TableHash () {
    // constructs an initial empty table 
    entries = new Hashtable<String,V>();
  }
	
  public boolean isEmpty () {
    // returns true if there are no entries in the table
    return entries.size() == 0;
  }
	
  public void clear () {
    // resets table to be empty
    entries.clear();
  }
	
  public V search (String S) {
    // calls the tableFind() method to search the table for a TableEntry 
    // containing the key S. If an entry with key S is in the table, then 
    // the value associated with this entry is returned. Otherwise the
    // value null is returned
    return tableFind(entries,S);
  }
	
  private V tableFind (Hashtable<String,V> t, String S) {
    // if an entry with key S is stored in the tree t, then the value
    // associated with this entry is returned. Otherwise null is returned.
    // if tree is empty, then key is not found, so return null immediately
    if (t.containsKey(S)) {
      //  return t.get(S).value();
      return t.get(S);
    } else
      return null;
  }
  
  public void insert (TableEntry<V> newEntry) {
    // inserts newEntry into the hashtable. If entry with the same key already 
    // exists in the table, this method overwrites it 
    entries.put(newEntry.key(),newEntry.value()); 
  }
	
       
  public void delete (String S) {
    // deletes the TableEntry with the key S from the table
    deleteTable(entries, S);
  }
  
  private Hashtable<String,V> deleteTable (Hashtable<String,V> t, String S) {
    // deletes the entry with the key S from the table
    if (t.containsKey(S)) {
      t.remove(S);
      return t;
    } else {
      return t;
    }
  }
	
  public int size () {
    // returns the number of entries in the table
    return entries.size();
  }
  
  public String toString () {
    // returns a String representation of the contents of the hashtable
    String s = "";
    Enumeration stuff = entries.keys();
    while (stuff.hasMoreElements()) {
      String key = (String) stuff.nextElement();
      s = s + key;
      s = s + " "+ (entries.get(key)).toString() + "\n";
    } 
    return s;
  }
   
  
  // testing the implementation methods
  public static void main(String[] args) {
    
    TableHash<String> T = new TableHash<String> ();
    System.out.println("Should be true and 0: " + T.isEmpty() + T.size());

    T.insert(new TableEntry ("A", "Alpha"));
    System.out.println("ToString:  T  "+T.toString());
    System.out.println("Should be false and 1: " + T.isEmpty() + T.size());
    System.out.println("Has 1 in :" + T);
    T.insert(new TableEntry ("B", "Beta"));
    T.insert(new TableEntry ("G", "Gamma"));
    T.insert(new TableEntry ("D", "Delta"));
    System.out.println("Should be false and 4: " + T.isEmpty() + T.size());
    System.out.println("Has 4 in : " + T);
    System.out.println("TO string: "+ T.toString());
    System.out.println("Can you find G? : " + T.search("G"));
    T.delete("A");
    System.out.println("Has no A in it : " + T);

  }
}
  

