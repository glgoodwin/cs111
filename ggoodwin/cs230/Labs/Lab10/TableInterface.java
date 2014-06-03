public interface TableInterface<V> {

  // interface for the generic Table data type

  public boolean isEmpty ();
  // returns true if there are no entries in the table
	
  public void clear ();
  // resets the table to be empty
    
  public void insert (TableEntry<V> newEntry);
  // inserts an entry into the table. If an entry with the same key already 
  // exists in the table, this method overwrites it
    
  public void delete (String S);
  // deletes the entry with the key S from the table
    
  public V search (String S);
  // if an entry with key S is in the table, then the Object associated 
  // with the entry with this key is returned. Otherwise a null value
  // is returned
  
  public int size ();
  // returns the number of entries in the table
  
  public String toString ();
  // returns a String representation of the contents of the table
	
}
