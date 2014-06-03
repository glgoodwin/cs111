public class TableArray implements TableInterface {	
	
  // implements the methods specified in the Table interface
	
  private static final int maxT = 500;  // maximum table size
	
  // instance variables
  
  private int size;
  private TableEntry [] entries;
	
  // constructor
  
  public TableArray () {
    size = 0;
    entries = new TableEntry[maxT];
  }
	
  // instance methods
	
  public boolean tableEmpty () {
    // returns true if there are no entries in the table
    return size == 0;
  }
	
  public boolean tableFull () {
    // returns true if no more entries can be inserted into the table
    return size == maxT;
  }
	
  public void tableClear () {
    // resets the table to be empty
    size = 0;
  }
	
  private int tableFind (String S) {
    // uses the find() method to locate index of the first TableEntry 
    // in the table whose key is equal to or greater than the string S
    return find(S, 0, size-1);
  }
	
  private int find (String S, int lo, int hi) {
    // recursive method that uses binary search to find index of the first 
    // TableEntry whose key is equal to or greater than the string S
    int mid;
    if (hi < lo)
      // key not found in table -- lo is the index of first TableEntry 
      // whose key is larger (later in the alphabet) than the string S
      return lo;     
    else {
      mid = (lo + hi) / 2;
      if ((entries[mid].key()).equals(S))
	return mid;    // key S is found in the table -- return index
      else if ((entries[mid].key()).compareTo(S) < 0)
	// continue search in the top half of the table
	return find(S, mid + 1, hi);
      else	// continue search in the bottom half of the table
	return find(S, lo, mid-1);
    }
  }
	
  public void tableInsert (TableEntry newEntry) {
    // inserts newEntry into the table. If entry with the same key already 
    // exists in the table, this method overwrites it 
    if (tableFull()) {
      // check whether TableEntry with this key already exists, and
      // if so, replace it
      int i = tableFind(newEntry.key());
      if ((i < size) && (entries[i].key()).equals(newEntry.key()))
	entries[i].setEntry(newEntry.entry());
      else 
	System.out.println("table is full");
    } else {
      // find the index where the new TableEntry should be inserted
      int i = tableFind(newEntry.key());
      // if key is greater than all keys currently in the table, insert 
      // new TableEntry at the end of the current contents
      if (i == size) {
	entries[i] = newEntry;	
	size++;
      } else
	// if TableEntry with this key already exists, replace entry 
	if ((entries[i].key()).equals(newEntry.key()))
	  entries[i].setEntry(newEntry.entry());
	else {
	  // shift remaining entries to the right to create space
	  // the new TableEntry
	  for (int index = size; index > i; index--)
	    entries[index] = entries[index-1];
	  entries[i] = newEntry;
	  size++;
	}
    }
  }
			
  public void tableDelete (String S) {
    // deletes the entry with the key S from the table
    // find the index of the TableEntry to be deleted
    int i = tableFind(S);
    // if TableEntry with this key is found, shift remaining contents of
    // table to the left, effectively covering this TableEntry
    if ((i < size) && ((entries[i].key()).equals(S))) {
      for (int index = i; index < (size-1); index++)
	entries[index] = entries[index+1];
      size--;
    }
  }
  
  public Object tableSearch (String S) {
    // if entry with key S is in the table, then the Object associated
    // with this entry is returned, otherwise a null value is returned.
  
    int i = tableFind(S);// find the loc of the TableEntry with this key
    // if a TableEntry with this key is found, return the associated entry
    if ((i < size) && ((entries[i].key()).equals(S))) 
      return entries[i].entry();
    else
      return null;
  }
  /*
  public TableEntry getIth(int i) {
    //Returns the i-th Table Entry
    if (i<0 || i>size-1) return null;
    return (entries[i]);
  }	
  */
  public int tableSize () {
    // returns the number of entries in the table
    return size;
  }
	
  public String toString () {
    // returns a String representation of the contents of a table
    String S = "";
    for (int i = 0; i < size; i++)
      S = S + entries[i].toString() + "\n";
    return S;
  }
    
}
