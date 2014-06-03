import java.util.*;

public class Table<V> implements TableInterface<V> {

  // implements the methods specified in the Table interface
	
  // instance variables

  private Vector<TableEntry<V>> entries;
	
  // constructor
  
  public Table () {
    entries = new Vector<TableEntry<V>>();
  }
	
  // instance methods
	
  public boolean isEmpty () {
    // returns true if there are no entries in the table
    return entries.size() == 0;
  }
	
  public void clear () {
    // resets the table to be empty
    entries.clear();
  }
	
  public int find (String key) {
    // uses the binarySearch() method to locate index of the first TableEntry 
    // in the table whose key is equal to or greater than the given key
    return binarySearch(key, 0, size()-1);
  }
	
  private int binarySearch (String key, int lo, int hi) {
    // recursive method that uses binary search to find index of the first 
    // TableEntry whose key is equal to or greater than the given key
    if (hi < lo)
      // key not found in table -- lo is the index of first TableEntry 
      // whose key is larger (using "compareTo") than the given key
      return lo;     
    else {
      int mid = (lo + hi) / 2;

      if (entries.get(mid).key().equals(key))
	return mid;    // key is found in the table -- return index
      else if (entries.get(mid).key().compareTo(key) < 0)
	// continue search in the top half of the table
	return binarySearch(key, mid + 1, hi);
      else	// continue search in the bottom half of the table
	return binarySearch(key, lo, mid-1);
    }
  }
	
  public void insert (TableEntry<V> newEntry) {
    // inserts newEntry into the table. If entry with the same key already 
    // exists in the table, this method overwrites it 

    // find the index where the new TableEntry should be inserted 
    int i = find(newEntry.key());

    // if key is greater than all keys currently in the table, insert 
    // new TableEntry at the end of the current contents
    if (i == size())
      entries.add(newEntry);

    // check whether TableEntry with this key already exists, and
    // if so, replace it
    else if ((i < size()) && (entries.get(i).key().equals(newEntry.key())))
      entries.set(i, newEntry);

    else  // add entry at given index (shifting all subsequent entries to the right)
      entries.add(i, newEntry);
  }
			
  public void delete (String key) {
    // deletes the entry with the given key from the table
    // find the index of the TableEntry to be deleted
    int i = find(key);
    // if TableEntry with this key is found, remove it
    if ((i < size()) && (entries.get(i).key().equals(key))) {
      entries.remove(i);
    }
  }
  
  public V search (String key) {
    // if entry with given key is in the table, then the value associated
    // with this entry is returned, otherwise a null value is returned
    // find the location of the TableEntry with this key
    int i = find(key);
    // if a TableEntry with this key is found, return the associated value
    if ((i < size()) && (entries.get(i).key().equals(key)))
      return entries.get(i).value();
    else
      return null;
  }
	
  public int size () {
    // returns the number of entries in the table
    return entries.size();
  }
	
  public String toString () {
    // returns a String representation of the contents of a table
    String S = "";
    for (int i = 0; i < size(); i++)
      S = S + entries.get(i).toString() + "\n";
    return S;
  }
    
}
